package it.uniroma3.siw.ecommerce.Controller;

import it.uniroma3.siw.ecommerce.Global.GlobalData;
import it.uniroma3.siw.ecommerce.Model.Credentials;
import it.uniroma3.siw.ecommerce.Model.User;
import it.uniroma3.siw.ecommerce.Service.CredentialsService;
import it.uniroma3.siw.ecommerce.Service.UserService;
import it.uniroma3.siw.ecommerce.Session.SessionData;
import it.uniroma3.siw.ecommerce.Validator.CredentialsValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private CredentialsService credentialsService;


    @Autowired
    CredentialsValidator credentialsValidator;

    @Autowired
    SessionData sessionData;

    @GetMapping("/login")
    public  String login(){
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/register")
    public String registerGet(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "register";
    }

    @PostMapping(value={"/register"})
    public String registerPost(@Valid @ModelAttribute("user") User user,
                               BindingResult userBindingResult, @Valid
                                   @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {

        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            userService.saveUser(user);
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "index";
        }
        return "register";
    }


    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/adminHome";
        }
        return "index";
    }

    /* questo metodo probabilmente dovrà essere cancellato*/
    @RequestMapping(value={"login/oauth2/user"}, method = RequestMethod.GET)
    public String oAuth2Successful(Model model){
        User loggedUser = this.sessionData.getLoggedUser();
        model.addAttribute("user",loggedUser);
        return "index";
    }



}
