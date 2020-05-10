
# Vaatimusmäärittely

## Sovelluksen tarkoitus
Vaelluspäiväkirjan avulla käyttäjä voi pitää kätevästi kirjaa menneiden ja tulevien vaellustensa reiteistä ja päivämatkoista. Lisäksi sovellukseen voi tallentaa esimerkiksi tiedon matkaseuralaisista, rinkan alku- ja loppupainosta, päiväkohtaisesta säästä sekä vaelluksen ruoka- ja varustelistat. Sovelluksessa näkee helposti sekä tekemiensä vaellusten tiedot, joita voi hyödyntää seuraavia vaelluksia suunnitellessa, että tulevat vaellussuunnitelmansa.

## Käyttäjät
Sovelluksen käyttäjien käyttäjärooli on lähtökohtaisesti _tavallinen käyttäjä_. Käyttäjä luo itselleen käyttäjänimen sovelluksen käytön aloittaessaan.

## Käyttöliittymäluonnos
Aloittaessaan sovelluksen käytön ensimmäistä kertaa käyttäjä luo itselleen käyttäjänimen, jota pääsee myöhemmin muokkaamaan _Asetuksista_. Tämän jälkeen sovellus aukeaa aloitusnäkymässä, missä se aukeaa aina jatkossa sovellusta käynnistettäessä.

Aloitusnäkymästä pääsee luomaan uuden vaelluksen sekä navigoimaan menneiden vaellusten listaukseen, tulevien vaellusten listaukseen ja _Asetuksiin_. Menneiden ja tulevien vaellusten listauksissa käyttäjä voi valita tarkasteltavaksi jonkin tietyn vaelluksen. Kun jokin tietty vaellus valitaan, aukeaa uusi näkymä, josta taas pääsee tarkastelemaan ko. vaellukseen tallennettuja tietoja sekä muokkaamaan näitä tietoja.

Alla olevassa luonnoskuvassa on esitelty kolme näkymää: aloitusnäkymä, menneiden vaellusten listaus ja valitun vaelluksen tiedot esittelevä näkymä.
![Image of user interface (sketch)](../dokumentointi/OHTE_luonnos_kayttoliittymasta.jpg)

## Perusversion tarjoama toiminnallisuus

Vaelluspäiväkirjan perusversiossa käyttäjä voi luoda, muokata ja selata vaelluksiaan, jotka on ryhmitelty menneisiin ja tuleviin vaelluksiin ja järjestetty alkamispäivän mukaan. Käyttäjä voi myös tarkastella, luoda ja muokata vaelluksen varuste- ja ruokalistoja, seuralaisia, rinkanpainoja ja päivämatkoja.

### Vaellus

Vaellus koostuu
* päivämatkoista (päivämatka on järjestetty päivämäärän mukaan) 
* aloitus- ja lopetuspaikasta (käyttäjän valinnan mukaan esim. paikannimiä tai koordinaatteja)
* varustelistasta, joka koostuu yksittäistä varusteista, joille voi asettaa painon
* ruokalistasta, joka koostuu yksittäisistä ruokalajeista (nämä taas koostuvat ruoka-aineista)
* seuralaisista (kanssavaeltajien nimiä) sekä
* rinkan alku- ja loppupainosta.
* Kun vaellukselle lisätään päivämatkoja, yksittäisen vaelluksen tiedot näyttävä näkymä ilmoittaa vaelluksen kokonaismatkan kilometreissä.
* Lisäksi jokainen vaellus kuuluu joko menneiden tai tulevien vaellusten kategoriaan.
* Käyttäjä voi valita, mitkä tiedot hän tallentaa. Vaelluksen luomisen yhteydessä vaellukselle on annettava vähintään nimi ja vuosi.

### Päivämatka
Päivämatkalla on
* päivämäärä
* alku- ja loppupisteet (käyttäjän valinnan mukana paikannimiä tai koordinaatteja)
* kuljettu matka (km) 
* kävelyyn käytetty aika (tunteina) sekä
* päivän sää.
* Käyttäjä voi valita, mitkä tiedot hän tallentaa. Päivämatkan luomisen yhteydessä on annettava vähintään päivämäärä sekä alku- ja loppupisteet.

### Varuste- ja ruokalistat
Käyttäjä voi luoda varustelistoja, jotka tallentavat listan vaellukselle mukaan otettavista varusteista. Samaan tapaan käyttäjä voi luoda myös ruokalistoja, jotka tallentavat vaellukselle suunnitellut ruokalajit. Listoja voi muokata: niihin voi lisätä varusteita tai ruokalajeja tai niitä voi poistaa.

### Asetukset
Sovelluksen käyttämisen alussa valitun käyttäjänimen voi vaihtaa asetuksissa.

## Jatkokehitysideoita
Myöhemmin sovellukseen lisättäviä toiminnallisuuksia voivat esimerkiksi olla 
* tilastoja vaelluksista (esim. kuljetut kilometrit, kävelyyn käytetyt tunnit)
* mahdollisuus lisätä valmiita ruokalistoja ja aterioita vaelluksiin valitsemalla olemassaolevista
* tietyn vaelluksen kaikkien ruokien ainesosalista yhdellä näkymällä (esim. kauppalistan pohjaksi) sekä
* käyttäjäprofiilin laajentaminen (mm. profiilikuva, ikä).

