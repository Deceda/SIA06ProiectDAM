package org.domain.bugfixmanagement.model;

import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.repository.BugRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MySQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@Testcontainers
public class BugRepositoryTest {
    @Container
    public static final MySQLContainer<?> mysqlcontainer = new MySQLContainer<>("mysql:5.7.34")
            .withDatabaseName("integration-tests-db")
            .withUsername("aiurea")
            .withPassword("cevaaiurea");

    @DynamicPropertySource
    static void mySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mysqlcontainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlcontainer::getUsername);
        registry.add("spring.datasource.password", mysqlcontainer::getPassword);
    }

    @Autowired
    BugRepository bugRepository;

    private void createData(){
        List<Bug> bugs = List.of(
                new Bug(1L, new Project(), new User(), "titlu", "descriere", "status", "creat"),
                new Bug(2L, new Project(), new User(), "titlu", "descriere", "status", "creat"),
                new Bug(3L, new Project(), new User(), "titlu", "descriere", "status", "creat")
        );

        bugRepository.deleteAll();
        bugRepository.saveAll(bugs);
    }

    @Test
    @Transactional
    public void testeaza(){
        createData();
        List<Bug> results = bugRepository.findAll();
        assertThat(results).hasSize(3);
        assertThat(getBugById(results, 1L)).isPresent();
        assertThat(getBugById(results, 4L)).isNotPresent();
    }

    private Optional<Bug> getBugById(List<Bug> results, Long Id){
        return results.stream().filter(r -> Id.equals(r.getBugId())).findFirst();
    }
}
