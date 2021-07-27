create table if not exists book
(
    id       int4 not null,
    author   varchar(35),
    quantity int4,
    title    varchar(35),
    primary key (id)
);
create table if not exists book_record
(
    id                 int4 not null,
    approving_admin_id varchar(255),
    book_id            int4,
    remarks            varchar(150),
    return_date_time   TIMESTAMP,
    request_date_time  TIMESTAMP,
    status             varchar(255),
    unit_book          varchar(255),
    username           varchar(255),
    primary key (id)
);
create table if not exists unit_book
(
    unit_book_id   varchar(255) not null,
    assigned       boolean,
    book_parent_id int4,
    primary key (unit_book_id)
);
create table if not exists userr
(
    username varchar(255) not null,
    active   boolean      not null,
    name     varchar(25),
    password varchar(255),
    roles    varchar(255),
    primary key (username)
);
