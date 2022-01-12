package org.domain.bugfixmanagement.repository;

import org.domain.bugfixmanagement.entity.Task;
import org.domain.bugfixmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "INSERT INTO users(fname, lname, email, password, position) VALUES(?, ?, ?, ?, ?)", nativeQuery = true)
    User insert_user(String fname, String lname, String email, String password, String position);

    @Query(value = "SELECT * FROM tasks WHERE user_id = ?", nativeQuery = true)
    List<User> getUserByUserId(long projectId);
}
