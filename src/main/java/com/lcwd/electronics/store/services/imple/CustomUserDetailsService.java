//package com.lcwd.electronics.store.services.imple;
//
//import com.lcwd.electronics.store.entities.User;
//import com.lcwd.electronics.store.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.core.userdetails.UserDetails;
////import org.springframework.security.core.userdetails.UserDetailsService;
////import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user with given email not found!!"));
//        return  user;
//
//    }
//}
