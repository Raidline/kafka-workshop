--liquibase formatted sql
-- changeset workshop:1

create table product(
                        id serial primary key,
                        name varchar(250),
                        value int,
                        related int[]
);

create table promo(
                      id serial primary key,
                      value int,
                      product_id int references product(id),
                      options int[]
);

--rollback drop table promo;
--rollback drop table product;
