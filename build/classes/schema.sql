drop table products;

create table products (
    id integer not null,
    name varchar not null,
    description varchar,
    category varchar not null,
    price decimal(11,2) not null,
    quantity integer not null,

    constraint pk_products primary key (id)
);
