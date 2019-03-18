package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinLuominenToimii() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void lataaRahaaToimii() {
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.10", kortti.toString());
    }
    
    @Test
    public void otaRahaaToimiiKunSaldoaTarpeeksi() {
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.5", kortti.toString());
    }
    
    @Test
    public void saldoEiMuutuJosOttaaLiikaa() {
        kortti.otaRahaa(12);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void otaRahaaPaluttaaTruejosRahatRiittaa() {
        assertEquals(true, kortti.otaRahaa(2));
    }
    
    @Test
    public void otaRahaaPaluttaaFalsejosRahatEiRiittaa() {
        assertEquals(false, kortti.otaRahaa(100));
    }
}
