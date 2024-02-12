insert into alarm_type(id, xkey, descripcion) values (2, 'HUM', 'Humedad');
insert into alarm_type(id, xkey, descripcion) values (3, 'DI', 'Dioxido de Carbono');

insert into alarms (id, idTunel, alarm_tpe, alarm, descripcion, severidad) values (1, 101, 2,  parsedatetime('2024-01-02-23.21.59', 'yyyy-MM-dd-hh.mm.ss'), 'Humedad al 10 %', 1);
insert into alarms (id, idTunel, alarm_tpe, alarm, descripcion, severidad) values (2, 102, 2,  parsedatetime('2024-01-02-22.21.59', 'yyyy-MM-dd-hh.mm.ss'), 'Humedad al 60 %', 3);
insert into alarms (id, idTunel, alarm_tpe, alarm, descripcion, severidad) values (3, 104, 2,  parsedatetime('2024-04-10-21.21.59', 'yyyy-MM-dd-hh.mm.ss'), 'Humedad al 90 %', 5);
insert into alarms (id, idTunel, alarm_tpe, alarm, descripcion, severidad) values (4, 107, 3,  parsedatetime('2024-03-10-22.21.59', 'yyyy-MM-dd-hh.mm.ss'), 'Dioxido de Carbono 60 %', 4);
insert into alarms (id, idTunel, alarm_tpe, alarm, descripcion, severidad) values (5, 240, 3,  parsedatetime('2024-04-10-22.21.59', 'yyyy-MM-dd-hh.mm.ss'), 'Dioxido de Carbono 80 %', 5);
insert into alarms (id, idTunel, alarm_tpe, alarm, descripcion, severidad) values (6, 10, 3,  parsedatetime('2024-02-07-22.21.59', 'yyyy-MM-dd-hh.mm.ss'), 'Dioxido de Carbono 20 %', 1);
insert into alarms (id, idTunel, alarm_tpe, alarm, descripcion, severidad) values (7, 14, 2,  parsedatetime('2024-02-07-22.21.59', 'yyyy-MM-dd-hh.mm.ss'), 'Humedad al 60 %', 3);
insert into alarms (id, idTunel, alarm_tpe, alarm, descripcion, severidad) values (8, 107, 3,  parsedatetime('2024-03-10-23.59.59', 'yyyy-MM-dd-hh.mm.ss'), 'Dioxido de Carbono 10 %', 1);