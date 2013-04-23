/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

/**
 *
 * @author Enji
 */
public class UnitReport {

    private String nameUnitReport;
    private String descriptionUnitReport;
    private String siteCodeUnitReport;
    private String siteNameUnitReport;
    private String countryUnitReport;

    public UnitReport() {
        this.nameUnitReport = null;
        this.descriptionUnitReport = null;
        this.siteCodeUnitReport = null;
        this.siteNameUnitReport = null;
        this.countryUnitReport = null;
    }

    /**
     * @return the nameUnitReport
     */
    public String getnameUnitReport() {
        return nameUnitReport;
    }

    /**
     * @param nameUnitReport the nameUnitReport to set
     */
    public void setnameUnitReport(String nameUnitReport) {
        this.nameUnitReport = nameUnitReport;
    }

    /**
     * @return the descriptionUnitReport
     */
    public String getdescriptionUnitReport() {
        return descriptionUnitReport;
    }

    /**
     * @param descriptionUnitReport the descriptionUnitReport to set
     */
    public void setdescriptionUnitReport(String descriptionUnitReport) {
        this.descriptionUnitReport = descriptionUnitReport;
    }

   /**
     * @return the siteCodeUnitReport
     */
    public String getsiteCodeUnitReport() {
        return siteCodeUnitReport;
    }

    /**
     * @param siteCodeUnitReport the siteCodeUnitReport to set
     */
    public void setsiteCodeUnitReport(String siteCodeUnitReport) {
        this.siteCodeUnitReport = siteCodeUnitReport;
    }

    /**
     * @return the siteNameUnitReport
     */
    public String getsiteNameUnitReport() {
        return siteNameUnitReport;
    }

    /**
     * @param siteNameUnitReport the siteNameUnitReport to set
     */
    public void setsiteNameUnitReport(String siteNameUnitReport) {
        this.siteNameUnitReport = siteNameUnitReport;
    }
    
        /**
     * @return the countryUnitReport
     */
    public String getcountryUnitReport() {
        return countryUnitReport;
    }

    /**
     * @param countryUnitReport the countryUnitReport to set
     */
    public void setcountryUnitReport(String countryUnitReport) {
        this.countryUnitReport = countryUnitReport;
    }
}
