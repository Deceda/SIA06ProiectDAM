package org.domain.bugfixmanagement.repository;

import org.domain.bugfixmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {

}
