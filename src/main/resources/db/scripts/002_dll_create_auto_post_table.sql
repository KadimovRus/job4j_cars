create table auto_post
(
    id             serial  primary key,
    description    varchar(255) not null,
    auto_user_id   int  references auto_user (id) not null,
    created        timestamp     not null
)