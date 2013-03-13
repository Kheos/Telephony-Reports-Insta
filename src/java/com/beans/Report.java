/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

/**
 *
 * @author Nico
 */
public class Report {
	
	/*
	 *	Fix Telephony
	 */
	private String linesFix;
	private String fixLocalCallsFix;
	private String fixInternationalCallsFix;
	private String fixTotalFix;
	private String varLocalCallsFix;
	private String varInternationalCallsFix;
	private String varTotalFix;
	
	/*
	 *	3G Cards
	 */
	private String lines3G;
	private String fixLocalData3G;
	private String fixInternationalData3G;
	private String fixTotal3G;
	private String varLocalData3G;
	private String varInternationalData3G;
	private String varTotal3G;
	
	/*
	 *	Mobile Telephony
	 */
	private String linesMobile;
	private String fixLocalCallsMobile;
	private String fixInternationalCallsMobile;
	private String fixLocalDataMobile;
	private String fixInternationalDataMobile;
	private String fixTotalMobile;
	private String varLocalCallsMobile;
	private String varInternationalCallsMobile;
	private String varLocalDataMobile;
	private String varInternationalDataMobile;
	private String varTotalMobile;
	
	/*
	 *	BlackBerry Smartphones
	 */
	private String linesBB;
	private String fixLocalCallsBB;
	private String fixInternationalCallsBB;
	private String fixLocalDataBB;
	private String fixInternationalDataBB;
	private String fixTotalBB;
	private String varLocalCallsBB;
	private String varInternationalCallsBB;
	private String varLocalDataBB;
	private String varInternationalDataBB;
	private String varTotalBB;
	
	private Boolean editable;
	private int month;
	private int year;
	private String site;
	private String type;
	
	/**
	 * @return the report type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @param type Fix if the report is a fix telephony report, Mobile if it's a mobile telephony report, and Both if it's a fix and mobile report
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the boolean which allow by default to edit the report
	 */
	public Boolean getEditable() {
		return editable;
	}
	
	/**
	 * @param editable true if report will be editable, false else
	 */
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	/**
	 * @return the report month
	 */
	public int getMonth() {
		return month;
	}
	
	/**
	 * @param month the month of the report
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	
	/**
	 * @return the report unit contract
	 */
	public String getSite() {
		return site;
	}
	
	/**
	 * @param site the unit contract of the report
	 */
	public void setSite(String site) {
		this.site = site;
	}
	
	/**
	 * @return the report year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * @param year the year of the report
	 */
	public void setYear(int year) {
		this.year = year;
	}	
	
	/**
	 * @return the linesFix
	 */
	public String getLinesFix() {
		return linesFix;
	}
	
	/**
	 * @param linesFix the linesFix to set
	 */
	public void setLinesFix(String linesFix) {
		this.linesFix = linesFix;
	}

	/**
	 * @return the fixLocalCallsFix
	 */
	public String getFixLocalCallsFix() {
		return fixLocalCallsFix;
	}

	/**
	 * @param fixLocalCallsFix the fixLocalCallsFix to set
	 */
	public void setFixLocalCallsFix(String fixLocalCallsFix) {
		this.fixLocalCallsFix = fixLocalCallsFix;
	}

	/**
	 * @return the fixInternationalCallsFix
	 */
	public String getFixInternationalCallsFix() {
		return fixInternationalCallsFix;
	}

	/**
	 * @param fixInternationalCallsFix the fixInternationalCallsFix to set
	 */
	public void setFixInternationalCallsFix(String fixInternationalCallsFix) {
		this.fixInternationalCallsFix = fixInternationalCallsFix;
	}

	/**
	 * @return the fixTotalFix
	 */
	public String getFixTotalFix() {
		return fixTotalFix;
	}

	/**
	 * @param fixTotalFix the fixTotalFix to set
	 */
	public void setFixTotalFix(String fixTotalFix) {
		this.fixTotalFix = fixTotalFix;
	}

	/**
	 * @return the varLocalCallsFix
	 */
	public String getVarLocalCallsFix() {
		return varLocalCallsFix;
	}

	/**
	 * @param varLocalCallsFix the varLocalCallsFix to set
	 */
	public void setVarLocalCallsFix(String varLocalCallsFix) {
		this.varLocalCallsFix = varLocalCallsFix;
	}

	/**
	 * @return the varInternationalCallsFix
	 */
	public String getVarInternationalCallsFix() {
		return varInternationalCallsFix;
	}

	/**
	 * @param varInternationalCallsFix the varInternationalCallsFix to set
	 */
	public void setVarInternationalCallsFix(String varInternationalCallsFix) {
		this.varInternationalCallsFix = varInternationalCallsFix;
	}

	/**
	 * @return the varTotalFix
	 */
	public String getVarTotalFix() {
		return varTotalFix;
	}

	/**
	 * @param varTotalFix the varTotalFix to set
	 */
	public void setVarTotalFix(String varTotalFix) {
		this.varTotalFix = varTotalFix;
	}

	/**
	 * @return the lines3G
	 */
	public String getLines3G() {
		return lines3G;
	}

