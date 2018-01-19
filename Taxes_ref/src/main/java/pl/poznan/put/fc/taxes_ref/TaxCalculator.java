package pl.poznan.put.fc.taxes_ref;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class TaxCalculator {
    
	public static void main(String[] args) {
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String umowa;
                        double podstawa;
			System.out.print("Podaj kwotę dochodu: ");	
			podstawa = Double.parseDouble(br.readLine());
			
			System.out.print("Typ umowy: (P)raca, (Z)lecenie: ");
			umowa = br.readLine().substring(0,1);
                  
                        Map<String, Double> parametryUmowy = new HashMap<>();
                        parametryUmowy.put("kwotaDochodu", podstawa);
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
                        Umowa u = UmowaFactory.getUmowa(umowa, parametryUmowy);
                        u.informacjeOUmowie();
			
		} catch (Exception ex) {
			System.out.println("Błędna kwota");
			System.err.println(ex);
			return;
		}
        }		
}
