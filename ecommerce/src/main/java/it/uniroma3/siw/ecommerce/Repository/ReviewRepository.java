package it.uniroma3.siw.ecommerce.Repository;

import it.uniroma3.siw.ecommerce.Model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    public boolean existsByUsernameAndTitleAndRatingAndDescription(String username,String title,Integer rating, String description);

}
