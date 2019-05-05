# Käyttöohje

Lataa tiedosto [DungeonCrawler.jar]

## konfigurointi?

## Ohjelman käynnistys?

## Aloitus

Sovellus käynnistyy Aloitusruutu näkymään:

<img src="https://github.com/Antgoblin/ot-harjoitustyo/blob/master/dokumentointi/kuvat/DungeonCrawler.png"> 

Painamalla Enteriä alkaa uusi peli ja sovellus siirtyy hahmon valintaan.
Painamalla näppäintä L lataa peli tallennetun pelin ja sovellus siirtyy suoraan pelinäkymään. Tosin jos pelin lataamisessa menee
joku pieleen tai peliä ei ole tallennettu aiemmin siirtyy sovellus hahmon valintaan.

## Hahmon valinta

Hahmon valinta:

<img src="https://github.com/Antgoblin/ot-harjoitustyo/blob/master/dokumentointi/kuvat/ChooseClass.png">

Painamalla näppäintä W tässä näkymässä siirtyy sovellus pelinäkymään ja asettaa käyttäjän hahmolle hahmoluokan warrior.
Näppäimet R ja M toimivat samaan tapaan, paitsi että asettavat hahmolle hahmoluokat ranger ja mage.

## Pelin ohjeet

Kun käyttäjä on valinnut hahmonluokan siirtyy sovellus pelinäkymään:

<img src="https://github.com/Antgoblin/ot-harjoitustyo/blob/master/dokumentointi/kuvat/GameScreen.png">

Pelissä käyttäjän hahmo on pieni musta neliö.
pienet ympyrät ovat pelin liikuttamia vihollisia, joiden väri kertoo mikiä vihollinen on.
Viholliset liikkuvat aina kun Käyttäjä ohjaa hahmoaan, eli painaa näppäintä näppäimistöltä. 
(kaikki näppäimet eivät laita vihollisia liikkumaan)
Pelissä värillinen ruutu indikoi mitä ruudussa on:
- valkoinen väri: ruutu on "lattiaa".
- musta väri: ruudussa on seinä
- ruskea väri: ruudussa on ovi
- vaalean ruskea: ruudussa on aukinainen ovi.
- turkoosi väri: portaat ylöspäin.
- vaaleanpunainen väri: portaat alaspäin.
Ruudussa voi mahdollisesti olla myös vinottainen viiva. Tämä kertoo että ruudussa on esine.

Näytön yläreunassa oleva valkoinen suorakulmio sisältää tekstiä, joka kertoo mitä pelissä tapahtuu.

Näytön vasemmassa reunassa oleva valkoinen suorakulmio sisältää tekstia, joka kertoo Käyttäjälle pelaajan tilanteen.

## Näppäimet

- Nuolinäppäimet
    liikuttavat pelihahmoa.
    
- O (options)
    avaa näkymän josta voi tallentaa tai ladata pelin.

- H (help)
    avaa näkymän jossa kerrotaan näppäimet.
    
- I (inventory)
    avaa näkymän jossa pelaajan kaikki esineet. Esineitä voi pudottaa ja käyttää tässä näkymässä.
    
- P (pickup)
    nostaa pelaajan ruudussa olevan esineen.
    
- S (shoot)
    aseella ampuminen (jos ase pystyy siihen)
   
- C (cast)
    avaa näkymän, jossa kaikki pelaajan osaamat loitsut. Loitsuja voi käyttää tässä näkymässä.
    
- SPACE 
    odottaa vuoron
    
- D (door)
    sulkee kysytyssä suunnassa olevan oven.
    
- TAB
    vaihtaa pelaajan asepaikoissa olevat aseet (hyötyä tästä on jos ei halua aina mennä inventorin kautta 
    vaihtamaan esim jousen miekkaan.)
    
- ESC 
    asettaa pelinäkymän (poistuu inventori, spell, help tai optionnäkymästä).
    
