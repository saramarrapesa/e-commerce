package it.uniroma3.siw.ecommerce.Controller;

import it.uniroma3.siw.ecommerce.Global.GlobalData;
import it.uniroma3.siw.ecommerce.Global.WishList;
import it.uniroma3.siw.ecommerce.Model.Newsletter;
import it.uniroma3.siw.ecommerce.Model.Product;
import it.uniroma3.siw.ecommerce.Service.CategoryService;
import it.uniroma3.siw.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WishListController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @GetMapping("/addToWishlist/{id}")
    public String addToWishlist(@PathVariable Long id){
        WishList.wishlist.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }

    @GetMapping("/wishlist")
    public String wishlistGet(Model model){
        model.addAttribute("wishlist",WishList.wishlist);
        model.addAttribute("wishlistCount", WishList.wishlist.size());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("newsletter", new Newsletter());
        return "wishlist";
    }


    @GetMapping("/wishlist/removeItem/{index}")
    public String wishlistItemRemove(@PathVariable int index){
        WishList.wishlist.remove(index);
        return "redirect:/wishlist";
    }

}
