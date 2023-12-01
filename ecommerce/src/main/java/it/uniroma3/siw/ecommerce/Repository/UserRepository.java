package it.uniroma3.siw.ecommerce.Repository;

import it.uniroma3.siw.ecommerce.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findUserByEmail(String email);
}
