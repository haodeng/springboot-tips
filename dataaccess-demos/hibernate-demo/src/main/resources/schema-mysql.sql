create table users
(
    id                 bigint    not null primary key,
    created_date       timestamp not null,
    last_modified_date timestamp,
    email              varchar(255),
    enabled            boolean   not null,
    first_name         varchar(255),
    last_name          varchar(255),
    telephone          varchar(255),
    address_1          varchar(255),
    address_2          varchar(255),
    city               varchar(255),
    country            varchar(2),
    postcode           varchar(10)
);

create table categories
(
    id                 bigint       not null primary key,
    created_date       timestamp    not null,
    last_modified_date timestamp,
    description        varchar(255) not null,
    name               varchar(255) not null
);

CREATE TABLE posts
(
    id BIGINT not null primary key,
    created_date       timestamp    not null,
    last_modified_date timestamp,
    name VARCHAR(200),
    category_id        bigint,
    user_id        bigint
);
alter table posts
    add constraint post_fk1 foreign key (category_id) references categories (id);
alter table posts
    add constraint post_fk2 foreign key (user_id) references users (id);

create table comments
(
    id                 bigint       not null primary key,
    created_date       timestamp    not null,
    last_modified_date timestamp,
    description        varchar(255) not null
);

create table posts_comments
(
    post_id bigint not null,
    comments_id bigint not null
);
alter table posts_comments
    add constraint posts_comments_pk primary key (post_id, comments_id);
alter table posts_comments
    add constraint posts_comments_fk1 foreign key (comments_id) references comments (id);
alter table posts_comments
    add constraint posts_comments_fk2 foreign key (post_id) references posts (id);

