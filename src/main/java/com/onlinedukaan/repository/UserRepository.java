package com.onlinedukaan.repository;

import com.onlinedukaan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByResetPasswordToken(String resetPasswordToken);
    @Query(value = "SELECT * FROM USERS u WHERE u.email = ?1"
    ,nativeQuery = true)
    User findByEmail(String email);

    @Override
    List<User> findAll();

    @Override
    List<User> findAllById(Iterable<Long> longs);

    @Override
    <S extends User> S save(S entity);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);
}
