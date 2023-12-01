package it.uniroma3.siw.ecommerce.Controller;

import it.uniroma3.siw.ecommerce.Global.GlobalData;
import it.uniroma3.siw.ecommerce.Model.User;
import it.uniroma3.siw.ecommerce.Repository.RoleRepository;
import it.uniroma3.siw.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/login")
    public  String login(){
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/register")
    public String registerGet(){
        return "register";
    }
}
