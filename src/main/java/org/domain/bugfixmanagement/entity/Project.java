package org.domain.bugfixmanagement.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
public class Project {
    @Id
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private User user;
    @Column(name = "title")
    private String title;
    @Column(name = "project_description")
    private String description;
    @Column(name = "readme")
    private String readme;

}
