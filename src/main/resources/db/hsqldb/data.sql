INSERT INTO users(username,password,enabled,correo) VALUES ('admin1','4dm1n',TRUE,'admin@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys, premium, fecha_fin_premium) VALUES (1,'Admin','Admin','SEVILLA','666666666','MASCULINO','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg?w=2000','admin1', 10000000, TRUE, '2024-01-01');

INSERT INTO users(username,password,enabled,correo) VALUES ('jorsilman','jorsilman',TRUE,'jorge@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (2,'jorsilman','admin');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys, premium, fecha_fin_premium) VALUES (2,'Jorge','Sillero','SEVILLA','657236154','MASCULINO','https://st1.uvnimg.com/e0/40/ab16af48465f804d56e0c2a7ccf4/gettyimages-1240189250.jpg','jorsilman', 1000, TRUE, '2024-01-01');

INSERT INTO users(username,password,enabled,correo) VALUES ('barba','barba',TRUE,'barba@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (3,'barba','admin');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys, premium) VALUES (3,'Francisco Javier','Barba','SEVILLA','666666666','MASCULINO','/resources/images/perfilPorDefecto.png','barba', 250, FALSE);

INSERT INTO users(username,password,enabled,correo) VALUES ('paomarsan','paomarsan',TRUE,'paomarsan@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (4,'paomarsan','admin');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys, premium) VALUES (4,'Paola','Martin','SEVILLA','666666666','FEMENINO','/resources/images/perfilPorDefecto.png','paomarsan', 150, FALSE);

INSERT INTO users(username,password,enabled,correo) VALUES ('meriglmar','meriglmar',TRUE,'meriglmar@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (5,'meriglmar','admin');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys, premium) VALUES (5,'Mercedes','Iglesias','SEVILLA','666666666','FEMENINO','/resources/images/perfilPorDefecto.png','meriglmar', 250, FALSE);

INSERT INTO users(username,password,enabled,correo) VALUES ('alecarnun','alecarnun',TRUE,'alejcn01@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (6,'alecarnun','admin');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys, premium) VALUES (6,'Alejandro','Carrasco','SEVILLA','666666666','MASCULINO','/resources/images/perfilPorDefecto.png','alecarnun', 250, FALSE);

INSERT INTO users(username,password,enabled,correo) VALUES ('jugador','jugador',TRUE,'jugador@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (7,'jugador','jugador');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys, premium) VALUES (7,'jugador','jugador','SEVILLA','666666666','MASCULINO','/resources/images/perfilPorDefecto.png','jugador', 10000000, FALSE);

INSERT INTO users(username,password,enabled,correo) VALUES ('robpazriv','robpazriv',TRUE,'robertopazrivera00@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (8,'robpazriv','jugador');
INSERT INTO jugadores(id, first_name, last_name, ciudad, telephone, sexo, image, username, volleys, premium) VALUES (8,'robpazriv','robpazriv','SEVILLA','628587130','MASCULINO','/resources/images/perfilPorDefecto.png','robpazriv', 10000000, FALSE);

INSERT INTO centros(id,nombre,direccion,ciudad,estado,maps) VALUES (1,'Mairena Voley Club','Av. de la Constitución num 2', 'SEVILLA', true, 'https://goo.gl/maps/P5uyWUKnDqNLAbnE6');
INSERT INTO centros(id,nombre,direccion,ciudad,estado,maps) VALUES (2,'Club Deportivo Claret','C. Monzón', 'SEVILLA', true, 'https://goo.gl/maps/JYErpvDCVSKuNLvF9');
INSERT INTO centros(id,nombre,direccion,ciudad,estado,maps) VALUES (3,'Club Voleibol Esquimo','Cl. Meñaca', 'SEVILLA', true, 'https://goo.gl/maps/ZNFjetB53pX1JMcg6');

INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (1,'Partido amistoso','MASCULINO','Vamos a jugar un partido amistoso',0,'admin1',3,'2024-01-02 17:00','2013-01-01 17:00',150,1);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (2,'Partido de playa','MASCULINO','Vamos a jugar un partido amistoso',5,'jorsilman',3,'2024-01-02 17:00','2013-01-01 17:00',150,2);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (3,'Partido de revancha','MASCULINO','Vamos a jugar un partido amistoso',1,'jorsilman',4,'2013-01-04 17:00','2013-01-03 17:00',150,2);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (4,'Partido competitivo','MASCULINO','Vamos a jugar un partido amistoso',2,'jorsilman',4,'2013-05-02 17:00','2013-05-01 17:00',150,3);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (5,'Partido entre amigos','MASCULINO','Vamos a jugar un partido amistoso',6,'jorsilman',3,'2013-05-03 17:00','2013-05-02 17:00',150,2);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (6,'Partido competitivo','MASCULINO','Vamos a jugar un partido amistoso',2,'barba',4,'2013-05-02 17:00','2013-05-01 17:00',150,3);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (7,'Partido entre amigos','MASCULINO','Vamos a jugar un partido amistoso',6,'barba',3,'2013-05-03 17:00','2013-05-02 17:00',150,2);
INSERT INTO partidos(id,nombre,sexo,descripcion,tipo,creador,num_jugadores,fecha,fecha_creacion,precio_persona,centro) VALUES (8,'Porb delete','MASCULINO','Vamos a jugar un partido amistoso',6,'jugador',3,'2013-05-03 17:00','2013-05-02 17:00',150,2);

INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (1,1);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (2,2);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (2,3);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (2,4);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (2,5);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (3,6);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (3,7);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (2,7);
INSERT INTO jugador_partidos(jugador_id,partido_id) VALUES (7,7);

INSERT INTO mensajes (id,contenido_mensaje,fecha_envio,emisor,partido) VALUES (1,'Hola, ¿alguien quiere jugar?', '2013-01-01 17:00','jorsilman',1);
INSERT INTO mensajes (id,contenido_mensaje,fecha_envio,emisor,partido) VALUES (2,'Hola, ¿alguien quiere jugar?', '2015-01-01 17:00','barba',1);
INSERT INTO mensajes (id,contenido_mensaje,fecha_envio,emisor,partido) VALUES (3,'Hola, ¿alguien quiere jugar?', '2017-01-01 17:00','barba',1);
INSERT INTO mensajes (id,contenido_mensaje,fecha_envio,emisor,partido) VALUES (4,'Hola, ¿alguien quiere jugar?', '2017-01-01 17:01','jugador',7);


INSERT INTO aspectos(id,imagen,precio) VALUES (1,'/resources/images/perfilPorDefecto.png',0);
INSERT INTO aspectos(id,imagen,precio) VALUES (2,'https://img.freepik.com/vector-premium/personaje-dibujos-animados-insignia-bandera-finlandia-jugando-voleibol_152558-37413.jpg?size=626&ext=jpg',0);
INSERT INTO aspectos(id,imagen,precio) VALUES (3,'https://img.freepik.com/vector-premium/ilustracion-diseno-vectores-dibujos-animados-planos-personaje-lindo-volley-ball_570764-3711.jpg',0);
INSERT INTO aspectos(id,imagen,precio) VALUES (4,'https://img.freepik.com/vector-gratis/silueta-voleibol-diseno-plano_23-2149400512.jpg?size=626&ext=jpg',400);
INSERT INTO aspectos(id,imagen,precio) VALUES (5,'https://img.freepik.com/vector-gratis/silueta-voleibol-dibujada-mano_23-2149406906.jpg?w=826&t=st=1681572824~exp=1681573424~hmac=2eed142dff709caa1910ea97112056283b88b6f865d764eb4e22930e7fbf2d21',400);
INSERT INTO aspectos(id,imagen,precio) VALUES (6,'https://img.freepik.com/vector-premium/jugador-voleibol-lindo-que-sostiene-ejemplo-icono-vector-historieta-bola-vector-aislado-concepto-icono-deportes-estilo-dibujos-animados-plana_422763-86.jpg?size=626&ext=jpg',400);
INSERT INTO aspectos(id,imagen,precio) VALUES (7,'https://img.freepik.com/vector-premium/caricatura-persona-sosteniendo-pelota_494525-740.jpg?size=626&ext=jpg',400);
INSERT INTO aspectos(id,imagen,precio) VALUES (8,'https://img.freepik.com/vector-premium/caricatura-personaje-bola-nieve-jugando-voleibol-diseno-estilo-lindo-camiseta-pegatina-elemento-logotipo_152558-18204.jpg?size=626&ext=jpg',400);

INSERT INTO aspectos_jugador(jugador_id, aspecto_id) VALUES (1,1);
INSERT INTO aspectos_jugador(jugador_id, aspecto_id) VALUES (2,2);
INSERT INTO aspectos_jugador(jugador_id, aspecto_id) VALUES (3,3);
INSERT INTO aspectos_jugador(jugador_id, aspecto_id) VALUES (4,4);
INSERT INTO aspectos_jugador(jugador_id, aspecto_id) VALUES (5,5);
INSERT INTO aspectos_jugador(jugador_id, aspecto_id) VALUES (6,6);
INSERT INTO aspectos_jugador(jugador_id, aspecto_id) VALUES (7,7);

INSERT INTO logros(id,nombre,descripcion,imagen,threshold,metrica) VALUES (1,'Primer partido','Ha jugado un partido en la aplicacion','/resources/images/bronce.png',1,'partidos');
INSERT INTO logros(id,nombre,descripcion,imagen,threshold,metrica) VALUES (2,'Cinco partidos','Ha jugado cinco partidos en la aplicacion','/resources/images/plata.png',5,'partidos');
INSERT INTO logros(id,nombre,descripcion,imagen,threshold,metrica) VALUES (3,'Diez partidos','Ha jugado diez partidos en la aplicacion','/resources/images/oro.png',10,'partidos');
INSERT INTO logros(id,nombre,descripcion,imagen,threshold,metrica) VALUES (4,'Aprobado','Ha alcanzado un aprobado','/resources/images/bronce.png',3,'valoracion');

insert into logros_jugador(id_jugador,id_logro) values (7,1);

insert into solicitudes(id, jugador_id, partido_id) values (1, 7, 1);

INSERT INTO valoracion(id, comentario, puntuacion, rated_player_id, rating_player_id) VALUES (1,'Muy buen jugador', 5, 1, 7);
INSERT INTO valoracion(id, comentario, puntuacion, rated_player_id, rating_player_id) VALUES (2,'Muy buen jugador', 5, 7, 1);


