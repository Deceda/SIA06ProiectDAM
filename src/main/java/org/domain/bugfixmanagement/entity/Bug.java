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
    @GeneratedValue
    private Long bugId;
    @JoinColumn(name = "project_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Project project;
    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
