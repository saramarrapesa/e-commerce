package it.uniroma3.siw.ecommerce.Repository;

import it.uniroma3.siw.ecommerce.Model.Newsletter;
import org.springframework.data.repository.CrudRepository;

public interface NewsletterRepository extends CrudRepository<Newsletter,Long> {
}
