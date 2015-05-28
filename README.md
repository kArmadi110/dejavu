#Programozási környezetek & Programozási technológiák beadandó
#DejaVu memóriajáték
Egy kártyás memóriajáték, melyben meg kell lelnünk minden kártyának a párját. A játék időre megy, a legjobbak felkerülhetnek az eredménytáblára.

##Használati előkövetelmények
Az alábbi parancsot ki kell adnunk a projekt könyvtárában
```sh
mvn install:install-file -Dfile=lib/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar
```
A használathoz szükséges az src/main/resources/project.properties fájlban a username, password és a url értékeket megadni. Ezek legyenek az adatbázis hozzáféréshez szükséges adatok.
##Futtatás
```sh
mvn clean install  && java -jar target/dejavu-0.1-jar-with-dependencies.jar
```
vagy
```sh
mvn clean install exec:java -Dexec.mainClass=hu.unideb.inf.dejavu.DejaVu
```
##Kapcsoló a debug megjelenítéséhez
```sh
-Dorg.slf4j.simpleLogger.defaultLogLevel=DEBUG
```