create table if not exists client_secrets (
    id varchar(36) not null,
    client_id varchar(36),
    secret varchar(255),
    issued_at timestamp(6),
    expires_at timestamp(6),
    primary key (id)
);

alter table if exists client_secrets add constraint FKjoq9qaxbktk6to8hfmhxcapwv foreign key (client_id) references clients;