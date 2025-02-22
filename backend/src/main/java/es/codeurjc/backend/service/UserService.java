
package es.codeurjc.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.UserRepository;

@Service
public class UserService {


    @Autowired
	private UserRepository repository;


    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getAllUsers() {
        return users;
    }

	public User userCorrect(String email, String password){

		for (User userInUsers: users){
			if (userInUsers.getEmail().equals(email)){
				 if (userInUsers.getPassword().equals(password)){
					return userInUsers;
				 }
			}
		}
		return null;
	}

	public Optional<User> findById(long id) {
		return repository.findById(id);
	}

	public Optional<User> findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

	public boolean userExists(String userName){
		return repository.findByUserName(userName).isPresent();
	}

	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public void save(User user) {
		repository.save(user);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}

	 
}

