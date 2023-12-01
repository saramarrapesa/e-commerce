package it.uniroma3.siw.ecommerce.Controller;

import it.uniroma3.siw.ecommerce.Global.GlobalData;
import it.uniroma3.siw.ecommerce.Model.Product;
import it.uniroma3.siw.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id){
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String cartGet(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice));
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }


    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public  String checkout(Model model){
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice));
        return  "checkout";
    }

}
