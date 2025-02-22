create table if not exists client_grant_types (
    client_id varchar(36) not null,
    grant_type varchar(50)
);

alter table if exists client_grant_types add constraint FKqr55dq5fnb0fq2l3gu6bafc94 foreign key (client_id) references clients;