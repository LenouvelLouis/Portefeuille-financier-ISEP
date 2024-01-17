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

CREATE TABLE evenements (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            description VARCHAR(255) NOT NULL,
                            date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO evenements (description) VALUES ('bonjour1');
INSERT INTO evenements (description) VALUES ('bonjour2');
INSERT INTO evenements (description) VALUES ('bonjour3');
INSERT INTO evenements (description) VALUES ('bonjour4');
INSERT INTO evenements (description) VALUES ('bonjour5');

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
    date         timestamp                      not null,
    type         enum ('actions', 'crypto') not null,
    libelle_type varchar(50)                not null,
    constraint wallet_value_wallet_user_id_fk
        foreign key (id_wallet) references wallet_user (id)
);
INSERT INTO user (nom, prenom, tel, mail, h_mdp, salt) VALUES ('Lenouvel','Louis',9876543210,'Louis@gg.fr','AG28eWuHs9+rlejUiOtjv25WhXjf95+lEoVkN5RQP84=','8VgE/7F');
INSERT INTO wallet_user (name, totale, id_user, totale_action, totale_crypto) VALUES ('wallettest', 50, 1, 10, 40);
INSERT INTO wallet_user (name, totale, id_user, totale_action, totale_crypto) VALUES ('test', 1000, 1, 600, 400);
INSERT INTO wallet_user (name, totale, id_user, totale_action, totale_crypto) VALUES ('new', 200, 1, 100, 100);
INSERT INTO wallet_user (name, totale, id_user, totale_action, totale_crypto) VALUES ('louis', 300, 1, 150, 150);


INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (3, 30, '2023-01-01 00:00:00', 'actions', 'Total');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (3, -20, '2023-02-01 00:00:00', 'actions', 'Total');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (3, 50, '2023-03-01 00:00:00', 'crypto', 'BTC');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (3, -10, '2023-04-01 00:00:00', 'crypto', 'BTC');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (9, 1000, '2023-01-01 00:00:00', 'actions', 'Total');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (9, -400, '2023-02-01 00:00:00', 'actions', 'Total');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (9, 600, '2023-03-01 00:00:00', 'crypto', 'BTC');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (9, -200, '2023-04-01 00:00:00', 'crypto', 'BTC');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (10, 200, '2023-01-01 00:00:00', 'actions', 'Total');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (10, -100, '2023-02-01 00:00:00', 'actions', 'Total');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (10, 150, '2023-03-01 00:00:00', 'crypto', 'BTC');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (10, -50, '2023-04-01 00:00:00', 'crypto', 'BTC');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (11, 300, '2023-01-01 00:00:00', 'actions', 'Total');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (11, -150, '2023-02-01 00:00:00', 'actions', 'Total');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (11, 250, '2023-03-01 00:00:00', 'crypto', 'BTC');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (11, -100, '2023-04-01 00:00:00', 'crypto', 'BTC');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (16, 10, '2024-01-14 00:00:00', 'actions', 'Cocacola');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (16, 10, '2024-01-14 00:00:00', 'actions', 'Cocacola');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (16, 10, '2024-01-14 00:00:00', 'actions', 'Cocacola');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (16, 20, '2024-01-14 00:00:00', 'actions', 'Cocacola');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (16, 10, '2024-01-14 00:00:00', 'actions', 'Cocacola');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (18, 10, '2024-01-14 00:00:00', 'actions', 'Cocacola');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (20, 10, '2024-01-14 00:00:00', 'actions', 'Cocacola');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (20, 10, '2024-01-14 00:00:00', 'actions', 'Cocacola');
INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (20, -5, '2024-01-14 00:00:00', 'actions', 'Cocacola');


