alter table pacientes add status varchar(10) not null;
update pacientes set status = "ACTIVE";