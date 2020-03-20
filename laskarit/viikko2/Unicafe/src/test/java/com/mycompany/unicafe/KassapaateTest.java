package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    Kassapaate kassa;
    
    public KassapaateTest() {
    }
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }
    
    @Test
    public void kassassaAluksiRahaaOikeaMaara() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    //Käteisostoon liittyvät testit
    @Test
    public void edullisiaLounaitaMyytyAluksiNolla() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaitaLounaitaMyytyAluksiNolla() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenLounaanOstoKateisellaKasvattaaKassanSaldoaKunMaksuOnRiittava() {
        kassa.syoEdullisesti(400);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void maukkaanLounaanOstoKateisellaKasvattaaKassanSaldoaKunMaksuOnRiittava() {
        kassa.syoMaukkaasti(500);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void edullisenLounaanOstoKateisellaEiKasvataKassanSaldoaKunMaksuEiOleRiittava() {
        kassa.syoEdullisesti(100);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void maukkaanLounaanOstoKateisellaEiKasvataKassanSaldoaKunMaksuEiOleRiittava() {
        kassa.syoMaukkaasti(200);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void edullisestaLounaastaKateisellaSaaOikeanVaihtorahanKunMaksuOnRiittava() {
        assertEquals(260, kassa.syoEdullisesti(500));
    }
    
    @Test
    public void maukkaastaLounaastaKateisellaSaaOikeanVaihtorahanKunMaksuOnRiittava() {
        assertEquals(100, kassa.syoMaukkaasti(500));
    }
    
    @Test
    public void edullisestaLounaastaKateisellaSaaOikeanVaihtorahanKunMaksuEiOleRiittava() {
        assertEquals(200, kassa.syoEdullisesti(200));
    }
    
    @Test
    public void maukkaastaLounaastaKateisellaSaaOikeanVaihtorahanKunMaksuEiOleRiittava() {
        assertEquals(300, kassa.syoMaukkaasti(300));
    }
    
        @Test
    public void kateisellaMyytyjenEdullistenLounaidenMaaraKasvaaKunMaksuOnRiittava() {
        kassa.syoEdullisesti(240);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisellaMyytyjenMaukkaidenLounaidenMaaraKasvaaKunMaksuOnRiittava() {
        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisellaMyytyjenEdullistenLounaidenMaaraEiKasvaKunMaksuEiOleRiittava() {
        kassa.syoEdullisesti(200);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisellaMyytyjenMaukkaidenLounaidenMaaraEiKasvaKunMaksuEiOleRiittava() {
        kassa.syoMaukkaasti(200);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    //Kortilla ostamiseen liittyvät testit
    @Test
    public void palauttaaTrueKunOstaaEdullisenLounaanKortillaJaKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(1000);
        assertTrue(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void palauttaaTrueKunOstaaMaukkaanLounaanKortillaJaKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(1000);
        assertTrue(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void palauttaaFalseKunOstaaEdullisenLounaanKortillaJaKortillaEiOleTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(100);
        assertFalse(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void palauttaaFalseKunOstaaMaukkaanLounaanKortillaJaKortillaEiOleTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(100);
        assertFalse(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void edullisenLounaanOstoKortillaEiKasvataKassanSaldoa() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void maukkaanLounaanOstoKortillaEiKasvataKassanSaldoa() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void edullisenLounaanOstoVahentaaKortinSaldoaKunKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
    }
    
    @Test
    public void maukkaanLounaanOstoVahentaaKortinSaldoaKunKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
    }
    
    @Test
    public void edullisenLounaanOstoEiVahennaKortinSaldoaKunKortillaEiOleTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(100);
        kassa.syoEdullisesti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void maukkaanLounaanOstoEiVahennaKortinSaldoaKunKortillaEiOleTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(100);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void MyytyjenEdullistenLounaidenMaaraKasvaaKunKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void MyytyjenEdullistenLounaidenMaaraEiKasvaKunKortillaEiOleTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(100);
        kassa.syoEdullisesti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void MyytyjenMaukkaidenLounaidenMaaraKasvaaKunKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void MyytyjenMaukkaidenLounaidenMaaraEiKasvaKunKortillaEiOleTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(100);
        kassa.syoMaukkaasti(kortti);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    //Kortin lataamiseen liityvät testit
    @Test
    public void kortinSaldoKasvaaaKunKorttiaLadataanEiNegatiivisellaLuvulla() {
        Maksukortti kortti = new Maksukortti(0);
        kassa.lataaRahaaKortille(kortti, 1000);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void kortinSaldoEiMuutuKunKorttiaLadataanNegatiivisellaLuvulla() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.lataaRahaaKortille(kortti, -500);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void kassanSaldoKasvaaKunKorttiaLadataanEiNegatiivisellaLuvulla() {
        Maksukortti kortti = new Maksukortti(0);
        kassa.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassanSaldoKasvaaKunKorttiaLadataanNegatiivisellaLuvulla() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.lataaRahaaKortille(kortti, -500);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void hello() {}
}
