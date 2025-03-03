package com.lcwd.electronics.store.repositories;

import com.lcwd.electronics.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    //custom finder method
   Optional<User> findByEmail(String email);  //entity ka nam findBy and then field name (parameter)..

   Optional<User> findByEmailAndPassword(String email,String password);

   List<User> findByNameContaining(String keyword);


}
