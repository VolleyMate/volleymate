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


INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,lugar,fecha,fecha_creacion,precio_persona) VALUES (1,'Partido amistoso','MASCULINO','Vamos a jugar un partido amistoso',0,'jorsilman',3,'Sevilla','2013-01-02 17:00','2013-01-01 17:00',0);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,lugar,fecha,fecha_creacion,precio_persona) VALUES (2,'Partido de playa','MASCULINO','Vamos a jugar un partido amistoso',5,'admin1',3,'Sevilla','2013-01-02 17:00','2013-01-01 17:00',0);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,lugar,fecha,fecha_creacion,precio_persona) VALUES (3,'Partido de revancha','MASCULINO','Vamos a jugar un partido amistoso',1,'jorsilman',4,'Sevilla','2013-01-04 17:00','2013-01-03 17:00',0);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,lugar,fecha,fecha_creacion,precio_persona) VALUES (4,'Partido competitivo','MASCULINO','Vamos a jugar un partido amistoso',2,'jorsilman',4,'Sevilla','2013-05-02 17:00','2013-05-01 17:00',0);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,lugar,fecha,fecha_creacion,precio_persona) VALUES (5,'Partido entre amigos','MASCULINO','Vamos a jugar un partido amistoso',6,'jorsilman',3,'Sevilla','2013-05-03 17:00','2013-05-02 17:00',0);

INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,lugar,fecha,fecha_creacion,precio_persona) VALUES (6,'Partido competitivo','MASCULINO','Vamos a jugar un partido amistoso',2,'barba',4,'Sevilla','2013-05-02 17:00','2013-05-01 17:00',0);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,lugar,fecha,fecha_creacion,precio_persona) VALUES (7,'Partido entre amigos','MASCULINO','Vamos a jugar un partido amistoso',6,'barba',3,'Sevilla','2013-05-03 17:00','2013-05-02 17:00',0);

INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (1,1);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (1,3);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (1,4);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (1,5);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (2,2);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (3,6);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (3,7);