/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forms;

import com.bdd.UnitReportDao;
import com.beans.UnitReport;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Enji
 */
public class UnitReportForm {

    private UnitReportDao unitReportDao;
    private String nameUnitReport, descriptionUnitReport, siteCodeUnitReport, siteNameUnitReport, countryUnitReport;

    /**
     *
     * @param UnitReportDao
     * @throws WriteException
     */
    @SuppressWarnings("empty-statement")
    public UnitReportForm(UnitReportDao unitReportDao) {
        this.unitReportDao = unitReportDao;

        //On initialise des variables utilisées dans différentes méthodes

        this.nameUnitReport = null;
        this.descriptionUnitReport = null;
        this.siteCodeUnitReport = null;
        this.siteNameUnitReport = null;
        this.countryUnitReport = null;
    }

    /**
     *
     * @param nameUnitReport Nom du contrat
     * @param descriptionUnitReport Type de contrat : Fixed, Mobile ou Both
     * @param siteCodeUnitReport Référence du site
     * @param siteNameUnitReport Nom du site
     * @param countryUnitReport Pays référant du contrat
     * @return Map contenant un contrat avec ses sites
     */
    public void addUnitReport(String nameUnitReport, String descriptionUnitReport, String siteCodeUnitReportint, List<String> siteNameUnitReport, String countryUnitReport) {
        for (i = 0; i < list.lenght(); i++) {
            st.executeUpdate
        }
    }

}
