--liquibase formatted sql
-- changeset workshop:2

insert into product(id, name, value, related) values (1, 'A', 10, '{}');
insert into product(id, name, value, related) values (2, 'B', 10, '{}');
insert into product(id, name, value, related) values (3, 'C', 20, '{}');
insert into product(id, name, value, related) values (4, 'AB', 20, '{1,2}');

insert into promo(id, value, product_id, options) values (1, 10, 4, '{5,15,20}');
insert into promo(id, value, product_id, options) values (2, 5, 3, '{5,15,20}');

--rollback truncate table promo cascade;
--rollback truncate table product cascade;
