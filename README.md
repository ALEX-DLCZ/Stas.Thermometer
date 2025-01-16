
<p align="center">
  <img src="https://github.com/user-attachments/assets/41711426-4e90-4614-8843-b51bac02d348" alt="image" width="50%">
</p>


# Stas.Thermometer 

>Projet B2-UE Programmation avancée a l'H.E.L.Mosane en lien avec le repository `Stas.Monitor`
>
Stas.Thermometer est une application Java JDK 17 & Gradle 8.+ exposant une CLI qui simule un thermomètre intelligent. Cette application sera configurée à l’aide d’un fichier ini fourni au moment de son exécution.

![image](https://github.com/user-attachments/assets/2b1bf3c7-c419-4f24-b089-40d4a4e72e4b)

## Fonctionnalités principales

![image](https://github.com/user-attachments/assets/f764a608-fec9-46cd-ae12-81f8d4d2630d)


- **Gestion des sondes** : Actuellement prévu pour des sondes thermiques et d'humidité mais facilement extensible à d'autres types de mesures comme la pression atmosphérique, le rayonnement thermique, la vitesse du vent, ou la conductivité thermique.\
      Pour ajouter une sonde il faut adapter le fichier ini et ajouter une énume dans ValueType.java pour définir son seuil d'allert.
- **Stockage des données** : Les mesures sont stockées dans une base de données relationnelle avec deux tables principales :
  - `Mesures` : Contient les données brutes des sondes.
  - `Alerts` : Contient des alertes basées sur les seuils définis.


 ## Particularité Technique du projet
 - **projet** : Java JDK 17 et Gradle 8.+
 - **Règle PMD pour l' O.O.** : 0 NCSS, 0 CC, 0 ToManyParameter, 1 LackOfCohesion, 0 TooManyFields, 0 TooManyMethods, 1 AvoidNonPrivateInstanceField, 0 CouplingBetweenObject, 0 LooseCoupling
 - **Exploitation de spécificité Java** : Utilisation de Pipline Stream, Utilisation de enregistrements(record), utilisation de générique, Interface Fonctionnelle.
 - **Tests Unitaire** : couverture a 100%, utilisation de Mocks
 - **DP utilisé** : DP Observeur (Publisher -> AggregatorMain, Subscriber -> AggregatorSubscriber, ConcreteSubscribers -> MainPresenter, MainDataBase)

![image](https://github.com/user-attachments/assets/1f05f264-acc2-40fd-b32d-61ebcff1d6b2)


## Structure des Données

### Table `Mesures`


```sql
CREATE TABLE Mesures (
    id INT AUTO_INCREMENT PRIMARY KEY,
    thermometerName VARCHAR(255) NOT NULL,
    datetime DATETIME NOT NULL,
    type VARCHAR(50) NOT NULL,
    format VARCHAR(50) NOT NULL,
    value DOUBLE NOT NULL
);
```

### Table `Alerts`
```sql
CREATE TABLE Alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    expectedValue DOUBLE NOT NULL,
    idMesure INT NOT NULL,
    FOREIGN KEY (idMesure) REFERENCES Mesures(id)
);
```

## Instructions d'installation et d'exécution

1. Clonez le dépôt :
   ```bash
   git clone <url-du-dépôt>
   ```
2. Configurez le fichier `config.ini` pour spécifier les paramètres de connexion à la base de données.
   ```ini
   [general]
   ;Nom de la sonde (obligatoir)
   name=salon
   [format]
   ; format non obligatoir
   temperature=00.00°
   humidity=0%
   otherdata=0000Ω
   datetime=dd/MM/YYYY a HH :mm :ss
   [temperature]
   ;jalons réparti dans la journée
   j00=5
   j01=40
   j02=10
   j03=4
   j04=10
   j05=30
   j06=26
   [humidity]
   j00=0.6
   j01=0.3
   j02=0.1
   j03=0.65
   [otherdata]
   j00=9416
   j01=9999
   [BD]
   ;acces a la bd
   IpServer= your.ipaddress.com
   PortServer= 8081
   User= USERNAME
   Pws= PASSWORD
   ```
4. Exécutez l'application avec la commande suivante :
   ```bash
   ./gradlew run --args="--config-file config.ini"
   ```
