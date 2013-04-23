/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

import com.beans.UnitReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Enji
 */
public class UnitReportDaoImpl implements UnitReportDao {

    private DaoFactory daoFactory;

    UnitReportDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    
}
