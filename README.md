#Programozási környezetek & Programozási technológiák beadandó
#DejaVu memóriajáték
Egy egyszerű kártyás memóriajáték, melyben meg kell lelnünk minden kártyának a párját.

##Használati előkövetelmények
Az alábbi parancsot ki kell adnunk a projekt könyvtárában
```sh
mvn install:install-file -Dfile=lib/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar
```
A használathoz szükséges az src/main/resources/project.properties fájlban a username, password és a url értékeket megadni. Ezek legyenek az adatbázis hozzáféréshez szükséges adatok.
##Futtatás
mvn clean install  && java -jar target/dejavu-0.1-jar-with-dependencies.jar