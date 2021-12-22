CREATE TABLE users (
	user_id bigint NOT NULL AUTO_INCREMENT,
    fname varchar(100),
    lname varchar(100),
    email varchar(100),
    password varchar(255),
    position varchar(100),
    team_id bigint NOT NULL,
    PRIMARY KEY(user_id)
);

CREATE TABLE projects (
	project_id bigint NOT NULL AUTO_INCREMENT,
    user_id bigint NOT NULL,
    title varchar(30),
    project_description varchar(100),
    readme varchar(1000),
    PRIMARY KEY(project_id)
);

CREATE TABLE tasks (
	task_id bigint NOT NULL AUTO_INCREMENT,
    project_id bigint NOT NULL,
    user_id bigint NOT NULL,
    title varchar(50),
    task_status varchar(20),
    task_description varchar(1000),
    created varchar(100),
    PRIMARY KEY(task_id)
);

CREATE TABLE bugs (
	bug_id bigint NOT NULL AUTO_INCREMENT,
    project_id bigint NOT NULL,
    user_id bigint NOT NULL,
    title varchar(50),
    bug_description varchar(1000),
    created varchar(100),
    bug_status varchar(20),
    affected_feature varchar(100),
    PRIMARY KEY(bug_id)
);

CREATE TABLE teams (
	team_id bigint NOT NULL AUTO_INCREMENT,
    team_name varchar(100),
    project_id bigint NOT NULL,
    working_feature varchar(100),
    PRIMARY KEY(team_id)
);