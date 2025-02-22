create table if not exists clients_scopes (
    client_id varchar(36) not null,
    scope_id varchar(36) not null,
    primary key (client_id, scope_id)
);

alter table if exists clients_scopes add constraint FKdy1ti9yipw52esaj60jtrpmon foreign key (scope_id) references scopes;
alter table if exists clients_scopes add constraint FK9q4o3qvf2c0t8nwi4gjub3wc6 foreign key (client_id) references clients;