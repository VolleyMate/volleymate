-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled,correo) VALUES ('admin1','4dm1n',TRUE,'admin@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
INSERT INTO jugadores VALUES (2,'Admin','Admin','Sevilla','','MASCULINO','666666666','admin1');

INSERT INTO users(username,password,enabled,correo) VALUES ('jorsilman','jorsilman',TRUE,'jorge@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (2,'jorsilman','owner');
INSERT INTO jugadores VALUES (1,'Jorge','Sillero','Sevilla','https://st1.uvnimg.com/e0/40/ab16af48465f804d56e0c2a7ccf4/gettyimages-1240189250.jpg','MASCULINO','657236154','jorsilman');

INSERT INTO users(username,password,enabled,correo) VALUES ('barba','barba',TRUE,'barba@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (3,'barba','admin');
INSERT INTO jugadores VALUES (3,'Barba','Barba','Sevilla','','MASCULINO','666666666','barba');


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