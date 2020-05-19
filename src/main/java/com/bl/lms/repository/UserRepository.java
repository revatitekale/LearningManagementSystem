package com.bl.lms.repository;

import com.bl.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByFirstName(String first_name);

    User findByEmail(String email);
}
