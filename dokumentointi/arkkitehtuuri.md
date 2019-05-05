# Arkkitehtuurikuvaus

## Rakenne

## Käyttöliittymä

Käyttöliittymä sisältää neljä eri nekymää
- Aloitus näyttö
- Hahmon valinta
- Peli näkymä
- Pelin Häviämis ruutu

Jokainen näistä näkymistä on oma scenensä ja vain yksi näkyy kerrallaan.
Scenet ovat luotu luokass dungeoncrawler.DungeonCrawlerApplication.

## Sovelluslogiikka

Sovelluksen looginen datamalli koostuu pääosin luokasta movementHandler.
Luokka DungeonCrawlerApplication saa tiedon mitä näppäintä on painettu ja lähettää tiedon Movementhandlerille.
MovementHandler sitten tutkii tätä tietoa ja kertoo kartalle, vihollisille ja pelaajalle mitä niiden pitää tehdä.

## Tietojen pysyväistallennus
Sovellus tallentaa tietoa metodilla save() tiedostoon save.txt ja lukee sen sieltä metodilla load().
Sovellus luo tiedoston save.txt jos sellaista ei ole olemassa.

### Päätoiminnallisuudet

Sovelluksen pää toiminnallisuutena on "pelivuoro" joka tapahtuu lähes aina kun käyttä painaa jotain peliin
asetettua näppäintä. Otetaan esimerkiksi vaikka yksi nuolinäppäimistä.

<img src="https://github.com/Antgoblin/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Turn.png">

Nuolinäppäimen painaminen aiheuttaa tapahtuman DungeonCrawler.Applicationissa joka muuttaa näppäimen suunnakasi
ja kertoo asiasta MovementHandlerille. MovementHandler ottaa kartasta kaikki viholliset ja katsoo voiko pelaaja
liikkua kyseiseen suuntaan. Jos pelaaja voi, antaa Movementhandler komennon Playerille liikkua. Player kutsuu omaa
Characteriaan, joka hakee kartalta ruudun jossa on ja ruudun johon ollaan siirtymässä. Character poistaa itsensä vanhalta 
ruudulta ja lisää itsensä uuteen. Tämän jälkeen DungeonCrawler huomaa että pelaaja on liikkunut ja antaa MovementHandlerille
kovennon lopettaa vuoro. movementhandler liikuttaa vihollisia samalla lailla kuin pelaajaa.
Lopuksi DungeonCrawlerApplicationista lähtee komento MapDrawerille piirtää kaikki mitä kartalla on muuttunut.
