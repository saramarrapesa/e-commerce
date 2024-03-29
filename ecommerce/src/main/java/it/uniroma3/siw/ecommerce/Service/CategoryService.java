package it.uniroma3.siw.ecommerce.Service;

import it.uniroma3.siw.ecommerce.Model.Category;
import it.uniroma3.siw.ecommerce.Model.Product;
import it.uniroma3.siw.ecommerce.Repository.CategoryRepository;
import it.uniroma3.siw.ecommerce.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    public Iterable<Category> getAllCategories(){
        return  categoryRepository.findAll();
    }

    public void addCategory(Category category){
        categoryRepository.save(category);
    }
    public void removeCategoryById(Long id){
        categoryRepository.deleteById(id);
    }
    public Optional<Category> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }
}
