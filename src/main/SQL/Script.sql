create or replace table user
(
    id     int auto_increment
        primary key,
    nom    varchar(50) not null,
    prenom varchar(50) not null,
    tel    varchar(50) not null,
    mail   varchar(50) not null,
    h_mdp  text        not null,
    constraint user_pk2
        unique (mail)
);

create or replace table wallet_user
(
    id        int auto_increment
        primary key,
    name      varchar(50)                                not null,
    mail_user varchar(50)                                not null,
    type      enum ('action', 'crypto') default 'action' null,
    constraint wallet_user_user_mail_fk
        foreign key (mail_user) references user (mail)
);

create or replace table wallet_value
(
    id_wallet int            not null,
    value     decimal(10, 2) null,
    date      date           not null,
    constraint wallet_value_wallet_user_id_fk
        foreign key (id_wallet) references wallet_user (id)
);

