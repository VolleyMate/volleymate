-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled,correo) VALUES ('admin1','4dm1n',TRUE,'admin@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled,correo) VALUES ('owner1','0wn3r',TRUE,'owner1@gmail.com');

-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled,correo) VALUES ('vet1','v3t',TRUE,'vet1@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');

INSERT INTO users(username,password,enabled,correo) VALUES ('jorsilman','jorsilman',TRUE,'jorge@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (2,'jorsilman','owner');

INSERT INTO jugadores VALUES (1,'Jorge','Sillero','657236154','jorsilman');
INSERT INTO partidos(id,creador,num_jugadores,lugar,fecha,fecha_creacion) VALUES (1,'jorsilman',3,'Sevilla','2013-01-01','2013-01-01');

