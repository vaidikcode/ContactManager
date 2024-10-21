package com.scm.UserServiceImplementations;

import com.scm.Repostiries.UserRepo;
import com.scm.Services.UserServices;
import com.scm.helpers.ApplicationsConstant;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImp implements UserServices {
    @Autowired
    private UserRepo  repo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public User saveUser(User user) {
        //generate random random unique id before storing the user in db
        String userId= UUID.randomUUID().toString();
        user.setUserId(userId);
        //encoding the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //setting the user role
        user.setRoleList(List.of(ApplicationsConstant.ROLE_USER));
        return repo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return repo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User newUser = repo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        // update krenge user ko new user se
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setAbout(user.getAbout());
        newUser.setProfilePic(user.getProfilePic());
        newUser.setEnabled(user.isEnabled());
        newUser.setEmailVerified(user.isEmailVerified());
        newUser.setPhoneVerified(user.isPhoneVerified());
        newUser.setProviders(user.getProviders());
        newUser.setProviderUserId(user.getProviderUserId());
        //save the user in database
        User saveduser = repo.save(newUser);
        return Optional.ofNullable(saveduser);
    }

    @Override
    public void deleteUser(String id) {
        User newUser = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        repo.delete(newUser);
    }
    //CRITICAL
    @Override
    public boolean isUserExist(String id) {
        User newUser = repo.findById(id).orElse(null);
        return newUser!=null ? true : false;
    }

    @Override
    public boolean isUserExistbyEmail(String email) {
        User user1 = repo.findByEmail(email).orElse(null);
        return user1!=null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User getByEmail(String username) {
        User newuser = repo.findByEmail(username).orElseThrow(null);
        return newuser;
    }
}
