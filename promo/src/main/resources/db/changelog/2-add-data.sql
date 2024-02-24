--liquibase formatted sql
-- changeset workshop-promo:2

insert into promo(id, value, product_id, options) values (1, 10, 4, '{5,15,20}');
insert into promo(id, value, product_id, options) values (2, 5, 3, '{5,15,20}');

--rollback truncate table promo;
