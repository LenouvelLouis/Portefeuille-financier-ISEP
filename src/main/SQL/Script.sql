create or replace table crypto
(
    nom   varchar(50)      not null
        primary key,
    value double default 0 null
);

create or replace table entreprise
(
    nom   varchar(50)      not null
        primary key,
    value double default 0 null
);


INSERT INTO entreprise (nom, value) VALUES ('Apple', 186.4);
INSERT INTO entreprise (nom, value) VALUES ('Google', 132.59);
INSERT INTO entreprise (nom, value) VALUES ('Tesla', 214.65);
INSERT INTO crypto (nom, value) VALUES ('Binancecoin', 15.79);
INSERT INTO crypto (nom, value) VALUES ('Bitcoin', 42675);
INSERT INTO crypto (nom, value) VALUES ('Cardano', 0.528285);
INSERT INTO crypto (nom, value) VALUES ('Chainlink', 69.51);
INSERT INTO crypto (nom, value) VALUES ('Ethereum', 2540.14);
INSERT INTO crypto (nom, value) VALUES ('Litecoin', 0.571183);
INSERT INTO crypto (nom, value) VALUES ('Polkadot', 7.34);
INSERT INTO crypto (nom, value) VALUES ('Ripple', 309.66);
INSERT INTO crypto (nom, value) VALUES ('Stellar', 0.118963);

create or replace table user
(
    id     int auto_increment
        primary key,
    nom    varchar(50)     not null,
    prenom varchar(50)     not null,
    tel    varchar(50)     not null,
    mail   varchar(50)     not null,
    h_mdp  text            not null,
    salt   varchar(100)    not null,
    apport float default 0 not null,
    constraint mail
        unique (mail)
);

create or replace table wallet_user
(
    id            int auto_increment
        primary key,
    name          varchar(50)     not null,
    totale        float default 0 not null,
    id_user       int             not null,
    totale_action float default 0 null,
    totale_crypto float default 0 null,
    constraint wallet_user_pk
        unique (name, id_user),
    constraint wallet_user_user_id_fk
        foreign key (id_user) references user (id)
);

create or replace table transaction
(
    id_wallet    int                        not null,
    value        float                      null,
    date         timestamp                  not null,
    type         enum ('actions', 'crypto') not null,
    libelle_type varchar(50)                not null,
    real_value   float                      null,
    value_cours  double                     null,
    constraint wallet_value_wallet_user_id_fk
        foreign key (id_wallet) references wallet_user (id)
);
