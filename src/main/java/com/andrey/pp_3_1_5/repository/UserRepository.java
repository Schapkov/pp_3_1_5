package com.andrey.pp_3_1_5.repository;

import com.andrey.pp_3_1_5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User user join fetch user.roles where user.username = :username")
    User findByUsername(@Param("username") String username);




}






