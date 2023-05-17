alter table consultas add status varchar(10) not null;
update consultas set status = "ACTIVE";
alter table consultas add motivo_cancelamento varchar(100);