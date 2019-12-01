package com.ambulance.security;

import com.ambulance.domain.hibernate.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class AmbulanceUserDetailsService implements UserDetailsService {

    private final UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.users.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User: '" + username + "' does not exist."));
    }
}
