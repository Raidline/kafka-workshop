--liquibase formatted sql
-- changeset workshop:3

create table job_record (
  id int primary key,
  job_name varchar(255),
  job_status varchar(255),
  wanted_value int
);
