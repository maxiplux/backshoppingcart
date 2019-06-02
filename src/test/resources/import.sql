/* Populate tabla clientes */

/* Creamos algunos usuarios con sus roles */
INSERT INTO `user` (username, password, enabled, firts_name, last_name, email) VALUES ('andres','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1, 'Andres', 'Guzman','maxiplux@gmail.com');
INSERT INTO `user` (username, password, enabled, firts_name, last_name, email) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',1, 'John', 'Doe','franciscomosquera@outlook.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `user_rols` (user_id, role_id) VALUES (1, 1);
INSERT INTO `user_rols` (user_id, role_id) VALUES (2, 2);
INSERT INTO `user_rols` (user_id, role_id) VALUES (2, 1);

/* Populate tabla productos */
INSERT INTO product (name, price, created_at) VALUES('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO product (name, price, created_at) VALUES('Sony Camara digital DSC-W320B', 123490, NOW());
INSERT INTO product (name, price, created_at) VALUES('Apple iPod shuffle', 1499990, NOW());
INSERT INTO product (name, price, created_at) VALUES('Sony Notebook Z110', 37990, NOW());
INSERT INTO product (name, price, created_at) VALUES('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO product (name, price, created_at) VALUES('Bianchi Bicicleta Aro 26', 69990, NOW());
INSERT INTO product (name, price, created_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());
