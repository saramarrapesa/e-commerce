package it.uniroma3.siw.ecommerce.OAuth;

import it.uniroma3.siw.ecommerce.Model.User;
import it.uniroma3.siw.ecommerce.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler  implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    public OAuth2LoginSuccessHandler(){
        super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String loginName = oAuth2User.getLogin();
        String name = oAuth2User.getName();
        String surname = oAuth2User.getSurname();
        String email = oAuth2User.getEmail();
        System.out.println("Login : " + loginName );
        System.out.println("name: " + name);
        System.out.println("surname: " + surname );
        System.out.println("email: " + email );

        User user;
        if(loginName != null) {

            user = userService.getUser(loginName);
        }else{

            user = userService.getUser(name);
        }

        if(user == null){
            userService.registerNewCustomerAfterOAuthLoginSuccess(loginName,name,surname, email,AuthenticationProvider.GOOGLE);
        }else{
            userService.updateExistingUser(user, name,surname, AuthenticationProvider.GOOGLE);
        }

        response.sendRedirect("/login/oauth2/user");
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

}
