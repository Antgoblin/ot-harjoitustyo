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

## Tietojen pysyväistallennus
Sovellus tallentaa tietoa metodilla save() tiedostoon save.txt ja lukee sen sieltä metodilla load().
Sovellus luo tiedoston save.txt jos sellaista ei ole olemassa.

### Päätoiminnallisuudet

Sovelluksen pää toiminnallisuutena on "pelivuoro" joka tapahtuu lähes aina kun käyttä painaa jotain peliin
asetettua näppäintä. Otetaan esimerkiksi vaikka yksi nuolinäppäimistä.

<img src="https://github.com/Antgoblin/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Turn.png">
