#Programozási környezetek & Programozási technológiák beadandó
#DejaVu memóriajáték
Egy kártyás memóriajáték, melyben meg kell lelnünk minden kártyának a párját. A játék időre megy, a legjobbak felkerülhetnek az eredménytáblára.

##Futtatás
```sh
mvn clean install   
java -jar target/dejavu-0.1-jar-with-dependencies.jar
```
vagy
```sh
mvn clean install exec:java -Dexec.mainClass=hu.unideb.inf.dejavu.controller.DejaVu
```
##Kapcsoló a debug megjelenítéséhez
```sh
-Dorg.slf4j.simpleLogger.defaultLogLevel=DEBUG
```