# TestausDokumentti

Ohjelmaa on testattu JUnitilla sekä Manuaalisesti

### Sovelluslogiikka
Sovelluslogiikka löytyy pääosin paketista dungeoncrawler.logic.
Sovelluslogiikan testit ovat tehty pääosin yksikkötestitasolla, tosin isompien luokkien kuten luokan player tai Map kanssa on 
integraatiotestattu joitain asioita.

### Testauskattavuus

Käyttöliittymä ja kartanpiirtäjä ovat ainoat luokat paketissa dungeoncrawler.
Joten näitä lukuunottamatta sovelluksen rivikattavuus on 79% ja haarautumakattavuus on 64%.

<img src="https://github.com/Antgoblin/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Testikattavuus.png">
  
Testaamatta jäi paljon kohtia jotka käyttävät hyväkeseen jonkinlaista satunnaista arvoa, sekä etenkin luokasta
MovementHandler jäi testaamatta paljon metodeja jotka olisivat kirjoittaneet TextArealle tekstiä.

## Järjestelmätestaus
sovelluksen järjestelmä testaus on tehty manuaalisesti pelaamalla

### Asennus
Sovellus on asennettu käyttöohjeen ohjaamalla tavalla Windowskoneella 

### Toiminnallisuudet
Kaikki Käyttäjän toiminnot jotka määrittelydokumentissa oli mainittu, on testattu.
Osa pelin omista toiminnoista 

## Sovellukseen jääneet laatuongelmat
Luokan MovementHandler olisi voinut testata peremmin, jos TextAreaan olisi kirjoitettu vaikka applicationissa tai ihan omassa luokassa
Sovelluksella ei ole mitään omia virheilmoituksia.
