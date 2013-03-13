/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

import com.beans.Report;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author Nico
 */
public interface ReportDao {

    Map<Integer, Report> list(Map<Integer, Report> reportMap, String refContract, Calendar calendar, Boolean allMonth) throws DaoException;

    Map<Integer, String> siteList(Map<Integer, String> siteList, String login) throws DaoException;

    String getType(String refContract);

    Report handleInformations(String refContract, int month, int year, Boolean editable, Report report);

    Report update(int month, int year, String serviceType, String refContract, Report report) throws DaoException;

    Report save(int month, int year, String serviceType, String refContract, Report report) throws DaoException;
}
