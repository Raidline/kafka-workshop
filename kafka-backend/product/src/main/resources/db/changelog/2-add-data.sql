--liquibase formatted sql
-- changeset workshop:2

insert into product(id, name, value, related) values (1, 'A', 100, '{}');
insert into product(id, name, value, related) values (2, 'B', 100, '{}');
insert into product(id, name, value, related) values (3, 'C', 200, '{}');
insert into product(id, name, value, related) values (4, 'AB', 200, '{1,2}');

insert into promo(id, value, product_id, options) values (1, 10, 4, '{5,15,20}');
insert into promo(id, value, product_id, options) values (2, 5, 3, '{5,15,20}');

--rollback truncate table promo cascade;
--rollback truncate table product cascade;
