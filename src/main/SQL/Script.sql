create or replace table user
(
    id     int auto_increment
        primary key,
    nom    varchar(50)  not null,
    prenom varchar(50)  not null,
    tel    varchar(50)  not null,
    mail   varchar(50)  not null,
    h_mdp  text         not null,
    salt   varchar(100) not null,
    apport float        not null,
    constraint user_pk2
        unique (mail)
);

create or replace table wallet_user
(
    id        int auto_increment
        primary key,
    name      varchar(50) not null,
    mail_user varchar(50) not null,
    totale    float       not null,
    constraint wallet_user_user_mail_fk
        foreign key (mail_user) references user (mail)
);

create or replace table transaction
(
    id_wallet int                        not null,
    value     float                      null,
    date      date                       not null,
    type      enum ('actions', 'crypto') not null,
    constraint wallet_value_wallet_user_id_fk
        foreign key (id_wallet) references wallet_user (id)
);

