package it.uniroma3.siw.ecommerce.Repository;

import it.uniroma3.siw.ecommerce.Model.Credentials;
import it.uniroma3.siw.ecommerce.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials,Long> {
    public Optional<Credentials> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Credentials getUserByUsername(String username);

}
