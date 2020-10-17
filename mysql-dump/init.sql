DROP DATABASE IF EXISTS `petzdb`;

CREATE DATABASE `petzdb`;

USE `petzdb`;

create table cliente (
 id integer not null,
 nome varchar(255), 
 primary key (id));

create table pet (
id integer not null, 
nome varchar(255), 
raca varchar(255), 
dono_id integer, 
primary key (id));

ALTER TABLE pet
	ADD CONSTRAINT FOREIGN KEY (dono_id) REFERENCES cliente(id);

insert into cliente (id, nome) values (90, 'Jose');
insert into cliente (id, nome) values (91, 'João');
insert into pet (id, nome, raca, dono_id) values (1, 'Bidu', 'Boxer',90);
insert into pet (id, nome, raca, dono_id) values (2, 'Rex', 'Poodle',90);
insert into pet (id, nome, raca, dono_id) values (3, 'Pluto', 'Schnauzer',91);
 
 
 
 
