package it.uniroma3.siw.ecommerce.Service;

import it.uniroma3.siw.ecommerce.Model.Product;
import it.uniroma3.siw.ecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Iterable<Product> getAllProducts(){
        return productRepository.findAll();
    }
}
