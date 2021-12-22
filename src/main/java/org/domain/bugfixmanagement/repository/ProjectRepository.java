package org.domain.bugfixmanagement.repository;

import org.domain.bugfixmanagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