	/**
	 * @param lines3G the lines3G to set
	 */
	public void setLines3G(String lines3G) {
		this.lines3G = lines3G;
	}

	/**
	 * @return the fixLocalData3G
	 */
	public String getFixLocalData3G() {
		return fixLocalData3G;
	}

	/**
	 * @param fixLocalData3G the fixLocalData3G to set
	 */
	public void setFixLocalData3G(String fixLocalData3G) {
		this.fixLocalData3G = fixLocalData3G;
	}

	/**
	 * @return the fixInternationalData3G
	 */
	public String getFixInternationalData3G() {
		return fixInternationalData3G;
	}

	/**
	 * @param fixInternationalData3G the fixInternationalData3G to set
	 */
	public void setFixInternationalData3G(String fixInternationalData3G) {
		this.fixInternationalData3G = fixInternationalData3G;
	}

	/**
	 * @return the fixTotal3G
	 */
	public String getFixTotal3G() {
		return fixTotal3G;
	}

	/**
	 * @param fixTotal3G the fixTotal3G to set
	 */
	public void setFixTotal3G(String fixTotal3G) {
		this.fixTotal3G = fixTotal3G;
	}

	/**
	 * @return the varLocalData3G
	 */
	public String getVarLocalData3G() {
		return varLocalData3G;
	}

	/**
	 * @param varLocalData3G the varLocalData3G to set
	 */
	public void setVarLocalData3G(String varLocalData3G) {
		this.varLocalData3G = varLocalData3G;
	}

	/**
	 * @return the varInternationalData3G
	 */
	public String getVarInternationalData3G() {
		return varInternationalData3G;
	}

	/**
	 * @param varInternationalData3G the varInternationalData3G to set
	 */
	public void setVarInternationalData3G(String varInternationalData3G) {
		this.varInternationalData3G = varInternationalData3G;
	}

	/**
	 * @return the varTotal3G
	 */
	public String getVarTotal3G() {
		return varTotal3G;
	}

	/**
	 * @param varTotal3G the varTotal3G to set
	 */
	public void setVarTotal3G(String varTotal3G) {
		this.varTotal3G = varTotal3G;
	}

	/**
	 * @return the linesMobile
	 */
	public String getLinesMobile() {
		return linesMobile;
	}

	/**
	 * @param linesMobile the linesMobile to set
	 */
	public void setLinesMobile(String linesMobile) {
		this.linesMobile = linesMobile;
	}

	/**
	 * @return the fixLocalCallsMobile
	 */
	public String getFixLocalCallsMobile() {
		return fixLocalCallsMobile;
	}

	/**
	 * @param fixLocalCallsMobile the fixLocalCallsMobile to set
	 */
	public void setFixLocalCallsMobile(String fixLocalCallsMobile) {
		this.fixLocalCallsMobile = fixLocalCallsMobile;
	}

	/**
	 * @return the fixInternationalCallsMobile
	 */
	public String getFixInternationalCallsMobile() {
		return fixInternationalCallsMobile;
	}

	/**
	 * @param fixInternationalCallsMobile the fixInternationalCallsMobile to set
	 */
	public void setFixInternationalCallsMobile(String fixInternationalCallsMobile) {
		this.fixInternationalCallsMobile = fixInternationalCallsMobile;
	}

	/**
	 * @return the fixLocalDataMobile
	 */
	public String getFixLocalDataMobile() {
		return fixLocalDataMobile;
	}

	/**
	 * @param fixLocalDataMobile the fixLocalDataMobile to set
	 */
	public void setFixLocalDataMobile(String fixLocalDataMobile) {
		this.fixLocalDataMobile = fixLocalDataMobile;
	}

	/**
	 * @return the fixInternationalDataMobile
	 */
	public String getFixInternationalDataMobile() {
		return fixInternationalDataMobile;
	}

	/**
	 * @param fixInternationalDataMobile the fixInternationalDataMobile to set
	 */
	public void setFixInternationalDataMobile(String fixInternationalDataMobile) {
		this.fixInternationalDataMobile = fixInternationalDataMobile;
	}

	/**
	 * @return the fixTotalMobile
	 */
	public String getFixTotalMobile() {
		return fixTotalMobile;
	}

	/**
	 * @param fixTotalMobile the fixTotalMobile to set
	 */
	public void setFixTotalMobile(String fixTotalMobile) {
		this.fixTotalMobile = fixTotalMobile;
	}

	/**
	 * @return the varLocalCallsMobile
	 */
	public String getVarLocalCallsMobile() {
		return varLocalCallsMobile;
	}

	/**
	 * @param varLocalCallsMobile the varLocalCallsMobile to set
	 */
	public void setVarLocalCallsMobile(String varLocalCallsMobile) {
		this.varLocalCallsMobile = varLocalCallsMobile;
	}

	/**
	 * @return the varInternationalCallsMobile
	 */
	public String getVarInternationalCallsMobile() {
		return varInternationalCallsMobile;
	}

	/**
	 * @param varInternationalCallsMobile the varInternationalCallsMobile to set
	 */
	public void setVarInternationalCallsMobile(String varInternationalCallsMobile) {
		this.varInternationalCallsMobile = varInternationalCallsMobile;
	}

