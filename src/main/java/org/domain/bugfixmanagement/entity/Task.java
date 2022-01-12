package org.domain.bugfixmanagement.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "task_id")
    private Long taskId;
    @JoinColumn(name = "project_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Project project;
    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User userId;
    @Column(name = "title")
    private String title;
    @Column(name = "task_description")
    private String task_description;
    @Column(name = "task_status")
    private String task_status;
    @Column(name = "created")
    private String created;

}
