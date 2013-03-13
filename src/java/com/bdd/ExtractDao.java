/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

import com.beans.ExtractTab;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 *
 * @author Nico
 */
public interface ExtractDao {

    public Map<Integer, ExtractTab> extractCountryMonth(Map<Integer, ExtractTab> extractMap, String nameExtract, GregorianCalendar calendar, Boolean allMonth);

    public Map<Integer, ExtractTab> extractContractMonth(Map<Integer, ExtractTab> extractMap, String nameExtract, GregorianCalendar calendar, Boolean allMonth);

    public Map<Integer, ExtractTab> extractCountryFiscalYear(Map<Integer, ExtractTab> extractMap, String nameExtract, int year);

    public Map<Integer, ExtractTab> extractContractFiscalYear(Map<Integer, ExtractTab> extractMap, String nameExtract, int year);
    
}