	/**
	 * @return the varLocalDataMobile
	 */
	public String getVarLocalDataMobile() {
		return varLocalDataMobile;
	}

	/**
	 * @param varLocalDataMobile the varLocalDataMobile to set
	 */
	public void setVarLocalDataMobile(String varLocalDataMobile) {
		this.varLocalDataMobile = varLocalDataMobile;
	}

	/**
	 * @return the varInternationalDataMobile
	 */
	public String getVarInternationalDataMobile() {
		return varInternationalDataMobile;
	}

	/**
	 * @param varInternationalDataMobile the varInternationalDataMobile to set
	 */
	public void setVarInternationalDataMobile(String varInternationalDataMobile) {
		this.varInternationalDataMobile = varInternationalDataMobile;
	}

	/**
	 * @return the varTotalMobile
	 */
	public String getVarTotalMobile() {
		return varTotalMobile;
	}

	/**
	 * @param varTotalMobile the varTotalMobile to set
	 */
	public void setVarTotalMobile(String varTotalMobile) {
		this.varTotalMobile = varTotalMobile;
	}

	/**
	 * @return the linesBB
	 */
	public String getLinesBB() {
		return linesBB;
	}

	/**
	 * @param linesBB the linesBB to set
	 */
	public void setLinesBB(String linesBB) {
		this.linesBB = linesBB;
	}

	/**
	 * @return the fixLocalCallsBB
	 */
	public String getFixLocalCallsBB() {
		return fixLocalCallsBB;
	}

	/**
	 * @param fixLocalCallsBB the fixLocalCallsBB to set
	 */
	public void setFixLocalCallsBB(String fixLocalCallsBB) {
		this.fixLocalCallsBB = fixLocalCallsBB;
	}

	/**
	 * @return the fixInternationalCallsBB
	 */
	public String getFixInternationalCallsBB() {
		return fixInternationalCallsBB;
	}

	/**
	 * @param fixInternationalCallsBB the fixInternationalCallsBB to set
	 */
	public void setFixInternationalCallsBB(String fixInternationalCallsBB) {
		this.fixInternationalCallsBB = fixInternationalCallsBB;
	}

	/**
	 * @return the fixLocalDataBB
	 */
	public String getFixLocalDataBB() {
		return fixLocalDataBB;
	}

	/**
	 * @param fixLocalDataBB the fixLocalDataBB to set
	 */
	public void setFixLocalDataBB(String fixLocalDataBB) {
		this.fixLocalDataBB = fixLocalDataBB;
	}

	/**
	 * @return the fixInternationalDataBB
	 */
	public String getFixInternationalDataBB() {
		return fixInternationalDataBB;
	}

	/**
	 * @param fixInternationalDataBB the fixInternationalDataBB to set
	 */
	public void setFixInternationalDataBB(String fixInternationalDataBB) {
		this.fixInternationalDataBB = fixInternationalDataBB;
	}

	/**
	 * @return the fixTotalBB
	 */
	public String getFixTotalBB() {
		return fixTotalBB;
	}

	/**
	 * @param fixTotalBB the fixTotalBB to set
	 */
	public void setFixTotalBB(String fixTotalBB) {
		this.fixTotalBB = fixTotalBB;
	}

	/**
	 * @return the varLocalCallsBB
	 */
	public String getVarLocalCallsBB() {
		return varLocalCallsBB;
	}

	/**
	 * @param varLocalCallsBB the varLocalCallsBB to set
	 */
	public void setVarLocalCallsBB(String varLocalCallsBB) {
		this.varLocalCallsBB = varLocalCallsBB;
	}

	/**
	 * @return the varInternationalCallsBB
	 */
	public String getVarInternationalCallsBB() {
		return varInternationalCallsBB;
	}

	/**
	 * @param varInternationalCallsBB the varInternationalCallsBB to set
	 */
	public void setVarInternationalCallsBB(String varInternationalCallsBB) {
		this.varInternationalCallsBB = varInternationalCallsBB;
	}

	/**
	 * @return the varLocalDataBB
	 */
	public String getVarLocalDataBB() {
		return varLocalDataBB;
	}

	/**
	 * @param varLocalDataBB the varLocalDataBB to set
	 */
	public void setVarLocalDataBB(String varLocalDataBB) {
		this.varLocalDataBB = varLocalDataBB;
	}

	/**
	 * @return the varInternationalDataBB
	 */
	public String getVarInternationalDataBB() {
		return varInternationalDataBB;
	}

	/**
	 * @param varInternationalDataBB the varInternationalDataBB to set
	 */
	public void setVarInternationalDataBB(String varInternationalDataBB) {
		this.varInternationalDataBB = varInternationalDataBB;
	}

	/**
	 * @return the varTotalBB
	 */
	public String getVarTotalBB() {
		return varTotalBB;
	}

	/**
	 * @param varTotalBB the varTotalBB to set
	 */
	public void setVarTotalBB(String varTotalBB) {
		this.varTotalBB = varTotalBB;
	}
}
