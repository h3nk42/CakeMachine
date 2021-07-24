@Author - Henk Lucas van der Sloot

##Beleg PZR 1:
### Punkte aus der Uebung
* Uebung 1 - 2 ✅
* Uebung 2 - 2 ✅
* Uebung 3 - 1 ✅
* Uebung 4 - 1 ✅
* Uebung 5 - 1 ✅
* praesentation - 2 ✅
  * 9 Punkte#✅

### Entwurf
* Schichtenaufteilung - 3✅
    * MVC
* Architekturdiagramm - 1✅
  * Beleg/ArchitekturDiagramm.pdf
* Zuständigkeit - 2✅ 
* Paketierung - 2✅ 
* Benennung - 2✅
* keine Duplikate - 1✅
    * 11 Punkte✅
  ### Tests
* Testqualität  7 ✅
  * Wenn ich 2 Zusicherungen verwendet habe, dann um per spy ein Objekt abzufangen via verify()
  * jeder Test hat ueberschrift
  * Verwendung von @Nested klassen fuer bessere uebersicht
* Testabdeckung GL 5 ✅
  * test/model/AutomatTests.java
* Testabdeckung Rest 5 ✅
  * einfuegen von Herstellern CLI
    * test/view/console/
    * test/control/automat/events
    * control/console
    * alles als unitTests, e2e ergibt sich daraus
  * Anzeigen von Herstellern CLI
    * wie einfuegen
  * Beobachter
    * control/automat/observers
  * deterministische Funktionalität der Simulationen
    * test/simulations/LockWrapperTest.java
  * Speichern via JOS
    * control/lib/PersistLibTest.java
  * Laden via JOS
    * control/lib/PersistLibTest.java
  
* Mockito richtig verwendet 5 ✅ 
  * (tst.model.verkaufsobjekte.verkaufsObjektImplTest)
  * habe es eigentlich ueberall verwendet kann man nicht verfehlen
* Spytests (Verhalten) 3  ✅
  * (tst.model.AutomatTests.AutomatTestKuchen zeile 100)
  * (tst.model.AutomatTests.AutomatTestBase)
  * (test/view/console/readerTest.java)
  * habe es eigentlich ueberall verwendet kann man nicht verfehlen
* keine unbeabsichtigt fehlschlagenden Test 1 ✅
  * 26 Punkte ✅
### Fehlerfreiheit
* Kapselung ✅ 5
* keine Ablauffehler  ✅ 5
  * 10 Punkte ✅
### Basisfunktionalitaet
* CRUD ✅ 2
* CLI  ✅ 2
* Simulation 1  ✅ 2
* GUI  ✅ 2
* I/O  ✅ 2
  * 12 Punkte ✅
  ### Funktionalitaet
* vollständige, threadsichere GL ✅ 3
* vollständiges CLI inkl. alternatives CLI  ✅ 3
    * ueber eventHandler deaktivieren veraendert
* vollständiges GUI  ✅ 2
* events (mindestens 3)  ✅ 2
  * control/automat/events/AutomatEvent.java
  * control/console/input/InputEvent.java
  * control/console/output/OutputEvent.java
  * control/gui/event/UpdateGuiEvent.java
* observer ^ property change propagation  ✅ 2
  * control/automat/observers
* angemessene Aufzählungstypen  ✅ 2
* drag&drop  ✅ 2
* Simulationen 2 & 3  ✅ 3
* data binding  ✅ 1
  * control/gui/FeController.java zeile 261
* JOS  ✅ 1
* JBP  ❌ 2
  * 23 - 2 = 21 Punkte ✅
  ### Extra Anforderung❌
  * 10 Punkte ❌
  
###Punkte falls alle angegebenen Punkte akzeptiert werden: 89/90 (100)
## Vielen Dank, ich habe einiges ueber Java gelernt in diesem Modul, was ich vorher nicht wusste :)
##Der Aufwand war allerdings extrem hoch verglichen zu anderen Modulen im Bachelor, weshalb ich auch die Extra-Anforderungen nicht machen konnte, und das obwohl Network ausgelassen wurde. Ich habe bestimmt ueber 120 Stunden Arbeit in dieses Projekt gesteckt.


