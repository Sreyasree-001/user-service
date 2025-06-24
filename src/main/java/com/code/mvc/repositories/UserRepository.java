package com.code.mvc.repositories;

import com.code.mvc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    Optional<User> findById(long id);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);
}





//    @Query("SELECT u FROM User u WHERE u.username = :username") // JPQL
//    Optional<User> findByUserName(@Param("username") String userName);
//
//    @Query("SELECT u FROM User u WHERE u.email = :email")
//    Optional<User> findByEmail(@Param("email") String name);
//
//    @Query("SELECT u FROM User u WHERE u.id = :id")
//    Optional<User> findByID(@Param("id") Long id);
//
//    @Query("SELECT CASE WHEN COUNT(u) > 0 " +
//            "THEN true " +
//            "ELSE false " +
//            "END FROM User u " +
//            "WHERE u.username = :username")
//    Boolean existsByUserName(@Param("username") String userName);
//
//    @Query("SELECT CASE WHEN COUNT(u) > 0 " +
//            "THEN true " +
//            "ELSE false " +
//            "END FROM User u WHERE u.email = :email")
//    Boolean existsByEmail(@Param("email") String email);
//
//    @Query("SELECT CASE WHEN COUNT(u) > 0 " +
//            "THEN true " +
//            "ELSE false " +
//            "END FROM User u WHERE u.phone_number = :phone_number")
//    Boolean existsByPhoneNumber(@Param("phone_number") String phone);