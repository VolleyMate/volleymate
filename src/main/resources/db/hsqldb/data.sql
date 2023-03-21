-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled,correo) VALUES ('admin1','4dm1n',TRUE,'admin@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys) VALUES (2,'Admin','Admin','Sevilla','666666666','MASCULINO','','admin1', 10000000);

INSERT INTO users(username,password,enabled,correo) VALUES ('jorsilman','jorsilman',TRUE,'jorge@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (2,'jorsilman','admin');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys) VALUES (1,'Jorge','Sillero','Sevilla','657236154','MASCULINO','https://st1.uvnimg.com/e0/40/ab16af48465f804d56e0c2a7ccf4/gettyimages-1240189250.jpg','jorsilman', 1000);

INSERT INTO users(username,password,enabled,correo) VALUES ('barba','barba',TRUE,'barba@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (3,'barba','admin');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys) VALUES (3,'Francisco Javier','Barba','Sevilla','666666666','MASCULINO','','barba', 250);

INSERT INTO users(username,password,enabled,correo) VALUES ('paomarsan','paomarsan',TRUE,'paomarsan@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (4,'paomarsan','admin');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys) VALUES (4,'Paola','Martin','Sevilla','666666666','FEMENINO','','paomarsan', 100);

INSERT INTO users(username,password,enabled,correo) VALUES ('meriglmar','meriglmar',TRUE,'meriglmar@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (5,'meriglmar','admin');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys) VALUES (5,'Mercedes','Iglesias','Sevilla','666666666','FEMENINO','','meriglmar', 250);

INSERT INTO users(username,password,enabled,correo) VALUES ('yalejandro9','yalejandro9',TRUE,'yalejandro9@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (5,'yalejandro9','jugador');
INSERT INTO jugadores VALUES (5,'Yanira','Alejandro','Sevilla','','FEMENINO','666666666','yalejandro9');

INSERT INTO users(username,password,enabled,correo) VALUES ('albertsalazarcaballero','albertsalazarcaballero',TRUE,'albertsalazarcaballero@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (6,'albertsalazarcaballero','jugador');
INSERT INTO jugadores VALUES (6,'Alberto','Salazar','Sevilla','','MASCULINO','666666666','albertsalazarcaballero');

INSERT INTO users(username,password,enabled,correo) VALUES ('javiercavlop','javiercavlop',TRUE,'javiercavlop@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (7,'javiercavlop','jugador');
INSERT INTO jugadores VALUES (7,'Javier','Cavazos','Sevilla','','MASCULINO','666666666','javiercavlop');

INSERT INTO users(username,password,enabled,correo) VALUES ('pachecomarquezjavier','pachecomarquezjavier',TRUE,'pachecomarquezjavier@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (8,'pachecomarquezjavier','jugador');
INSERT INTO jugadores VALUES (8,'Javier','Pacheco','Sevilla','','MASCULINO','666666666','pachecomarquezjavier');

INSERT INTO users(username,password,enabled,correo) VALUES ('juangarciaoliva163','juangarciaoliva163',TRUE,'juangarciaoliva163@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (9,'juangarciaoliva163','jugador');
INSERT INTO jugadores VALUES (9,'Juan','Garcia','Sevilla','','MASCULINO','666666666','juangarciaoliva163');

INSERT INTO users(username,password,enabled,correo) VALUES ('vicentect10','vicentect10',TRUE,'vicentect10@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (10,'vicentect10','jugador');
INSERT INTO jugadores VALUES (10,'Vicente','Castillo','Sevilla','','MASCULINO','666666666','vicentect10');

INSERT INTO users(username,password,enabled,correo) VALUES ('cargarpas1','cargarpas1',TRUE,'cargarpas1@alum.us.es');
INSERT INTO authorities(id,username,authority) VALUES (11,'cargarpas1','jugador');
INSERT INTO jugadores VALUES (11,'Carla','Garcia','Sevilla','','FEMENINO','666666666','cargarpas1');

INSERT INTO users(username,password,enabled,correo) VALUES ('josgarmar31','josgarmar31',TRUE,'josgarmar31@alum.us.es');
INSERT INTO authorities(id,username,authority) VALUES (12,'josgarmar31','jugador');
INSERT INTO jugadores VALUES (12,'Jose','Garcia','Sevilla','','MASCULINO','666666666','josgarmar31');

INSERT INTO users(username,password,enabled,correo) VALUES ('maricarmen2g98','maricarmen2g98',TRUE,'maricarmen2g98@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (13,'maricarmen2g98','jugador');
INSERT INTO jugadores VALUES (13,'Maria Carmen','Garcia','Sevilla','','FEMENINO','666666666','maricarmen2g98');

INSERT INTO users(username,password,enabled,correo) VALUES ('joseluissfc4','joseluissfc4',TRUE,'joseluissfc4@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (14,'joseluissfc4','jugador');
INSERT INTO jugadores VALUES (14,'Jose Luis','Sanchez','Sevilla','','MASCULINO','666666666','joseluissfc4');

INSERT INTO users(username,password,enabled,correo) VALUES ('javiborregocaraballo4','javiborregocaraballo4',TRUE,'javiborregocaraballo4@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (15,'javiborregocaraballo4','jugador');
INSERT INTO jugadores VALUES (15,'Javier','Borrego','Sevilla','','MASCULINO','666666666','javiborregocaraballo4');

INSERT INTO users(username,password,enabled,correo) VALUES ('rafaestrada3','rafaestrada3',TRUE,'rafaestrada3@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (16,'rafaestrada3','jugador');
INSERT INTO jugadores VALUES (16,'Rafael','Estrada','Sevilla','','MASCULINO','666666666','rafaestrada3');

INSERT INTO users(username,password,enabled,correo) VALUES ('carlosnuchera98','carlosnuchera98',TRUE,'carlosnuchera98@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (17,'carlosnuchera98','jugador');
INSERT INTO jugadores VALUES (17,'Carlos','Nuchera','Sevilla','','MASCULINO','666666666','carlosnuchera98');

INSERT INTO users(username,password,enabled,correo) VALUES ('patriaguiro35','patriaguiro35',TRUE,'patriaguiro35@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (18,'patriaguiro35','jugador');
INSERT INTO jugadores VALUES (18,'Patricia','Aguirre','Sevilla','','FEMENINO','666666666','patriaguiro35');



INSERT INTO users(username,password,enabled,correo) VALUES ('profesor1','profesor1',TRUE,'profesor1@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (19,'profesor1','jugador');
INSERT INTO jugadores VALUES (19,'profesor1','profesor1','Sevilla','','FEMENINO','666666666','profesor1');

INSERT INTO users(username,password,enabled,correo) VALUES ('profesor2','profesor2',TRUE,'profesor2@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (20,'profesor2','jugador');
INSERT INTO jugadores VALUES (20,'profesor2','profesor2','Sevilla','','FEMENINO','666666666','profesor2');

INSERT INTO users(username,password,enabled,correo) VALUES ('albatinajeroherrera','albatinajeroherrera',TRUE,'albatinajeroherrera@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (21,'albatinajeroherrera','jugador');
INSERT INTO jugadores VALUES (21,'Alba','Tinajero','Sevilla','','FEMENINO','666666666','albatinajeroherrera');

INSERT INTO users(username,password,enabled,correo) VALUES ('daniel0diiaz','daniel0diiaz',TRUE,'daniel0diiaz@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (22,'daniel0diiaz','jugador');
INSERT INTO jugadores VALUES (22,'Daniel','Diaz','Sevilla','','MASCULINO','666666666','daniel0diiaz');


INSERT INTO centros(id,nombre,direccion,ciudad,maps) VALUES (1,'Mairena Voley Club','Av. de la Constitución num 2', 'Sevilla','https://goo.gl/maps/P5uyWUKnDqNLAbnE6');
INSERT INTO centros(id,nombre,direccion,ciudad,maps) VALUES (2,'Club Deportivo Claret','C. Monzón', 'Sevilla','https://goo.gl/maps/JYErpvDCVSKuNLvF9');
INSERT INTO centros(id,nombre,direccion,ciudad,maps) VALUES (3,'Club Voleibol Esquimo','Cl. Meñaca', 'Sevilla','https://goo.gl/maps/ZNFjetB53pX1JMcg6');

INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (1,'Partido amistoso','MASCULINO','Vamos a jugar un partido amistoso',0,'jorsilman',3,'2013-01-02 17:00','2013-01-01 17:00',150,1);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (2,'Partido de playa','MASCULINO','Vamos a jugar un partido amistoso',5,'admin1',3,'2013-01-02 17:00','2013-01-01 17:00',150,2);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (3,'Partido de revancha','MASCULINO','Vamos a jugar un partido amistoso',1,'jorsilman',4,'2013-01-04 17:00','2013-01-03 17:00',150,2);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (4,'Partido competitivo','MASCULINO','Vamos a jugar un partido amistoso',2,'jorsilman',4,'2013-05-02 17:00','2013-05-01 17:00',150,3);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (5,'Partido entre amigos','MASCULINO','Vamos a jugar un partido amistoso',6,'jorsilman',3,'2013-05-03 17:00','2013-05-02 17:00',150,2);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (6,'Partido competitivo','MASCULINO','Vamos a jugar un partido amistoso',2,'barba',4,'2013-05-02 17:00','2013-05-01 17:00',150,3);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (7,'Partido entre amigos','MASCULINO','Vamos a jugar un partido amistoso',6,'barba',3,'2013-05-03 17:00','2013-05-02 17:00',150,2);

INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (1,1);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (1,3);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (1,4);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (1,5);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (2,2);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (3,6);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (3,7);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (3,1);

INSERT INTO mensajes (id,contenido_mensaje,fecha_envio,emisor,partido) VALUES (1,'Hola, ¿alguien quiere jugar?', '2013-01-01 17:00','jorsilman',1);
INSERT INTO mensajes (id,contenido_mensaje,fecha_envio,emisor,partido) VALUES (2,'Hola, ¿alguien quiere jugar?', '2015-01-01 17:00','barba',1);
INSERT INTO mensajes (id,contenido_mensaje,fecha_envio,emisor,partido) VALUES (3,'Hola, ¿alguien quiere jugar?', '2017-01-01 17:00','barba',1);
