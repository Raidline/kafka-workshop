--liquibase formatted sql
-- changeset workshop:1

create table promo(
    id serial primary key,
    value int,
    product_id int,
    options int[]
);

--rollback drop table promo;

