package com.bl.lms.repository;

import com.bl.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from user_details u where u.first_name = ?1")
    User findByFirstName(String first_name);

    @Query("select u from user_details u where u.email = ?1")
    User findByEmail(String email);
}
