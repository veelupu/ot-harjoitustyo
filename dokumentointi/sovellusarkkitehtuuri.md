# Sovellusarkkitehtuuri

## Rakenne
Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria. Lähdekoodin pakkausrakenne on niin ikään kolmiosainen:

![Image of package structure (sketch)](../dokumentointi/vaelluspaivakirja_pakkauskaavio_1.jpg)

Pakkauksessa hikingDiary.ui on sekä tekstikäyttöliittymän että JavaFX:llä toteutettu graafisen käyttöliittymän lähdekoodi. Pakkauksessa hikingDiary.domain sisältää ohjelman sovelluslogiikan. Pakkauksessa hikingDiary.dao oleva lähdekoodi taas hoitaa ohjelmaan liittyvän pysyväistalletuksen.

## Käyttöliittymä
Käyttöliittymässä on useita eri näkymiä. Kun sovellus käynnistetään, avautuu aloitusnäkymä, josta pääsee navigoimaan viiteen eri suuntaan:

* Mikäli käyttäjä valitsee uuden vaelluksen luomisen, pyydetään käyttäjältä luotavan vaelluksen perustietoja (nimi, ajankohta, kuuluuko vaellus tuleviin vai menneisiin vaelluksiin)
* Mikäli käyttäjä valitsee menneiden tai tulevien vaellusten listauksen, näytetään käyttäjälle lista tämän tallentamista menneistä tai tulevista vaelluksista. Jonkin tietyn vaelluksen valitsemalla aukeaa näkymä vaellukseen tallennetuista tiedoista. 
* Mikäli käyttäjä valitsee Asetukset, hän pääsee muuttamaan käyttäjänimeään.
* Mikäil käyttäjä valitsee poistumisen, kysytään varmistusta ja lopulta suljetaan sovellus.

## Sovelluslogiikka

_Tähän sanallinen kuvaus sovelluslogiikasta_

![Imgae of class structure (sketch)](../dokumentointi/vaelluspaivakirja_luokkakaavio_1.jpg)

## Tietojen pysyväistalletus

Pakkauksessa hikingdiary.dao olevat luokat UserDao, HikeDao ja DayTripDao vastaavat vaellusten ja käyttäjänimen pysyväistallennuksesta tallettamalla tiedot tietokantaan. Luokat noudattavat Data Access Object -suunnittelumallia.

## Toiminnallisuus

Alla olevassa sekvenssikaaviossa kuvataan yksi sovelluksen päätoiminnallisuuksista: uuden vaelluksen luominen sen toiminnallisuuden osalta, jonka sovellus tällä hetkellä tarjoaa. Vaelluksen luominen alkaa siitä, että käyttäjä on syöttänyt CreateHikeView-näkymässä näkyvälle lomakkeelle vaelluksen tiedot tekstikenttiin ja valinnut vaelluksen kuuluvan "Tulevat vaellukset" -kategoriaan. 

Kun käyttäjä painaa "Ready to create a new hike!"-nappia, tapahtumat etenevät seuraavasti: 

<img src="../dokumentointi/sekvenssikaavio.png" width="80%" alt="Image of sequence diagram: creating new hike"/>

UserInterface-luokan (UI) olio kutsuu tapahtumien käsittelystä vastaava Controller-oliota, joka puolestaan kutsuu Hike-luokkaa saaden paluuarvona uuden Hike-olion. Tällä oliolla Controller kutsuu HikeDao-luokan vaelluksenluomismetodia ja palauttaa lopuksi hetki sitten syntyneen hike-olion UI:lle. Tämä tarkastaa, onko käyttäjä antanut vaellukselle rinkan alku- ja loppupainot, ja mikäli on, ne lisätään tähän uuteen vaellukseen: Ensin UI kutsuu Hike-luokkaa, joka päivittää painot vaelluksen tietoihin. Tämän jälkeen se kutsuu Controlleria, joka puolestaan kutsuu HikeDaoa päivittääkseen rinkanpainot myös tietokantaan.

Lopuksi tyhjennetään lomakkeen tekstikentät ja näytetään käyttäjälle teksti "New hike created succesfully!".
