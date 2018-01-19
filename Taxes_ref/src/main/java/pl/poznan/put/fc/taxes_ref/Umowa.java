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
    
    public static double obliczProcentOd(double wartosc, double procent) {
        return wartosc*procent/100.0;
    }
    
    public double obliczonaPodstawa(double kwotaDochodu, double oprUbEmerytalne, double oprUbRentowe, double oprUbChorobowe) {
        return kwotaDochodu - obliczProcentOd(kwotaDochodu, (oprUbEmerytalne + oprUbRentowe + oprUbChorobowe));
    }
    
    public double obliczPodatekPotracony(double zaliczka, double pomniejszeniePodatku) {
        return zaliczka - pomniejszeniePodatku;
    }
    
    public double getParametrUmowy(String key) {
        return PARAMETRY_UMOWY.get(key);
    }
    
    public double getPodstawaOpodatkowana(double obliczonaPodstawa, double kosztyUzyskania) {
        return obliczonaPodstawa - kosztyUzyskania;
    }
    
    public double getZaliczkaNaPodatke(double obliczonaPodstawa, double procentPodatku) {
        return obliczProcentOd(obliczonaPodstawa, procentPodatku);
    }
    
    public double getPodatekPotracony(double zaliczkaNaPodatek, double kwotaWolnaOdPodatku) {
        return zaliczkaNaPodatek - kwotaWolnaOdPodatku;
    }
    
    public double getZaliczkaNaUS(double zaliczkaNaPodatek, double skladkaZdrowotna2, double kwotaWolnaOdPodatku) {
        return zaliczkaNaPodatek - skladkaZdrowotna2 - kwotaWolnaOdPodatku;
    }
    
    public double getWynagrodzenie(double kwotaDochodu, double skladkaEmerytalna, double skladkaRentowa, double skladkaChorobowa,
            double skladkaZdrowotna, double zaliczkaNaUS) {
        return kwotaDochodu - (skladkaEmerytalna + skladkaRentowa + skladkaChorobowa + skladkaZdrowotna + zaliczkaNaUS);
    }
    
    public abstract String infoKwotaWolnaOdPodatku();
    
    public abstract double getKosztyUzyskaniaPrzychodu();
    
    public abstract double getKwotaWolnaOdPodatku();
    
    protected void informacjeOUmowie() {
        double kwotaDochodu = getParametrUmowy("kwotaDochodu");
        double oprocentowanieUbezpieczenieEmerytalne = getParametrUmowy("oprocentowanieUbezpieczenieEmerytalne");
        double oprocentowanieUbezpieczenieRentowe = getParametrUmowy("oprocentowanieUbezpieczenieRentowe");
        double oprocentowanieUbezpieczenieChorobowe = getParametrUmowy("oprocentowanieUbezpieczenieChorobowe");
        double oprocentowanieSkladkaZdrowotna1 = getParametrUmowy("oprocentowanieSkladkaZdrowotna1");
        double oprocentowanieSkladkaZdrowotna2 = getParametrUmowy("oprocentowanieSkladkaZdrowotna2");
        double kosztyUzyskaniaPrzychodu = getKosztyUzyskaniaPrzychodu();
        double procentPodatku = getParametrUmowy("procentPodatku");
        double kwotaWolnaOdPodatku = getKwotaWolnaOdPodatku();
        
        
        // sesty dla tych obliczen - skopiowac Map'e z TaxCalkulatora jako inicjalizator testow
        double skladkaEmerytalna = obliczProcentOd(kwotaDochodu, oprocentowanieUbezpieczenieEmerytalne);
        double skladkaRentowa = obliczProcentOd(kwotaDochodu, oprocentowanieUbezpieczenieRentowe);
        double skladkaChorobowa = obliczProcentOd(kwotaDochodu, oprocentowanieUbezpieczenieChorobowe);
        double obliczonaPodstawa = obliczonaPodstawa(kwotaDochodu,
                    oprocentowanieUbezpieczenieEmerytalne,
                    oprocentowanieUbezpieczenieRentowe,
                    oprocentowanieUbezpieczenieChorobowe);
        double skladkaZdrowotna1 = obliczProcentOd(obliczonaPodstawa, oprocentowanieSkladkaZdrowotna1);
        double skladkaZdrowotna2 = obliczProcentOd(obliczonaPodstawa, oprocentowanieSkladkaZdrowotna2);
        double podstawaOpodatkowana = getPodstawaOpodatkowana(obliczonaPodstawa, kosztyUzyskaniaPrzychodu);
        double zaliczkaNaPodatek = getZaliczkaNaPodatke(podstawaOpodatkowana, procentPodatku);
        double zaliczkaNaUS = getZaliczkaNaUS(zaliczkaNaPodatek, skladkaZdrowotna2, getKwotaWolnaOdPodatku());
        double podatekPotracony = getPodatekPotracony(getZaliczkaNaPodatke(kwotaDochodu, procentPodatku), kwotaWolnaOdPodatku);
        double wynagrodzenie = getWynagrodzenie(kwotaDochodu, skladkaEmerytalna, 
                skladkaRentowa, skladkaChorobowa, skladkaZdrowotna1, zaliczkaNaUS);
        
        System.out.println("Podstawa wymiaru składek " + kwotaDochodu);
        
        System.out.println("Składka na ubezpieczenie emerytalne "
                        + DECIMAL_FORMAT_1.format(skladkaEmerytalna));
        System.out.println("Składka na ubezpieczenie rentowe    "
                        + DECIMAL_FORMAT_1.format(skladkaRentowa));
        System.out.println("Składka na ubezpieczenie chorobowe  "
                        + DECIMAL_FORMAT_1.format(skladkaChorobowa));
        System.out.println("Podstawa wymiaru składki na ubezpieczenie zdrowotne: " + obliczonaPodstawa);
        System.out.println("Składka na ubezpieczenie zdrowotne: 9% = "
                        + DECIMAL_FORMAT_1.format(skladkaZdrowotna1) + 
                " 7,75% = " + DECIMAL_FORMAT_1.format(skladkaZdrowotna2));

        System.out.println("Koszty uzyskania przychodu (stałe) " + kosztyUzyskaniaPrzychodu);
        System.out.println("Podstawa opodatkowania " + podstawaOpodatkowana + 
                " zaokrąglona " + DECIMAL_FORMAT_2.format(podstawaOpodatkowana));
        System.out.println("Zaliczka na podatek dochodowy 18 % = " + zaliczkaNaPodatek);
        System.out.print(infoKwotaWolnaOdPodatku());
        System.out.println("Podatek potrącony = " + 
                DECIMAL_FORMAT_1.format(podatekPotracony));
        System.out.println("Zaliczka do urzędu skarbowego = " + 
                DECIMAL_FORMAT_1.format(zaliczkaNaUS) + " po zaokrągleniu = " + DECIMAL_FORMAT_2.format(zaliczkaNaUS));
        System.out.println();
        System.out.println("Pracownik otrzyma wynagrodzenie netto w wysokości = " + DECIMAL_FORMAT_1.format(wynagrodzenie));
    }
}
