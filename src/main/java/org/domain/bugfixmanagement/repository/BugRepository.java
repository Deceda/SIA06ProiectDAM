package org.domain.bugfixmanagement.repository;

import org.domain.bugfixmanagement.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
    @Query(value = "INSERT INTO bugs(project_id, user_id, title, bug_description, created, bug_status) VALUES(?, ?, ?, ?, ?, ?)", nativeQuery = true)
    Bug insert_value(long projectId, long userId, String title, String description, String created, String status);

    @Query(value = "SELECT * FROM bugs WHERE project_id = ?", nativeQuery = true)
    List<Bug> getBugsByProject(long projectId);

    @Query(value = "UPDATE bugs b SET b.bug_status = :status WHERE b.bugId = :id", nativeQuery = true)
    Bug updateBug(@Param(value = "id") long id, @Param(value = "status") String status);
}
