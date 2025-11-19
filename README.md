# 💼 Financial Portfolio — ISEP
(French version below)

JavaFX + SQL application for managing a financial portfolio.  
Users can log in, manage their transactions, view their fund performance, edit their profile, and manage multiple portfolios.

---

## 📌 Features

- 🔐 **User login/registration**
- 👤 Profile management
- 💼 Management of multiple portfolios
- ➕ Add/withdraw funds
- 🔄 Add transactions (purchase/sale)
- 📊 Dashboard: overviews and graphs
- 🗃️ Data storage in **SQL database* *
- 🖼️ **JavaFX (FXML + controllers)** graphical interface

---

## 📁 Project structure

````
src/
├── main/
│ ├── java/com.example.portefeuillefinancierisep/
│ │ ├── Controller/ # JavaFX controllers (.java)
│ │ ├── Info/ # DTO / Shared data
│ │ ├── Model/ # Database access + business models
│ │ └── Service/ # Services (data verification, logic)
│ │ └── HomeApplication.java # Main class
│ │
│ ├── resources/
│ │ ├── com.example.portefeuillefinancierisep/.fxml # Interfaces
│ │ └── Image/.png # UI icons
│ │
│ └── SQL/Script.sql # Database script
````


---

## 🛠️ Prerequisites

| Software | Version |
|----------|---------|
| Java JDK | **17+** |
| Maven    | **3.6+** |
| SQL database | PostgreSQL / MySQL / MariaDB |
| JavaFX   | Included via Maven (no manual installation required) |

---

## 🗃️ Database

📌 The schema is located in: `src/main/resources/SQL/Script.sql`

### ▶️ Installing the schema

#### ✔️ PostgreSQL (example)

```bash
psql -h localhost -U username -d wallet < src/main/resources/SQL/Script.sql
```

### 🔧 Database configuration

The configuration is done in the class:
```
Modele/ConnectDB.java
```

➡️ Remember to change your credentials:
```
String url = “jdbc:mysql://localhost:3306/wallet”;
String user = “root”;
String pass = “”;
String driver = “com.mysql.cj.jdbc.Driver”;
```

### 🚀 Installation & execution

####  Clone the project
```bash
git clone https://github.com/LenouvelLouis/Portefeuille-financier-ISEP.git
cd Portefeuille-financier-ISEP
```

#### Compile
```bash
mvn clean package
```

#### Run in IntelliJ IDEA

📌 Configuration to use:
- Main class: `com.example.portefeuillefinancierisep.HomeApplication`
- Working directory: `ISEP-financial-portfolio`


### 👨‍🎓Project carried out at ISEP

- [Louis Lenouvel](https://github.com/LenouvelLouis)
- [Mohamed Bedwey](https://github.com/Mohamed-Bedwey)
- [Fayçal Lassri](https://github.com/iiChoppa)


# 💼 Portefeuille Financier — ISEP

Application Java **JavaFX + SQL** permettant de gérer un portefeuille financier.  
L’utilisateur peut s’authentifier, gérer ses transactions, visualiser l’évolution de ses fonds, modifier son profil et gérer plusieurs portefeuilles.

---

## 📌 Fonctionnalités

- 🔐 **Connexion / Inscription utilisateur**
- 👤 Gestion du profil
- 💼 Gestion de plusieurs portefeuilles
- ➕ Ajout / Retrait de fonds
- 🔄 Ajout de transactions (achat / vente)
- 📊 Dashboard : vues globales et graphiques
- 🗃️ Stockage des données en **base SQL**
- 🖼️ Interface graphique **JavaFX (FXML + controllers)**

---

## 📁 Structure du projet

````
src/
├── main/
│ ├── java/com.example.portefeuillefinancierisep/
│ │ ├── Controller/ # Controllers JavaFX (.java)
│ │ ├── Info/ # DTO / Data shared
│ │ ├── Modele/ # Accès BDD + modèles métiers
│ │ └── Service/ # Services (vérif. données, logique)
│ │ └── HomeApplication.java # Classe main
│ │
│ ├── resources/
│ │ ├── com.example.portefeuillefinancierisep/.fxml # Interfaces
│ │ └── Image/.png # Icônes UI
│ │
│ └── SQL/Script.sql # Script BDD
````


---

## 🛠️ Prérequis

| Logiciel | Version |
|----------|---------|
| Java JDK | **17+** |
| Maven    | **3.6+** |
| Base SQL | PostgreSQL / MySQL / MariaDB |
| JavaFX   | Inclus via Maven (pas besoin manuel) |

---

## 🗃️ Base de données

📌 Le schéma se trouve dans : `src/main/resources/SQL/Script.sql`

### ▶️ Installation du schéma

#### ✔️ PostgreSQL (exemple)

```bash
psql -h localhost -U username -d portefeuille < src/main/resources/SQL/Script.sql
```

### 🔧 Configuration BDD

La configuration est faite dans la classe :
```
Modele/ConnectDB.java
```

➡️ Pensez à modifier vos identifiants :
```
String url = "jdbc:mysql://localhost:3306/portefeuille";
String user = "root";
String pass = "";
String driver = "com.mysql.cj.jdbc.Driver";
```

### 🚀 Installation & exécution

####  Cloner le projet
```bash
git clone https://github.com/LenouvelLouis/Portefeuille-financier-ISEP.git
cd Portefeuille-financier-ISEP
```

#### Compiler
```bash
mvn clean package
```

#### Exécuter sous IntelliJ IDEA

📌 Configuration à utiliser :
- Main class : `com.example.portefeuillefinancierisep.HomeApplication`
- Working directory : `Portefeuille-financier-ISEP`


### 👨‍🎓Projet réalisé à l'ISEP 

- [Louis Lenouvel](https://github.com/LenouvelLouis)
- [Mohamed Bedwey](https://github.com/Mohamed-Bedwey)
- [Fayçal Lassri](https://github.com/iiChoppa)