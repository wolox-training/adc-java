package wolox.training.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import wolox.training.repositories.UserRepository;

@Component
public class AuthenticationProvider implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userFound -> new User(userFound.getUsername(), userFound.getPassword(), Collections.emptyList()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}