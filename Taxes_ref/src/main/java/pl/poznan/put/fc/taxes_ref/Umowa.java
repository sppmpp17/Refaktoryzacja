/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.poznan.put.fc.taxes_ref;

import java.text.DecimalFormat;
import java.util.Map;

/**
 *
 * @author fenix
 */
public abstract class Umowa {
    private final Map<String, Double> PARAMETRY_UMOWY;
    private final static DecimalFormat DECIMAL_FORMAT_1 = new DecimalFormat("#.00");
    private final static DecimalFormat DECIMAL_FORMAT_2 = new DecimalFormat("#");
    
    public Umowa(Map<String, Double> parametryUmowy) {
        PARAMETRY_UMOWY = parametryUmowy;
    }
    
    protected double obliczProcentOd(double wartosc, double procent) {
        return wartosc*procent/100.0;
    }
    
    protected double obliczonaPodstawa(double kwotaDochodu, double oprUbEmerytalne, double oprUbRentowe, double oprUbChorobowe) {
        return kwotaDochodu - obliczProcentOd(kwotaDochodu, (oprUbEmerytalne + oprUbRentowe + oprUbChorobowe));
    }
    
    protected void informacjeOUmowie() {
        double kwotaDochodu = PARAMETRY_UMOWY.get("kwotaDochodu");
        double oprocentowanieUbezpieczenieEmerytalne = PARAMETRY_UMOWY.get("oprocentowanieUbezpieczenieEmerytalne");
        double oprocentowanieUbezpieczenieRentowe = PARAMETRY_UMOWY.get("oprocentowanieUbezpieczenieRentowe");
        double oprocentowanieUbezpieczenieChorobowe = PARAMETRY_UMOWY.get("oprocentowanieUbezpieczenieChorobowe");
        double skladkaZdrowotna1 = PARAMETRY_UMOWY.get("skladkaZdrowotna1");
        double skladkaZdrowotna2 = PARAMETRY_UMOWY.get("skladkaZdrowotna2");
        
        System.out.println("Podstawa wymiaru składek " + kwotaDochodu);
        System.out.println("Składka na ubezpieczenie emerytalne "
                        + DECIMAL_FORMAT_1.format(obliczProcentOd(kwotaDochodu, oprocentowanieUbezpieczenieEmerytalne)));
        System.out.println("Składka na ubezpieczenie rentowe    "
                        + DECIMAL_FORMAT_1.format(obliczProcentOd(kwotaDochodu, oprocentowanieUbezpieczenieRentowe)));
        System.out.println("Składka na ubezpieczenie chorobowe  "
                        + DECIMAL_FORMAT_1.format(obliczProcentOd(kwotaDochodu, oprocentowanieUbezpieczenieChorobowe)));
        System.out.println("Podstawa wymiaru składki na ubezpieczenie zdrowotne: " +
                obliczonaPodstawa(kwotaDochodu,
                    oprocentowanieUbezpieczenieEmerytalne,
                    oprocentowanieUbezpieczenieRentowe,
                    oprocentowanieUbezpieczenieChorobowe));
        System.out.println("Składka na ubezpieczenie zdrowotne: 9% = "
                        + DECIMAL_FORMAT_1.format(obliczProcentOd(kwotaDochodu, skladkaZdrowotna1)) + 
                " 7,75% = " + DECIMAL_FORMAT_1.format(obliczProcentOd(kwotaDochodu, skladkaZdrowotna2)));
    }
}
