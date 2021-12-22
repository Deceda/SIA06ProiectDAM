package org.domain.bugfixmanagement.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bugs")
public class Bug {
    @Id
    @Column(name = "bug_id")
    private Long bugId;
    @Column(name = "project_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Project project;
    @Column(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private User user;
    @Column(name = "title")
    private String title;
    @Column(name = "bug_description")
    private String description;
    @Column(name = "bug_status")
    private String bug_status;
    @Column(name = "created")
    private String created;
}
