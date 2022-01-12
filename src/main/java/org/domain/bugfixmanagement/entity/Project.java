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
    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;
    @Column(name = "title")
    private String title;
    @Column(name = "project_description")
    private String description;
    @Column(name = "readme")
    private String readme;

}
