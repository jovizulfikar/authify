create table if not exists refresh_tokens (
    id varchar(36) not null,
    client_id varchar(255),
    user_id varchar(255),
    token varchar(36),
    created_at timestamp(6),
    expired_at timestamp(6),
    primary key (id)
);

alter table if exists refresh_tokens drop constraint if exists UKghpmfn23vmxfu3spu3lfg4r2d;
alter table if exists refresh_tokens add constraint UKghpmfn23vmxfu3spu3lfg4r2d unique (token);