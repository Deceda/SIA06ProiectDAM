package org.domain.bugfixmanagement.repository;

import org.domain.bugfixmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

}
