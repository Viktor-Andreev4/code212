INSERT INTO role (name)
VALUES ('user'),
       ('admin');

INSERT INTO language (name)
VALUES ('cpp'),
       ('java'),
       ('py'),
       ('csharp'),
       ('js');

INSERT INTO problem (title, description, input_url, output_url)
VALUES (
           'Two Sum',
           'Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
            You may assume that each input would have exactly one solution, and you may not use the same element twice.
            You can return the answer in any order.',
            'https://leetcode.com/problems/two-sum/',
            'https://leetcode.com/problems/two-sum/'
       );