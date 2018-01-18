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
import pl.poznan.put.fc.taxes_ref.UmowaFactory;

/**
 *
 * @author fenix
 */
public class TaxesTests {
    
    private Map<String, Double> parametryUmowy;
    
    public TaxesTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Map<String, Double> parametryUmowy = new HashMap<>();
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
        //Umowa u = UmowaFactory.getUmowa(umowa, parametryUmowy);
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
}
