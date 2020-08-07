![Logo](https://gitl4b.dutches.de/fhe/java1-2/travelbuddy/-/raw/09602b1c1e2d512495568601810f0f4b3cfaa390/Logo/Travelbuddy_Logo_ohne_Schrift.png )<br>
[![pipline status](https://gitl4b.dutches.de/fhe/java1-2/travelbuddy/badges/master/pipeline.svg)](https://gitl4b.dutches.de/fhe/java1-2/travelbuddy/commits/master) <br><br>  
[![forthebadge](https://forthebadge.com/images/badges/fuck-it-ship-it.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/as-seen-on-tv.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/winter-is-coming.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/designed-in-ms-paint.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/contains-cat-gifs.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/uses-badges.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/compatibility-betamax.svg)](https://forthebadge.com)  
  

  
# TravelBuddy   
  
## Beschreibung  

TravelBuddy ist ein Planungstool und Begleiter für die nächste Reise.    

## Zentrale Features

Mit dem TravelBuddy können momentan folgende Sachen gespeichert und gemanaged werden <br>  
* Orte (unterteilt in allgemeine Orte, Sehenswürdigkeiten und Unterkünfte)
* Verbindungen zwischen diesen Orten
* Personen die an der Reise teilnehme
* Entstehende Kosten, welche den Personen zugeordnet werden können. 
* Kostenübersichten zu Reisen und Personen
* Diese Kosten können in verschiedenen Währungen gespeichert werden und werden automatisch umgerechnet
  
## Zukünftige Features
  
* Suche für bestimmte lokalitäten wie Restaurants, Parkhäuser, Bahnhöfe etc.  
* Dynamischer Wetterbericht für die in der Reise abgelegten Orte
* Share Funktion und weitere Multiuserfunktionen
* CalDav export für Reisedaten
  
  
## UML Diagramm des Models
![UML mit Logo](https://gitl4b.dutches.de/fhe/java1-2/travelbuddy/-/raw/rest/documents/UML_Java_final%20mit%20Logo.png)
## Aktuere/Stakeholder  
  
### Aktuere  
Aktuell nur auf einem Gerät, somit Local Admin.  
  
### Verwendete Tools  
- **Drittbibliotheken**:
	- JUnit
	- Maven, 
	- JavaDoc
	- Spring
	- Hibernate
	- Restassured
	- Lombok
	- Querydsl
	- H2 DB
	- Sprinfox (Swagger)
	- MariaDb
- Versionskontrollsystem: GitLab  
- Kommunikation: Discord, Signal, GitLab

### Build
1. Repository klonen
2. Projekt mit "mvn compile" kompilieren
2.1 Hierdurch werden die Q-Entitäten erstellt

### Entwickler
Frieder Ullmann, Tim Vogel, Marcel van der Heide 

### WebUI
Die Oberfläche des Projekts ist zu finden im Projekt: TravelBuddy-WebUI
https://github.com/fh-erfurt/TravelBuddy-WebUI

### Generische Repository
Ein veralteter Stand, mit generischem repository und DAO ist zu finden im branch: generic_dao_repo.
