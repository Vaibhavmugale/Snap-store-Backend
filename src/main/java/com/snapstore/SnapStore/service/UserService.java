package com.snapstore.SnapStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.snapstore.SnapStore.Enity.UserEntity;
import com.snapstore.SnapStore.Request.UserRequest;

@Service
public interface UserService {

    public boolean addUser(UserRequest user);

    public UserRequest getUserById(Integer id);

    public UserEntity getUserByEmail(String emailId);

    public List<UserRequest> getUser();

}
