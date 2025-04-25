
package es.codeurjc.backend.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.dto.ticket.TicketDTO;
import es.codeurjc.backend.dto.user.NewUserDTO;
import es.codeurjc.backend.dto.user.UserDTO;
import es.codeurjc.backend.dto.user.UserMapper;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserMapper mapper;

	public Collection<UserDTO> getUsers() {
		return toDTOs(repository.findAll());
	}

	public UserDTO getUser(long id) {
		User user = repository.findById(id).orElseThrow();
		user.addFavoriteGenre();
		repository.save(user);
		return toDTO(user);

	}

	public UserDTO deleteUser(long id) {

		User user = repository.findById(id).orElseThrow();
		UserDTO userDTO = toDTO(user);

		repository.deleteById(id);

		return userDTO;
	}

	public UserDTO createUser(UserDTO userDTO) throws SQLException {

		if (userDTO == null ||userDTO.id() != null) {
			throw new IllegalArgumentException();
		}

		User user = toDomain(userDTO);

		repository.save(user);

		return toDTO(user);
	}

	public UserDTO replaceUser(long id, UserDTO updateUserDTO) throws SQLException {

		if (repository.existsById(id)) {
			User updatedUser = toDomain(updateUserDTO);
			User existingUser = repository.findById(id).orElseThrow();
			updatedUser.setProfilePhoto(existingUser.getProfilePhoto());
			updatedUser.setImage(existingUser.getImage());
			updatedUser.setId(id);
			repository.save(updatedUser);
			return toDTO(updatedUser);
		} else {
			throw new NoSuchElementException();
		}

	}

	public UserDTO createOrReplaceUser(Long id, UserDTO userDTO) throws SQLException {

		UserDTO user;
		if (id == null) {
			user = createUser(userDTO);
		} else {
			user = replaceUser(id, userDTO);
		}
		return user;
	}

	public void createUserImage(long id, InputStream inputStream, long size) {

		User user = repository.findById(id).orElseThrow();

		user.setImage(true);
		user.setProfilePhoto(BlobProxy.generateProxy(inputStream, size));

		repository.save(user);
	}

	public Resource getUserImage(long id) throws SQLException {

		User user = repository.findById(id).orElseThrow();

		if (user.getProfilePhoto() != null) {
			return new InputStreamResource(user.getProfilePhoto().getBinaryStream());
		} else {
			throw new NoSuchElementException();
		}
	}

	public void updateImageUser(User user, boolean removeImage, User updatedUser)
			throws IOException, SQLException {

		if (removeImage) {
			user.setProfilePhoto(null);
			user.setImage(false);
		} else if (updatedUser.getProfilePhoto() != null) {
			user.setProfilePhoto(BlobProxy.generateProxy(updatedUser.getProfilePhoto().getBinaryStream(),
					updatedUser.getProfilePhoto().length()));
			user.setImage(true);
		} else {
			User dbUser = repository.findById(user.getId()).orElseThrow();
			if (dbUser.getImage()) {
				user.setProfilePhoto(BlobProxy.generateProxy(dbUser.getProfilePhoto().getBinaryStream(),
						dbUser.getProfilePhoto().length()));
				user.setImage(true);
			}
		}
	}

	public void replaceUserImage(long id, InputStream inputStream, long size) {

		User user = repository.findById(id).orElseThrow();

		if (!user.getImage()) {
			throw new NoSuchElementException();
		}

		user.setProfilePhoto(BlobProxy.generateProxy(inputStream, size));

		repository.save(user);
	}

	public void deleteUserImage(long id) {

		User user = repository.findById(id).orElseThrow();

		if (!user.getImage()) {
			throw new NoSuchElementException();
		}

		user.setProfilePhoto(null);
		user.setImage(false);

		repository.save(user);
	}

	private UserDTO toDTO(User user) {
		return mapper.toDTO(user);
	}

	private User toDomain(UserDTO newUserDTO) {
		return mapper.toDomain(newUserDTO);
	}

	private Collection<UserDTO> toDTOs(Collection<User> users) {
		return mapper.toDTOs(users);
	}

	public UserDTO getUserByUsername(String userName) {
		User user = repository.findByUserName(userName).orElseThrow();
		user.addFavoriteGenre();
		repository.save(user);
		return toDTO(user);
	}

	public boolean userExists(String userName) {
		return repository.findByUserName(userName).isPresent();
	}

	public Map<String, Integer> calculateAgeDistribution(Collection<TicketDTO> tickets) {
    int rank018 = 0, rank1950 = 0, rank51110 = 0;

    for (TicketDTO ticket : tickets) {
        UserDTO user = getUser(ticket.userOwnerId());
        int age = user.age();
        int numTickets = ticket.numTickets();

        if (age <= 18) {
            rank018 += numTickets;
        } else if (age >= 19 && age <= 50) {
            rank1950 += numTickets;
        } else {
            rank51110 += numTickets;
        }
    }

    return Map.of(
        "0-18", rank018,
        "19-50", rank1950,
        "51-110", rank51110
    );
	}

	public UserDTO UserCreationReplacement(Long userId, NewUserDTO newUserDTO, Boolean removeImage, PasswordEncoder passwordEncoder) throws IOException, SQLException {
		boolean image = false;
		String userName = newUserDTO.userName();
		String password = null;
		Integer numTicketsBought = 0;
		String favoriteGenre = "None";
		List<TicketDTO> tickets = null;
		List<String> roles = List.of("USER");

		if (newUserDTO.password() != null && !newUserDTO.password().isEmpty()) {
			password = passwordEncoder.encode(newUserDTO.password());
		}

		if (userId != null) {
			UserDTO oldUser = getUser(userId);
			image = removeImage != null && removeImage ? false : oldUser.image();
			userName = oldUser.userName();
			password = oldUser.password();
			numTicketsBought = oldUser.tickets() != null ? oldUser.tickets().stream().mapToInt(TicketDTO::numTickets).sum() : 0;
			favoriteGenre = oldUser.favoriteGenre();
			tickets = oldUser.tickets();
			roles = oldUser.roles();
		}

		UserDTO userDTO = new UserDTO(userId, newUserDTO.fullName(), userName, newUserDTO.phone(),
				newUserDTO.email(), password, newUserDTO.age(), numTicketsBought, favoriteGenre, image, tickets, roles);

		UserDTO newUser = createOrReplaceUser(userId, userDTO);

		MultipartFile imageField = newUserDTO.profilePhoto();
		if (imageField != null && !imageField.isEmpty()) {
			createUserImage(newUser.id(), imageField.getInputStream(), imageField.getSize());
		}

		return newUser;
	}

	public UserDTO getAuthenticatedUser(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			return getUserByUsername(principal.getName());
		} else {
			throw new NoSuchElementException();
		}
	}

}
