
package es.codeurjc.backend.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.dto.UserDTO;
import es.codeurjc.backend.dto.UserMapper;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.UserRepository;


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
		return toDTO(repository.findById(id).orElseThrow());
	}

	public UserDTO deleteUser(long id) {

		User user = repository.findById(id).orElseThrow();
		UserDTO userDTO = toDTO(user);

		repository.deleteById(id);

		return userDTO;
	}

	public UserDTO createUser(UserDTO userDTO) {

		if (userDTO.id() != null) {
			throw new IllegalArgumentException();
		}

		User user = toDomain(userDTO);

		repository.save(user);

		return toDTO(user);
	}

	public UserDTO replaceUser(long id, UserDTO updateUserDTO) throws SQLException {

		if (repository.existsById(id)) {
			User updatedUser = toDomain(updateUserDTO);
			updatedUser.setId(id);
			repository.save(updatedUser);
			return toDTO(updatedUser);
		} else {
			throw new NoSuchElementException();
		}

	}

	public Resource getUserImage(long id) throws SQLException {

		User user = repository.findById(id).orElseThrow();

		if (user.getProfilePhoto() != null) {
			return new InputStreamResource(user.getProfilePhoto().getBinaryStream());
		} else {
			throw new NoSuchElementException();
		}
	}

	public void createUserImage(long id, InputStream inputStream, long size) {

		User user = repository.findById(id).orElseThrow();

		user.setImage(true);
		user.setProfilePhoto(BlobProxy.generateProxy(inputStream, size));

		repository.save(user);
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

	private User toDomain(UserDTO userDTO) {
		return mapper.toDomain(userDTO);
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

