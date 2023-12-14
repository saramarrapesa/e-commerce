package it.uniroma3.siw.ecommerce.Authentication;



import it.uniroma3.siw.ecommerce.OAuth.OAuth2LoginSuccessHandler;
import it.uniroma3.siw.ecommerce.Service.CustomOAuth2UserService;
import it.uniroma3.siw.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static it.uniroma3.siw.ecommerce.Model.Credentials.ADMIN_ROLE;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;

    @Autowired
    private OAuth2LoginSuccessHandler authenticationSuccessHandler;

    @Autowired
    UserService userService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().and().cors().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET,"/","/index","/shop/**","/register","/login","/register","/css/**","/lib/**", "/scss/**","/js/**","/mail/**","/images/**","/productImage/**", "favicon.icon","/cart","/checkout","/orderPlaced","/viewProduct","/productImages/{id}","/wishlist","/contact").permitAll()
                .requestMatchers("/oauth2/**").authenticated()
                .requestMatchers(HttpMethod.POST,"/register","/login","/contact","/newsletter").permitAll()
                .requestMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/success",true)
                .failureUrl("/login?error=true")
                .and().logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true).permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService( oAuth2UserService)
                .and()
                .successHandler(authenticationSuccessHandler)
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .clearAuthentication(true).permitAll();
        return httpSecurity.build();
    }



    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }



}
