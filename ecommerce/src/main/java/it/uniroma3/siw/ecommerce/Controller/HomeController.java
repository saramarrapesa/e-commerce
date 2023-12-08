package it.uniroma3.siw.ecommerce.Controller;

import it.uniroma3.siw.ecommerce.Global.GlobalData;
import it.uniroma3.siw.ecommerce.Model.Credentials;
import it.uniroma3.siw.ecommerce.Service.CategoryService;
import it.uniroma3.siw.ecommerce.Service.CredentialsService;
import it.uniroma3.siw.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    ProductService productService;

    @GetMapping({"/", "/home"})
    public  String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("cartCount",GlobalData.cart.size());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "index";
        }
        else {
            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
            if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
                return "admin/adminHome";
            }
        }
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
        model.addAttribute("categories",categoryService.getAllCategories());
        return "viewProduct";
    }

}
