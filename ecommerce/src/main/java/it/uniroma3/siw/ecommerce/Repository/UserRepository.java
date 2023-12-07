package it.uniroma3.siw.ecommerce.Repository;

import it.uniroma3.siw.ecommerce.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findUsersByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User getUserByUsername(String username);
}
