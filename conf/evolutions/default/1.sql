# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table common_pit (
  pit_id                    varchar(255) not null,
  player_id                 integer not null,
  leaves_qty                integer,
  index                     integer,
  constraint pk_common_pit primary key (pit_id))
;

create table game (
  id                        varchar(255) not null,
  active_player_name        varchar(255),
  constraint pk_game primary key (id))
;

create table grava_hal (
  pit_id                    varchar(255) not null,
  leaves_qty                integer,
  constraint pk_grava_hal primary key (pit_id))
;

create table player (
  id                        integer not null,
  game_id                   varchar(255) not null,
  name                      varchar(255),
  grava_hal_pit_id          varchar(255),
  constraint pk_player primary key (id))
;

create sequence common_pit_seq;

create sequence game_seq;

create sequence grava_hal_seq;

create sequence player_seq;

alter table common_pit add constraint fk_common_pit_player_1 foreign key (player_id) references player (id) on delete restrict on update restrict;
create index ix_common_pit_player_1 on common_pit (player_id);
alter table player add constraint fk_player_game_2 foreign key (game_id) references game (id) on delete restrict on update restrict;
create index ix_player_game_2 on player (game_id);
alter table player add constraint fk_player_gravaHal_3 foreign key (grava_hal_pit_id) references grava_hal (pit_id) on delete restrict on update restrict;
create index ix_player_gravaHal_3 on player (grava_hal_pit_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists common_pit;

drop table if exists game;

drop table if exists grava_hal;

drop table if exists player;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists common_pit_seq;

drop sequence if exists game_seq;

drop sequence if exists grava_hal_seq;

drop sequence if exists player_seq;

