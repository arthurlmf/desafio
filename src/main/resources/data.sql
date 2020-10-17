DELETE  FROM  pet;
DELETE  FROM  cliente;

insert into cliente (id, nome) values (90, 'Jose');
insert into cliente (id, nome) values (91, 'João');
insert into cliente (id, nome) values (93, 'Italo');
insert into pet (id, nome, raca, dono_id) values (1, 'Bidu', 'Boxer',90);
insert into pet (id, nome, raca, dono_id) values (2, 'Rex', 'Poodle',90);
insert into pet (id, nome, raca, dono_id) values (3, 'Pluto', 'Schnauzer',91);