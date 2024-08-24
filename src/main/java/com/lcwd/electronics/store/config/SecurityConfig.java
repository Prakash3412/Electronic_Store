//package com.lcwd.electronics.store.config;
//
//import jakarta.servlet.FilterChain;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.net.http.HttpRequest;
//
//@Configuration
//public class SecurityConfig {     // in old we extends webMvcConfigureAdapter but now no need
//
//    @Autowired  // now we can directly used
//    private UserDetailsService userDetailsService;
//
//
//    //Used UserDetailsService interface
//
////    @Bean
////    public UserDetailsService userDetailsService()
////    {
////
////        UserDetails normal = User.builder()
////                .username("prakash")
////                .password(passwordEncoder().encode("ankit"))
////                .roles("NORMAL")
////                .build();
////
////        UserDetails admin = User.builder()
////                .username("bablu")
////                .password(passwordEncoder().encode("babli"))
////                .roles("ADMIN")
////                .build();
////        //user create
////
////        //InMemoryUserDetailsManager ->implementation class of UserDetailsService interface we need object and interface we cant create object
////
////        return  new InMemoryUserDetailsManager(admin,normal); //in constructor we need to we have to pass userDetails users so we need users
////    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }
//
//
//    //we need to Encode password
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    //Basic Authentication in spring security\
//    @Bean
//   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//       http
//        .authorizeRequests()
//               .anyRequest()
//               .authenticated()
//               .and()
//               .httpBasic(Customizer.withDefaults());
//                return http.build();
//
//   }
//
//}
