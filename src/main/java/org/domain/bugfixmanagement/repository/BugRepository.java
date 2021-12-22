package org.domain.bugfixmanagement.repository;

import org.domain.bugfixmanagement.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {

}
