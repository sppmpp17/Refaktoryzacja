/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.poznan.put.fc.taxes_ref.Umowa;
import pl.poznan.put.fc.taxes_ref.UmowaOPrace;
import pl.poznan.put.fc.taxes_ref.UmowaZlecenie;

/**
 *
 * @author fenix
 */
public class TaxesTests {
    
    private Map<String, Double> parametryUmowy;
    private UmowaZlecenie umowaZ;
    private UmowaOPrace umowaP;
    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        parametryUmowy = new HashMap<>();
        parametryUmowy.put("kwotaDochodu", 1000.0);
        parametryUmowy.put("oprocentowanieUbezpieczenieEmerytalne", 9.76);
        parametryUmowy.put("oprocentowanieUbezpieczenieRentowe", 1.5);
        parametryUmowy.put("oprocentowanieUbezpieczenieChorobowe", 2.45);
        parametryUmowy.put("oprocentowanieSkladkaZdrowotna1", 9.0);
        parametryUmowy.put("oprocentowanieSkladkaZdrowotna2", 7.75);
        parametryUmowy.put("procentPodatku", 18.0);
        parametryUmowy.put("kwotaWolnaUmowaZlecenie", 0.0);
        parametryUmowy.put("kwotaWolnaUmowaOPrace", 46.33);
        parametryUmowy.put("kosztyUzyskaniaPrzychoduProcent", 20.0);
        parametryUmowy.put("kosztyUzyskaniaPrzychoduUmowaOPrace", 111.25);
        
