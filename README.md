
# Vaelluspäiväkirja

Vaelluspäiväkirja-sovelluksen avulla sovelluksen käyttäjän on helppoa ja kätevää pitää kirjaa menneistä vaelluksistaan sekä suunnitella tulevia vaelluksiaan. Sovellukseen voi tallentaa tiedot vaelluksistaan päivämatkoja, rinkan painoa, seuralaisia, säätä sekä ruoka- ja varustelistoja myöten. Sovelluksessa voi selata ja hakea erikseen menneitä ja tulevia vaelluksiaan sekä käyttää menneiden vaellusten listoja ja päivämatkatietoja uusien vaellusten suunnittelun pohjana.

Tähän mennessä graafiselle käyttöliittymälle on toteutettu päävalikko sekä toiminnallisuus uuden vaelluksen lisäämiseksi, menneiden ja tulevien vaellusten listaamiseksi, valitun vaelluksen näyttämiseksi sekä käyttäjänimen vaihtamiseksi. Vaellukselle on mahdollista lisätä seuraavat tiedot: nimi, vuosi, alku- ja loppupiste, rinkan alku- ja loppupaino, mahdolliset seuralaiset, ruokalista, varustelista, päivämatkat sekä tieto siitä, onko kyseessä mennyt vai tuleva vaellus.

Vaelluspäiväkirja on Helsingin yliopiston tietojenkäsittelytieteen osaston Ohjelmistotekniikka-kurssin (kevät 2020) harjoitustyö.

## Dokumentaatio

[Käyttöohje](dokumentointi/kayttoohje.md)

[Määrittelydokumentti](dokumentointi/maarittelydokumentti.md)

[Sovellusarkkitehtuuri](dokumentointi/sovellusarkkitehtuuri.md)

[Testaus](dokumentointi/testaus.md)

[Työajanseuranta](dokumentointi/tyoajanseuranta.md)

## Releaset

[Viikko 5](https://github.com/veelupu/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/veelupu/ot-harjoitustyo/releases/tag/viikko6)

[Loppupalautus](https://github.com/veelupu/ot-harjoitustyo/releases/tag/loppupalautus)

## Konfiguraatio

Sovellus on tehty Javan versiolla 11. Graafinen käyttöliittymä on tehty JavaFX:n versiolla 14.

## Komentorivitoiminnot

### Suoritus

Suoritettava jar-tiedosto generoidaan hakemistoon target komennolla

```
mvn package
```

Ohjelma suoritetaan komennolla

```
mvn compile exec:java -Dexec.mainClass=hikingdiary.ui.Main
```

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

### Checkstyle

Checkstyle-tarkistukset luodaan tiedostoon [checkstyle.xml](Vaelluspaivakirja/checkstyle.xml) komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```
Mikäli koodin tyylissä mahdollisesti olevia virheitä voi tarkastella avaamalla tiedosto target/site/checkstyle.html selaimella.

### JavaDoc

Komennolla

```
mvn javadoc:javadoc
```
generoitua JavaDocia pääsee tarkastelemaan avamaalla tiedosto _target/site/apidocs/index.html_ selaimella.

## Sovelluksen taustakuva

Kuva: <a href="https://pixabay.com/fi/users/GDJ-1086657/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=3846595">Gordon Johnson</a> <a href="https://pixabay.com/fi/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=3846595"> Pixabay (Public Domain)</a>
