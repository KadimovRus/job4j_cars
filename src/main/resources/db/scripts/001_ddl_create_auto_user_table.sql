create table auto_user
(
    id       serial primary key,
    login    varchar(255) not null,
    password varchar(255) not null
)