/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

/**
 *
 * @author 251362
 */
public class ExtractTab {

    private String country;
    private String contractName;
    private String type;
    private int month;
    private int year;
    private int quantity;
    private float totalCost;
    private float arpu;
    
    private int extractStatus;
    
    public ExtractTab() {
        this.extractStatus = 0;
    }
    
    public ExtractTab(int month, int year) {
        this.country = null;
        this.contractName = null;
        this.type = null;
        this.month = month;
        this.year = year;
        this.quantity = 0;
        this.totalCost = 0;
        this.arpu = 0;
        this.extractStatus = 0;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the contractName
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * @param contractName the contractName to set
     */
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the totalCost
     */
    public float getTotalCost() {
        return totalCost;
    }

    /**
     * @param totalCost the totalCost to set
     */
    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * @return the arpu
     */
    public float getArpu() {
        return arpu;
    }

    /**
     * @param arpu the arpu to set
     */
    public void setArpu(float arpu) {
        this.arpu = arpu;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the extractStatus
     */
    public int getExtractStatus() {
        return extractStatus;
    }

    /**
     * @param extractStatus the extractStatus to set
     * Set 0 if extract is empty
     * Set 1 if extract is not completely filled
     * Set 2 if extract is completely filled
     */
    public void setExtractStatus(int extractStatus) {
        this.extractStatus = extractStatus;
    }
}
