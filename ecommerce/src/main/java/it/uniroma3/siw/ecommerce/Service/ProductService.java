package it.uniroma3.siw.ecommerce.Service;

import it.uniroma3.siw.ecommerce.Model.Image;
import it.uniroma3.siw.ecommerce.Model.Product;
import it.uniroma3.siw.ecommerce.Repository.ImageRepository;
import it.uniroma3.siw.ecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    public List<Product> getAllProductsByCategory(long id){
        return productRepository.findAllByCategory_Id(id);
    }

    public List<Product> findByKeyword(String keyword){
        return productRepository.findByKeyword(keyword);
    }
}
