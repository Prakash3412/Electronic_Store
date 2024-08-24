//package com.lcwd.electronics.store.controllers;
//
//import com.lcwd.electronics.store.dtos.UserDto;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private ModelMapper mapper;
//
////    @GetMapping("/current")
////    public ResponseEntity<String> getCurrentUser(Principal principal) //with help of we can fetch current user login name
////    {
////        String name = principal.getName();
////        return new ResponseEntity<>(name, HttpStatus.OK);
////    }
//
//    //we want to all details of users then we will use UserDetails
//
////    @GetMapping("/current")
////    public ResponseEntity<UserDetails> getCurrentUser(Principal principal)
////    {
////        String name = principal.getName();
////        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
////        return  new ResponseEntity<>(userDetails,HttpStatus.OK);
////    }
//
//    @GetMapping("/current")
//    public ResponseEntity<UserDto> getCurrentUser(Principal principal)
//    {
//        String name = principal.getName();
//        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
//        return  new ResponseEntity<>(mapper.map(userDetails,UserDto.class),HttpStatus.OK);
//    }
//
//
//}
