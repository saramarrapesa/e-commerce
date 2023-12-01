package it.uniroma3.siw.ecommerce.Controller;

import it.uniroma3.siw.ecommerce.Global.GlobalData;
import it.uniroma3.siw.ecommerce.Service.CategoryService;
import it.uniroma3.siw.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping({"/", "/home"})
    public  String home(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public  String shop(Model model){
       model.addAttribute("categories", categoryService.getAllCategories());
       model.addAttribute("products", productService.getAllProducts());
       model.addAttribute("cartCount", GlobalData.cart.size());
       return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public  String shopByCategory(Model model, @PathVariable Long id ){
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProductsByCategory(id));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public  String viewProduct(Model model, @PathVariable Long id ){
        model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "viewProduct";
    }

}
