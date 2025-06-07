package com.khaikin.qrest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Long countByRole(Role role);

    @Query(value = "SELECT * FROM users " +
            "WHERE role = :role AND username REGEXP :pattern " +
            "ORDER BY username DESC LIMIT 1", nativeQuery = true)
    Optional<User> findTopValidUsernameByRole(@Param("role") String role, @Param("pattern") String pattern);

}
