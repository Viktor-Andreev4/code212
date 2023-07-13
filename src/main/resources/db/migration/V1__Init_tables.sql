CREATE TABLE role(
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

CREATE TABLE user(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    problems_solved INT NOT NULL DEFAULT 0,
    role_id INT NOT NULL REFERENCES role(role_id)
);

CREATE TABLE user_role(
    user_id INT REFERENCES user(user_id),
    role_id INT REFERENCES role(role_id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE difficulty(
    difficulty_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE problem
(
    problem_id    INT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(50) NOT NULL,
    description   TEXT        NOT NULL,
    difficulty_id INT         NOT NULL REFERENCES difficulty (difficulty_id)
);

CREATE TABLE language(
    language_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10)
);
