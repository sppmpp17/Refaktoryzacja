/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.poznan.put.fc.taxes_ref;

import java.util.Map;

/**
 *
 * @author fenix
 */
public class UmowaFactory {
    public static Umowa getUmowa(String typUmowy, Map<String, Double> parametryUmowy) {
        Umowa umowa = null;
        switch(typUmowy) {
            case "P": umowa = new UmowaOPrace(parametryUmowy); break;
            case "Z": umowa = new UmowaZlecenie(parametryUmowy); break;
            default: System.out.println("Nieznany typ umowy!");
        }
        return umowa;
    }
}
