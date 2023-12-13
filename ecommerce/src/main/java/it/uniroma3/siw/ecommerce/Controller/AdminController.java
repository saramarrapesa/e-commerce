package it.uniroma3.siw.ecommerce.Controller;

import it.uniroma3.siw.ecommerce.Model.Category;
import it.uniroma3.siw.ecommerce.Model.Product;
import it.uniroma3.siw.ecommerce.Service.CategoryService;
import it.uniroma3.siw.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/admin")
    public String adminHome(){
        return "admin/adminHome";
    }

    //categories section
    @GetMapping("/admin/categories")
    public String getCategories(Model model){
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCategoriesAdd(Model model){
        model.addAttribute("category", new Category());
        return "admin/categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCategoriesAdd(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable Long id , Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "admin/categoriesAdd";
        }
        else
           return "404";
    }
    //product section

    @GetMapping("/admin/products")
    public String getProducts(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/admin/products/add")
    public String getProductsAdd(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute("product") Product product , @RequestParam("productImage")MultipartFile file,  Model model) throws IOException{
       model.addAttribute("product",this.productService.addProduct(product,file));
       return "redirect:/admin/products";
    }

    @GetMapping ("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.removeProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable Long id , Model model){
        Product existingProduct = productService.getProductById(id).get();
        Product product = new Product();
        product.setId(existingProduct.getId());
        product.setName(existingProduct.getName());
        product.setBrand(existingProduct.getBrand());
        product.setWeight(existingProduct.getWeight());
        product.setDescription(existingProduct.getDescription());
        product.setPrice(existingProduct.getPrice());
        product.setImage(existingProduct.getImage());
        product.setCategory(existingProduct.getCategory());
        model.addAttribute("product" , product);
        model.addAttribute("categories" , categoryService.getAllCategories());
        return "admin/productsAdd";
    }

}
