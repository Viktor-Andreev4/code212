INSERT INTO role (name)
VALUES ('user'),
       ('admin');

INSERT INTO language (language_id, name)
VALUES (54, 'c++'),
       (62, 'java'),
       (71, 'py'),
       (63, 'js');

INSERT INTO status(status_id, name)
VALUES
    (1, 'In Queue'),
    (2, 'Processing'),
    (3, 'Accepted'),
    (4, 'Wrong Answer'),
    (5, 'Time Limit Exceeded'),
    (6, 'Compilation Error'),
    (7, 'Runtime Error (SIGSEGV)'),
    (8, 'Runtime Error (SIGXFSZ)'),
    (9, 'Runtime Error (SIGFPE)'),
    (10, 'Runtime Error (SIGABRT)'),
    (11, 'Runtime Error (NZEC)'),
    (12, 'Runtime Error (Other)'),
    (13, 'Internal Error'),
    (14, 'Exec Format Error');

