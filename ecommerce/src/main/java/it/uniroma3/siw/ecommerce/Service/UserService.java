package it.uniroma3.siw.ecommerce.Service;

import it.uniroma3.siw.ecommerce.Model.User;
import it.uniroma3.siw.ecommerce.OAuth.AuthenticationProvider;
import it.uniroma3.siw.ecommerce.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    protected UserRepository userRepository;

    /**
     * This method retrieves a User from the DB based on its ID.
     * @param id the id of the User to retrieve from the DB
     * @return the retrieved User, or null if no User with the passed ID could be found in the DB
     */
    @Transactional
    public User getUser(Long id) {
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * This method saves a User in the DB.
     * @param user the User to save into the DB
     * @return the saved User
     * @throws DataIntegrityViolationException if a User with the same username
     *                              as the passed User already exists in the DB
     */
    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */
    @Transactional
    public Collection<User> getAllUsers() {

        return (Collection<User>) this.userRepository.findAll();
    }

    @Transactional
    public User getUsername(String login) {
        Optional<User> result = this.userRepository.findByUsername(login);
        return result.orElse(null);
    }

    @Transactional
    public User getUser(String username){
        Optional<User> result = this.userRepository.findByUsername(username);
        return result.orElse(null);
    }


    public void registerNewCustomerAfterOAuthLoginSuccess(String loginName, String fullName,  AuthenticationProvider provider) {

        User user = new User();
        if(loginName != null) {
            user.setUsername(loginName);
            user.setFirstName(fullName);
        }
        else{
            user.setUsername(loginName);
        }
        user.setAuthProvider(provider);
        userRepository.save(user);
    }

    public void updateExistingUser(User user, String fullName, AuthenticationProvider provider){
        user.setFirstName(fullName);
        user.setAuthProvider(provider);   // probabilmente da modificare
        userRepository.save(user);
    }


}
