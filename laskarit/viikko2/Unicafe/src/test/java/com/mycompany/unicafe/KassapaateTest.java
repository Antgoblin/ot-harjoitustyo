package com.mycompany.unicafe;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jy
 */
public class KassapaateTest {
    
    Kassapaate kassapaate;
    Maksukortti maksukortti;
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
    }
    
    @Test
    public void alussaOikeaMaaraRahaa() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void alussaEdullistenLuonaidenMaaraOikein() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void alussaMaukkaidenLuonaidenMaaraOikein() {
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiMaksaaOikein() {
        kassapaate.syoEdullisesti(300);
        assertEquals(100240, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiMaksaaOikein() {
        kassapaate.syoMaukkaasti(500);
        assertEquals(100400, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiMaksaaTakaisinOikein() {
        assertEquals(60, kassapaate.syoEdullisesti(300));
    }
    
    @Test
    public void syoMaukkaastiMaksaaTakaisinOikein() {
        assertEquals(100, kassapaate.syoMaukkaasti(500));
    }
    
    @Test
    public void syoEdullisestiKasvattaaLuonaita() {
        kassapaate.syoEdullisesti(300);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKasvattaaLuonaita() {
        kassapaate.syoMaukkaasti(500);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisetiToimiiKunMaksaaLiianVahan() {
        kassapaate.syoEdullisesti(100);
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(100, kassapaate.syoEdullisesti(100));
    }
    
    @Test
    public void syoMaukkaastiToimiiKunMaksaaLiianVahan() {
        kassapaate.syoMaukkaasti(100);
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100, kassapaate.syoMaukkaasti(100));
    }
    
    @Test
    public void syoEdullisestiToimiiKunMaksaaKortilla() { 
        maksukortti = new Maksukortti(500);
        assertEquals(true, kassapaate.syoEdullisesti(maksukortti));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(260, maksukortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiToimiiKunMaksaaKortilla() {  
        maksukortti = new Maksukortti(500);
        assertEquals(true, kassapaate.syoMaukkaasti(maksukortti));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100, maksukortti.saldo());
    }
    
    @Test
    public void syoEdullisestiToimiiKunKortillaEiTarpeeksiSaldoa() {
        maksukortti = new Maksukortti(100);
        assertEquals(false, kassapaate.syoEdullisesti(maksukortti));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(100, maksukortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiToimiiKunKortillaEiTarpeeksiSaldoa() {
        maksukortti = new Maksukortti(100);
        assertEquals(false, kassapaate.syoMaukkaasti(maksukortti));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100, maksukortti.saldo());
    }
    
    @Test
    public void lataaRahaaKortilleToimii() {
        maksukortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(maksukortti, 1000);
        assertEquals(101000, kassapaate.kassassaRahaa());
        assertEquals(1000, maksukortti.saldo());
    }
    
    @Test
    public void lataaRahaaKunAsettaaNegatiivisenMaaran() {
        maksukortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(maksukortti, -20);
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, maksukortti.saldo());
    }
}
