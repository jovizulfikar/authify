create table clients (
    id varchar(36) not null,
    client_id varchar(36) not null,
    name varchar(50),
    access_token_ttl bigint,
    refresh_token_ttl bigint,
    issued_at timestamp(6),
    primary key (id)
);

alter table if exists clients drop constraint if exists UK2og8x0i6lngghy4cqupje9dki;
alter table if exists clients add constraint UK2og8x0i6lngghy4cqupje9dki unique (client_id);