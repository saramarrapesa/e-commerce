package it.uniroma3.siw.ecommerce.Service;

import it.uniroma3.siw.ecommerce.Model.CustomUserDetail;
import it.uniroma3.siw.ecommerce.Model.User;
import it.uniroma3.siw.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Optional<User> user = userRepository.findUserByEmail(email);
        user.orElseThrow(()-> new UsernameNotFoundException("User non trovato"));
        return user.map(CustomUserDetail::new).get();
    }
}
