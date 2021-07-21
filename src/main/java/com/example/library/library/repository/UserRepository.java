package com.example.library.library.repository;

import com.example.library.library.model.Role;
import com.example.library.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCastom {

    Optional<User> findByLogin(String login);

    @Query("select r from Role r")
    List<Role> getAllRolesForUser();
}
