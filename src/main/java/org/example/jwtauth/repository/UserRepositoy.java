package org.example.jwtauth.repository;

import org.example.jwtauth.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoy extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
