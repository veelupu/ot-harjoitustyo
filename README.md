
# Vaelluspäiväkirja

Vaelluspäiväkirja-sovelluksen avulla sovelluksen käyttäjän on helppoa ja kätevää pitää kirjaa menneistä vaelluksistaan sekä suunnitella tulevia vaelluksiaan. Sovellukseen voi tallentaa tiedot vaelluksistaan päivämatkoja, rinkan painoa, seuralaisia, säätä sekä ruoka- ja varustelistoja myöten. Sovelluksessa voi selata ja hakea erikseen menneitä ja tulevia vaelluksiaan sekä käyttää menneiden vaellusten listoja ja päivämatkatietoja uusien vaellusten suunnittelun pohjana.

Tähän mennessä graafiselle käyttöliittymälle on toteutettu päävalikko sekä toiminnallisuus uuden vaelluksen lisäämiseksi, menneiden ja tulevien vaellusten listaamiseksi, valitun vaelluksen näyttämiseksi sekä käyttäjänimen vaihtamiseksi. Vaellukselle on tällä hetkellä mahdollista lisätä seuraavat tiedot: nimi, vuosi, rinkan alku- ja loppupaino, mahdolliset seuralaiset sekä tieto siitä, onko kyseessä mennyt vai tuleva vaellus.

## Dokumentaatio

[Käyttöohje]
_Tähän käyttöohjedokumentti_

[Alustava määrittelydokumentti](dokumentointi/alustava_maarittelydokumentti.md)

[Sovellusarkkitehtuuri](dokumentointi/sovellusarkkitehtuuri.md)

[Testaus](dokumentointi/testaus.md)

[Työajanseuranta](dokumentointi/tyoajanseuranta.md)

## Releaset

[Viikko 5](/releases/tag/viikko5)

## Komentorivitoiminnot

### Suoritus

Suoritettava jar-tiedosto _Vaelluspaivakirja-1.0.jar_ generoidaan hakemistoon target komennolla

```
mvn package
```

Ohjelman suoritetaan komennolla

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
Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto target/site/checkstyle.html.

