create table if not exists customers
(
    id         bigint primary key,
    created_at timestamp  default current_timestamp,
    updated_at timestamp  default current_timestamp,
    status     varchar(50) default 'ACTIVE',
    firstName  varchar(100),
    lastName   varchar(100),
    birthday   timestamp
);
create table if not exists users
(
    id                       bigint primary key,
    created_at               timestamp   default current_timestamp,
    updated_at               timestamp   default current_timestamp,
    status                   varchar(50)  default 'ACTIVE',
    customer_id              bigint       not null,
    phone_number             varchar(255) not null,
    password                 varchar(255) not null,
    first_name               varchar(50),
    last_name                varchar(50),
    last_password_reset_date timestamp   default current_timestamp,
    user_type                varchar(50),
    user_status              varchar(50)  default 'ACTIVE',
    avatar_url               varchar(256) default '',
    constraint fk_customer foreign key (customer_id) references customers (id) on delete cascade
);
create table if not exists roles
(
    id         bigint primary key,
    name       varchar(100) not null unique,
    created_at timestamp  default current_timestamp,
    updated_at timestamp  default current_timestamp,
    status     varchar(50) default 'ACTIVE'
);
create table if not exists users_roles (
    user_id bigint,
    role_id bigint,
    constraint fk_user foreign key (user_id) references users (id) on delete cascade,
    constraint fk_role foreign key (role_id) references roles (id) on delete cascade
);
insert into roles (id, name) values (1, 'ROLE_USER'),
                                    (2, 'ROLE_SPECIALIST'),
                                    (3, 'ROLE_ADMINISTRATOR'),
                                    (4, 'ROLE_OWNER');
