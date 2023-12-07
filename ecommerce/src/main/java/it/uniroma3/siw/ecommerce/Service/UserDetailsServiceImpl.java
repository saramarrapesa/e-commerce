package it.uniroma3.siw.ecommerce.Service;


import it.uniroma3.siw.ecommerce.Model.Credentials;
import it.uniroma3.siw.ecommerce.Model.MyUserDetail;
import it.uniroma3.siw.ecommerce.Model.User;
import it.uniroma3.siw.ecommerce.Repository.CredentialsRepository;
import it.uniroma3.siw.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Credentials user = credentialsRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetail(user);
    }

}
