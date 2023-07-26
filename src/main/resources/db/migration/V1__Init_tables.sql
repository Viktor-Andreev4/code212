CREATE TABLE role (
    role_id     INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) UNIQUE
);

CREATE TABLE user (
    user_id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    email           VARCHAR(50) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    role_id         INT NOT NULL DEFAULT 1,
    CONSTRAINT FK_user_role FOREIGN KEY (role_id) REFERENCES role(role_id)
);

CREATE TABLE user_role(
    user_id         INT REFERENCES user(user_id),
    role_id         INT REFERENCES role(role_id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE exam (
    exam_id         INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    start_date      DATETIME NOT NULL,
    end_date        DATETIME NOT NULL
);

CREATE TABLE problem(
    problem_id          INT AUTO_INCREMENT PRIMARY KEY,
    title               VARCHAR(255) NOT NULL,
    description         TEXT        NOT NULL,
    input_url           TEXT        NOT NULL,
    output_url          TEXT        NOT NULL
);

CREATE TABLE exam_problem(
    exam_id         INT REFERENCES exam(exam_id),
    problem_id      INT REFERENCES problem(problem_id),
    PRIMARY KEY (exam_id, problem_id)
);

CREATE TABLE grade (
    grade_id            INT AUTO_INCREMENT PRIMARY KEY,
    user_id             INT NOT NULL,
    grade               INT NOT NULL,
    problem_id             INT NOT NULL,
    CONSTRAINT FK_grade_user FOREIGN KEY (user_id) REFERENCES user(user_id),
    CONSTRAINT FK_grade_problem FOREIGN KEY (problem_id) REFERENCES problem(problem_id)
);

CREATE TABLE language(
    language_id   INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(10)
);

CREATE TABLE status (
    status_id       INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(50) UNIQUE
);

CREATE TABLE solution_code(
    code_submitted_id   INT AUTO_INCREMENT PRIMARY KEY,
    code_url            TEXT NOT NULL,
    user_id             INT NOT NULL,
    problem_id          INT NOT NULL,
    language_id         INT NOT NULL,
    status_id           INT NOT NULL,
    CONSTRAINT FK_solution_code_user     FOREIGN KEY (user_id)     REFERENCES user(user_id),
    CONSTRAINT FK_solution_code_problem  FOREIGN KEY (problem_id)  REFERENCES problem(problem_id),
    CONSTRAINT FK_solution_code_language FOREIGN KEY (language_id) REFERENCES language(language_id),
    CONSTRAINT FK_solution_code_status   FOREIGN KEY (status_id)   REFERENCES status(status_id)
);





