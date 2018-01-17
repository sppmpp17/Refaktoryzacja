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
public class UmowaOPrace extends Umowa {
public UmowaOPrace(Map<String, Double> parametryUmowy) {
        super(parametryUmowy);
        System.out.println("UMOWA-ZLECENIE");
    }
}
