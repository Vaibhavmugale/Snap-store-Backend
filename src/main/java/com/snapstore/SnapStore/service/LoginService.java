package com.snapstore.SnapStore.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
