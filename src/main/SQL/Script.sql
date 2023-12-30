create or replace table crypto
(
    nom varchar(50) not null
        primary key
);

create or replace table entreprise
(
    nom varchar(50) not null
        primary key
);

INSERT INTO entreprise (nom) VALUES ('Total');
INSERT INTO entreprise (nom) VALUES ('Cocacola');
INSERT INTO entreprise (nom) VALUES ('Mcdonalds');
INSERT INTO entreprise (nom) VALUES ('Nike');
INSERT INTO entreprise (nom) VALUES ('Samsung');
INSERT INTO crypto (nom) VALUES ('BNB');
INSERT INTO crypto (nom) VALUES ('BTC');
INSERT INTO crypto (nom) VALUES ('ETH');
INSERT INTO crypto (nom) VALUES ('SOL');
INSERT INTO crypto (nom) VALUES ('STETH');

create or replace table user
(
    nom    varchar(50)     not null,
    prenom varchar(50)     not null,
    tel    varchar(50)     not null,
    mail   varchar(50)     not null
        primary key ,
    h_mdp  text            not null,
    salt   varchar(100)    not null,
    apport float default 0 not null
);

create or replace table wallet_user
(
    id            int auto_increment
        primary key,
    name          varchar(50)     not null,
    mail_user     varchar(50)     not null,
    totale        float default 0 not null,
    totale_action float default 0 not null,
    totale_crypto float default 0 not null
);
ALTER TABLE wallet_user
    ADD CONSTRAINT wallet_user_user_mail_fk
        FOREIGN KEY (mail_user) REFERENCES user (mail);
create or replace table transaction
(
    id_wallet int                        not null,
    value     float                      null,
    date      date                       not null,
    type      enum ('actions', 'crypto') not null,
    constraint wallet_value_wallet_user_id_fk
        foreign key (id_wallet) references wallet_user (id)
);
