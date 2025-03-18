
package es.codeurjc.backend.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.dto.NewUserDTO;
import es.codeurjc.backend.dto.UserDTO;
import es.codeurjc.backend.dto.UserMapper;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.UserRepository;


@Service
public class UserService {


	@Autowired
	private UserRepository repository;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserMapper mapper;

	public Collection<UserDTO> getUsers() {
		return toDTOs(repository.findAll());
	}

	public UserDTO getUser(long id) {
		return toDTO(repository.findById(id).orElseThrow());
	}

	public UserDTO deleteUser(long id) {

		User user = repository.findById(id).orElseThrow();
		UserDTO userDTO = toDTO(user);

		repository.deleteById(id);

		return userDTO;
	}

	public UserDTO createUser(NewUserDTO newUserDTO) throws SQLException{

		User user = toDomain(newUserDTO);

		user.setFavoriteGenre("None");
		user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));
		user.setRoles(List.of("USER"));
		if (user.getProfilePhoto() != null) {
			user.setProfilePhoto(BlobProxy.generateProxy(user.getProfilePhoto().getBinaryStream(), user.getProfilePhoto().length()));
			user.setImage(true);
		}

		repository.save(user);

		return toDTO(user);
	}

	public UserDTO replaceUser(long id, NewUserDTO updateUserDTO, boolean removeImage) throws SQLException, IOException {

		User oldUser= repository.findById(id).orElseThrow();
		User updatedUser = toDomain(updateUserDTO);
		updatedUser.setId(id);
		if (updatedUser.getFullName() != null) {
			oldUser.setFullName(updatedUser.getFullName());
		}
		if (updatedUser.getPhone() != null) {
			oldUser.setPhone(updatedUser.getPhone());
		}
		if (updatedUser.getEmail() != null) {
			oldUser.setEmail(updatedUser.getEmail());
		}
		if (updatedUser.getAge() != null) {
			oldUser.setAge(updatedUser.getAge());
		}

		updateImageUser(oldUser, removeImage, updatedUser);

		repository.save(updatedUser);
		return toDTO(updatedUser);
		
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
			user.setProfilePhoto(BlobProxy.generateProxy(updatedUser.getProfilePhoto().getBinaryStream(), updatedUser.getProfilePhoto().length()));
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

	private UserDTO toDTO(User user) {
		return mapper.toDTO(user);
	}

	private User toDomain(NewUserDTO newUserDTO) {
		return mapper.toDomain(newUserDTO);
	}

	private Collection<UserDTO> toDTOs(Collection<User> users) {
		return mapper.toDTOs(users);
	}

	public UserDTO getUserByUsername(String userName) {
		return toDTO(repository.findByUserName(userName).orElseThrow());
	}

	public boolean userExists(String userName){
		return repository.findByUserName(userName).isPresent();
	}

	 
}

