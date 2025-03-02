package com.snapstore.SnapStore.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.snapstore.SnapStore.Enity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.emailId = ?1")
    public boolean existsByEmailId(String emailId);

    public Optional<UserEntity> findByEmailId(String username);
}
