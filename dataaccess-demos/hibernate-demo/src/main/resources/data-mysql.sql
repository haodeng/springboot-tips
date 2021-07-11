insert into categories
values (1, current_timestamp, current_timestamp, 'Java language', 'Java'),
       (2, current_timestamp, current_timestamp, 'Java springboot framework', 'Springboot'),
       (3, current_timestamp, current_timestamp, 'kubenates', 'k8s');

insert into users
values (4, current_timestamp, current_timestamp, 'test1@test.dk', TRUE, 'Test', '1', '123456', 'addr1', 'addr2', 'cph', 'dk', '2100'),
       (5, current_timestamp, current_timestamp, 'test2@test.dk', TRUE, 'Test', '2', '123457', 'addr1', 'addr2', 'cph', 'dk', '2100');

insert into posts
values (6, current_timestamp, current_timestamp, 'Java language intro 1', 1, 4),
       (7, current_timestamp, current_timestamp, 'springboot up', 2, 4),
       (8, current_timestamp, current_timestamp, 'springboot with k8s part1', 3, 5),
       (9, current_timestamp, current_timestamp, 'springboot with k8s part2', 3, 5),
       (10, current_timestamp, current_timestamp, 'springboot with k8s part3', 3, 5);

insert into comments
values (11, current_timestamp, current_timestamp, 'comment 1'),
       (12, current_timestamp, current_timestamp, 'comment 2'),
       (13, current_timestamp, current_timestamp, 'comment 3'),
       (14, current_timestamp, current_timestamp, 'comment 4');

insert into posts_comments
values (6, 11),
       (6, 12),
       (6, 13),
       (7, 14);