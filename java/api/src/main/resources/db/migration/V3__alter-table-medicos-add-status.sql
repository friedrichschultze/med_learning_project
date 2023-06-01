alter table medicos add status varchar(10) not null;
update medicos set status = "ACTIVE";