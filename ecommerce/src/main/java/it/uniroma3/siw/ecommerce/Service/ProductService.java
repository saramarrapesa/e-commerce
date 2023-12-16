package it.uniroma3.siw.ecommerce.Service;

import it.uniroma3.siw.ecommerce.Model.Image;
import it.uniroma3.siw.ecommerce.Model.Product;
import it.uniroma3.siw.ecommerce.Model.Review;
import it.uniroma3.siw.ecommerce.Repository.ImageRepository;
import it.uniroma3.siw.ecommerce.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;


    @Autowired
    private ImageRepository imageRepository;
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product addProduct(Product product , MultipartFile multipartFile )throws IOException {
        Image imageProduct = new Image(multipartFile.getBytes());
        this.imageRepository.save(imageProduct);
        product.setImage(imageProduct);
        return this.productRepository.save(product);
    }

    public void saveProdotto(Product product) {
        this.productRepository.save(product);
    }


    public void removeProduct(Long id){
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public Product findProductById(Long id){ return productRepository.findProductById(id);  }

    public List<Product> getAllProductsByCategory(long id){
        return productRepository.findAllByCategory_Id(id);
    }

    public List<Product> findByKeyword(String keyword){
        return productRepository.findByKeyword(keyword);
    }

    //ogni utente può scrivere una recensione sul prodotto

    public void function (Model model , Product prodotto , String username) {
        if(username!=null && this.alreadyReviewed(prodotto.getReviews(), username))
            model.addAttribute("hasNotAlreadyCommented", false);
        else
            model.addAttribute("hasNotAlreadyCommented", true);
        model.addAttribute("review", new Review());
        model.addAttribute("reviews", prodotto.getReviews());
        model.addAttribute("hasReviews", !prodotto.getReviews().isEmpty());

    }

    @Transactional
    public boolean alreadyReviewed(Set<Review> reviews , String username){
        if(reviews != null) {
            for(Review rev : reviews) {
                if(rev.getUsername().equals(username))
                    return true;
            }
        }
        return false;
    }
}
