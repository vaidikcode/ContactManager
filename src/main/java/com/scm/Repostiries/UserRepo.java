package com.scm.Repostiries;

import com.scm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//to write all the custom related methods and queries to mess with database
public interface UserRepo extends JpaRepository<User,String> {
    //findByField is the custom general syntax to search something in field and write custom writer methods.Spring data jpa implements this method by itself
    Optional<User> findByEmail(String email);
}
