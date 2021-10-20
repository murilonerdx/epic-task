create table tb_perfil (id  bigserial not null, data bytea, name varchar(255), quantidade_tarefa_concluida int4 not null, quantidade_tarefa_criada int4 not null, score float8 not null, primary key (id));
create table tb_tarefa (id  bigserial not null, date date, description varchar(8000), obtain boolean not null, progress int4, score float8 not null, status_task varchar(255), title varchar(255), perfil_id int8, primary key (id));
create table tb_usuario (id  bigserial not null, email varchar(255), password varchar(255), role varchar(255), perfil_id int8, primary key (id));
alter table tb_perfil add constraint UK_3exb1wgqywtr65tlk7kmqsw7t unique (name);
alter table tb_usuario add constraint UK_spmnyb4dsul95fjmr5kmdmvub unique (email);
alter table tb_tarefa add constraint FK612kknrvxav78h7w91gdq003d foreign key (perfil_id) references tb_perfil;
alter table tb_usuario add constraint FK49avuiu7e76c5xiivsek3yut4 foreign key (perfil_id) references tb_perfil;
