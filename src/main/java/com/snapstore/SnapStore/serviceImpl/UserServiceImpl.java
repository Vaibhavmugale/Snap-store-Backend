package com.snapstore.SnapStore.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.snapstore.SnapStore.Enity.UserEntity;
import com.snapstore.SnapStore.Repository.UserRepository;
import com.snapstore.SnapStore.Request.UserRequest;
import com.snapstore.SnapStore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean addUser(UserRequest user) {
        try {
            if (user.getIsEdited()==0 ){
                if( userRepo.existsByEmailId(user.getEmailId())) {
                    return false;
                }
            }
            user.setPassword(user.getPassword2());
            
            UserEntity entity = modelMapper.map(user, UserEntity.class);
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    
            entity.setCreatedDate(currentTime);
            entity.setModifiedDate(currentTime);
            entity.setCreatedBy(user.getCreatedBy() != null ? user.getCreatedBy() : 1);
            entity.setModifiedBy(user.getCreatedBy() != null ? user.getCreatedBy() : 1);
    
            String hashPassword = passwordEncoder.encode(user.getPassword());
            entity.setPassword(hashPassword);
    
            userRepo.save(entity);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

     @Override
    public UserRequest getUserById(Integer id) {
        UserEntity entity = userRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return modelMapper.map(entity, UserRequest.class);
    }

    @Override
    public UserEntity getUserByEmail(String emailId) {
        return userRepo.findByEmailId(emailId)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + emailId));
    }

    @Override
    public List<UserRequest> getUser() {
         List<UserEntity> entities = userRepo.findAll();
        return modelMapper.map(entities, new TypeToken<List<UserRequest>>(){}.getType());  
    }
    
    

}
