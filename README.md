
# Vaelluspäiväkirja

Vaelluspäiväkirja-sovelluksen avulla sovelluksen käyttäjän on helppoa ja kätevää pitää kirjaa menneistä vaelluksistaan sekä suunnitella tulevia vaelluksiaan. Sovellukseen voi tallentaa tiedot vaelluksistaan päivämatkoja, rinkan painoa, seuralaisia, säätä sekä ruoka- ja varustelistoja myöten. Sovelluksessa voi selata ja hakea erikseen menneitä ja tulevia vaelluksiaan sekä käyttää menneiden vaellusten listoja ja päivämatkatietoja uusien vaellusten suunnittelun pohjana.

Tähän mennessä tekstikäyttöliittymälle on toteutettu päävalikko sekä toiminnallisuus uuden vaelluksen lisäämiseksi, menneiden ja tulevien vaellusten listaamiseksi sekä käyttäjänimen vaihtamiseksi. Graafiselle käyttöliittymälle on toteutettu aloitusnäkymä (päävalikko) sekä uuden vaelluksen luominen.

## Dokumentaatio

[Käyttöohje]
_Tähän käyttöohjedokumentti_

[Alustava määrittelydokumentti](dokumentointi/alustava_maarittelydokumentti.md)

[Sovellusarkkitehtuuri](dokumentointi/sovellusarkkitehtuuri.md)

[Testaus](dokumentointi/testaus.md)

[Työajanseuranta](dokumentointi/tyoajanseuranta.md)

## Komentorivitoiminnot

Ohjelman suoritetaan komennolla

```
mvn compile exec:java -Dexec.mainClass=hikingdiary.ui.Main
```

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

Checkstyle-tarkistukset luodaan tiedostoon [checkstyle.xml](Vaelluspaivakirja/checkstyle.xml) komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```