        umowaZ = new UmowaZlecenie(parametryUmowy);
        umowaP = new UmowaOPrace(parametryUmowy);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testSkladkaEmerytalna() {
        double skladkaEmerytalna = 
                Umowa.obliczProcentOd(parametryUmowy.get("kwotaDochodu"), parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"));
        assertEquals(skladkaEmerytalna, 97.60, 0.001);
    }
    
    @Test
    public void testSkladkaRentowa() {
        double skladkaRentowa = 
                Umowa.obliczProcentOd(parametryUmowy.get("kwotaDochodu"), parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"));
        assertEquals(skladkaRentowa, 15.00, 0.001);
    }
    
    @Test
    public void testSkladkaChorobowa() {
        double skladkaChorobowa = 
                Umowa.obliczProcentOd(parametryUmowy.get("kwotaDochodu"), parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        assertEquals(skladkaChorobowa, 24.50, 0.001);
    }
    
     @Test
    public void testSkladkaZdrowotna1UmowyZlecenie() {
        double obliczonaPodstawa = umowaZ.obliczonaPodstawa(
                parametryUmowy.get("kwotaDochodu"),
                parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"),
                parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"),
                parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        
        double skladkaZdrowotna1 = 
                Umowa.obliczProcentOd(obliczonaPodstawa, parametryUmowy.get("oprocentowanieSkladkaZdrowotna1"));
        assertEquals(skladkaZdrowotna1, 77.66, 0.01);
    }
    
    @Test
    public void testSkladkaZdrowotna1() {
        double obliczonaPodstawa = umowaP.obliczonaPodstawa(
                parametryUmowy.get("kwotaDochodu"),
                parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"),
                parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"),
                parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        
        double skladkaZdrowotna1 = 
                Umowa.obliczProcentOd(obliczonaPodstawa, parametryUmowy.get("oprocentowanieSkladkaZdrowotna1"));
        assertEquals(skladkaZdrowotna1, 77.66, 0.01);
    }
    
    
    @Test
    public void testPodstawaOpodatkowanaUmowyZlecenie() {
        double obliczonaPodstawa = umowaZ.obliczonaPodstawa(
                parametryUmowy.get("kwotaDochodu"),
                parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"),
                parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"),
                parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        
        double podstawaOpodatkowana = umowaZ.getPodstawaOpodatkowana(obliczonaPodstawa, umowaZ.getKosztyUzyskaniaPrzychodu());
        assertEquals(podstawaOpodatkowana, 690.3199999999999, 0.001);
    }
    
    @Test
    public void testPodstawaOpodatkowanaUmowyOPrace() {
        double obliczonaPodstawa = umowaP.obliczonaPodstawa(
                parametryUmowy.get("kwotaDochodu"),
                parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"),
                parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"),
                parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        
        double podstawaOpodatkowana = umowaP.getPodstawaOpodatkowana(obliczonaPodstawa, umowaP.getKosztyUzyskaniaPrzychodu());
        assertEquals(podstawaOpodatkowana, 751.65, 0.001);
    }
    
    @Test
    public void testZaliczkaNaPodatekDochodowyUmowyZlecenie() {
        double obliczonaPodstawa = umowaZ.obliczonaPodstawa(
                parametryUmowy.get("kwotaDochodu"),
                parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"),
                parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"),
                parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        double podstawaOpodatkowania = umowaZ.getPodstawaOpodatkowana(obliczonaPodstawa, umowaZ.getKosztyUzyskaniaPrzychodu());
        
        double zaliczkaNaPodatekDochodowy = umowaZ.getZaliczkaNaPodatke(podstawaOpodatkowania, parametryUmowy.get("procentPodatku"));
        assertEquals(zaliczkaNaPodatekDochodowy, 124.257, 0.001);
    }
    
    @Test
    public void testZaliczkaNaPodatekDochodowyUmowyOPrace() {
        double obliczonaPodstawa = umowaP.obliczonaPodstawa(
                parametryUmowy.get("kwotaDochodu"),
                parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"),
                parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"),
                parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        double podstawaOpodatkowania = umowaP.getPodstawaOpodatkowana(obliczonaPodstawa, umowaP.getKosztyUzyskaniaPrzychodu());
        
        double zaliczkaNaPodatekDochodowy = umowaP.getZaliczkaNaPodatke(podstawaOpodatkowania, parametryUmowy.get("procentPodatku"));
        assertEquals(zaliczkaNaPodatekDochodowy, 135.297, 0.001);
    }
    
    @Test
    public void testPodatekPotraconyUmowyZlecenie() {        
        double podatekPotracony = umowaZ.getPodatekPotracony(umowaZ.getZaliczkaNaPodatke(parametryUmowy.get("kwotaDochodu"), parametryUmowy.get("procentPodatku")), umowaZ.getKwotaWolnaOdPodatku());
        assertEquals(podatekPotracony, 180.0, 0.001);
    }
    
     @Test
    public void testZaliczkaDoUrzeduSkarbowegoUmowyZlecenie() {    
        double obliczonaPodstawa = umowaZ.obliczonaPodstawa(
                parametryUmowy.get("kwotaDochodu"),
                parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"),
                parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"),
                parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        double podstawaOpodatkowania = 
                umowaZ.getPodstawaOpodatkowana(obliczonaPodstawa, umowaZ.getKosztyUzyskaniaPrzychodu());
        double skladkaZdrowotna2 = 
                Umowa.obliczProcentOd(obliczonaPodstawa, parametryUmowy.get("oprocentowanieSkladkaZdrowotna2"));
        
        double zaliczkaDoUrzeduSkarbowego = umowaZ.getZaliczkaNaUS(umowaZ.getZaliczkaNaPodatke(podstawaOpodatkowania, parametryUmowy.get("procentPodatku")), skladkaZdrowotna2, umowaZ.getKwotaWolnaOdPodatku());
        assertEquals(zaliczkaDoUrzeduSkarbowego, 57.38, 0.01);
    }
    
    @Test
    public void testZaliczkaDoUrzeduSkarbowegoUmowyOPrace() {    
        double obliczonaPodstawa = umowaZ.obliczonaPodstawa(
                parametryUmowy.get("kwotaDochodu"),
                parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"),
                parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"),
                parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        double podstawaOpodatkowania = 
                umowaP.getPodstawaOpodatkowana(obliczonaPodstawa, umowaP.getKosztyUzyskaniaPrzychodu());
        double skladkaZdrowotna2 = 
                Umowa.obliczProcentOd(obliczonaPodstawa, parametryUmowy.get("oprocentowanieSkladkaZdrowotna2"));
        
        double zaliczkaDoUrzeduSkarbowego = umowaP.getZaliczkaNaUS(umowaP.getZaliczkaNaPodatke(podstawaOpodatkowania, parametryUmowy.get("procentPodatku")), skladkaZdrowotna2, umowaP.getKwotaWolnaOdPodatku());
        assertEquals(zaliczkaDoUrzeduSkarbowego, 22.09, 0.01);
    }
    
     @Test
    public void testWynagrodzenieUmowyZlecenie() {    
        double obliczonaPodstawa = umowaZ.obliczonaPodstawa(
                parametryUmowy.get("kwotaDochodu"),
                parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"),
                parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"),
                parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        double podstawaOpodatkowania = 
                umowaZ.getPodstawaOpodatkowana(obliczonaPodstawa, umowaZ.getKosztyUzyskaniaPrzychodu());
        double skladkaEmerytalna = 
                Umowa.obliczProcentOd(parametryUmowy.get("kwotaDochodu"), parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"));
        double skladkaRentowa = 
                Umowa.obliczProcentOd(parametryUmowy.get("kwotaDochodu"), parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"));
        double skladkaChorobowa = 
                Umowa.obliczProcentOd(parametryUmowy.get("kwotaDochodu"), parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        double skladkaZdrowotna1 = 
                Umowa.obliczProcentOd(obliczonaPodstawa, parametryUmowy.get("oprocentowanieSkladkaZdrowotna1"));  
        double skladkaZdrowotna2 = 
                Umowa.obliczProcentOd(obliczonaPodstawa, parametryUmowy.get("oprocentowanieSkladkaZdrowotna2"));         
        double zaliczkaDoUrzeduSkarbowego = 
                umowaZ.getZaliczkaNaUS(umowaZ.getZaliczkaNaPodatke(podstawaOpodatkowania, parametryUmowy.get("procentPodatku")), skladkaZdrowotna2, umowaZ.getKwotaWolnaOdPodatku());
        
        double Wynagrodzenie = umowaZ.getWynagrodzenie(umowaZ.getParametrUmowy("kwotaDochodu"), skladkaEmerytalna, 
                skladkaRentowa, skladkaChorobowa, skladkaZdrowotna1, zaliczkaDoUrzeduSkarbowego);        
        
        assertEquals(Wynagrodzenie, 727.856, 0.001);       
    }
    
    @Test
    public void testWynagrodzenieUmowyOPrace() {    
        double obliczonaPodstawa = umowaP.obliczonaPodstawa(
                parametryUmowy.get("kwotaDochodu"),
                parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"),
                parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"),
                parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        double podstawaOpodatkowania = 
                umowaP.getPodstawaOpodatkowana(obliczonaPodstawa, umowaP.getKosztyUzyskaniaPrzychodu());
        double skladkaEmerytalna = 
                Umowa.obliczProcentOd(parametryUmowy.get("kwotaDochodu"), parametryUmowy.get("oprocentowanieUbezpieczenieEmerytalne"));
        double skladkaRentowa = 
                Umowa.obliczProcentOd(parametryUmowy.get("kwotaDochodu"), parametryUmowy.get("oprocentowanieUbezpieczenieRentowe"));
        double skladkaChorobowa = 
                Umowa.obliczProcentOd(parametryUmowy.get("kwotaDochodu"), parametryUmowy.get("oprocentowanieUbezpieczenieChorobowe"));
        double skladkaZdrowotna1 = 
                Umowa.obliczProcentOd(obliczonaPodstawa, parametryUmowy.get("oprocentowanieSkladkaZdrowotna1"));  
        double skladkaZdrowotna2 = 
                Umowa.obliczProcentOd(obliczonaPodstawa, parametryUmowy.get("oprocentowanieSkladkaZdrowotna2"));         
        double zaliczkaDoUrzeduSkarbowego = 
                umowaP.getZaliczkaNaUS(umowaP.getZaliczkaNaPodatke(podstawaOpodatkowania, parametryUmowy.get("procentPodatku")), skladkaZdrowotna2, umowaP.getKwotaWolnaOdPodatku());
        
        double Wynagrodzenie = umowaP.getWynagrodzenie(umowaP.getParametrUmowy("kwotaDochodu"), skladkaEmerytalna, 
                skladkaRentowa, skladkaChorobowa, skladkaZdrowotna1, zaliczkaDoUrzeduSkarbowego);        
        
        assertEquals(Wynagrodzenie, 763.146, 0.001);       
    }
}


