package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void metodiSaldoPalauttaaSaldon() {
        assertTrue(1000 == kortti.saldo());
    }
    
    @Test
    public void alkusaldoOnOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(500);
        assertEquals("saldo: 15.0", kortti.toString());
    }
    
    @Test
    public void otaRahaaVahentaaSaldoaOikein() {
        kortti.otaRahaa(300);
        assertEquals("saldo: 7.0", kortti.toString());
    }
    
    @Test
    public void otaRahaaEiVahennaSaldoaJosKatettaEiOleTarpeeksi() {
        kortti.otaRahaa(2000);
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void palauttaaTrueJosRahatRiittivat() {
        assertTrue(kortti.otaRahaa(300));
    }
    
    @Test
    public void palauttaaFalseJosRahatEivatRiittaneet() {
        assertFalse(kortti.otaRahaa(2000));
    }
}
