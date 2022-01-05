package org.domain.bugfixmanagement.repository;

import org.domain.bugfixmanagement.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {

}
