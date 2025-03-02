package com.snapstore.SnapStore.serviceImpl;
import java.util.Optional;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.snapstore.SnapStore.Enity.UserEntity;
import com.snapstore.SnapStore.Repository.UserRepository;
import com.snapstore.SnapStore.service.LoginService;

@Service
public class LoginServiceImpl implements UserDetailsService, LoginService{

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Checking for user with email: " + username);
        
        Optional<UserEntity> userOptional = userRepo.findByEmailId(username);
        
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        UserEntity user = userOptional.get();
        if (user.getUserStatus()==0) {
            throw new UsernameNotFoundException("User blocked to use this website");
        }
        return new User(user.getEmailId(), user.getPassword(), Collections.emptyList());
    }
}
