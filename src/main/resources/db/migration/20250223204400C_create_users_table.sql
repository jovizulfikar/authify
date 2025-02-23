create table if not exists users (
    id varchar(36) not null,
    username varchar(36),
    email varchar(50),
    phone_number varchar(15),
    password varchar(255),
    created_at timestamp(6),
    primary key (id)
);

alter table if exists users drop constraint if exists UKr43af9ap4edm43mmtq01oddj6;
alter table if exists users add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username);
