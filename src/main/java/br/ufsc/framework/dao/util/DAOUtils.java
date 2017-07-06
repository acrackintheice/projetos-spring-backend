package br.ufsc.framework.dao.util;

import br.ufsc.framework.dao.util.filter.QueryFilter;
import br.ufsc.framework.services.InternationalizationService;

import javax.persistence.Query;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOUtils {

    private DAOUtils() {

    }

    public static String StringArrayToCommaString(Object[] array) {
        String idsStr = "";

        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1)
                idsStr += "'" + array[i] + "',";
            else
                idsStr += "'" + array[i] + "'";

        }

        return idsStr;

    }

    public static String StringArrayToCommaNumber(Object[] array) {
        String idsStr = "";

        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1)
                idsStr += "" + array[i] + ",";
            else
                idsStr += "" + array[i] + "";

        }

        return idsStr;

    }

    @SuppressWarnings("unchecked")
    private static boolean isInvalidSqlQueryFilter(QueryFilter f) {
        return (f.getValue() == null) || f.getValue().toString().trim().equals("") || (f.getValue() instanceof List && ((List) f.getValue()).size() == 0);
    }

    @SuppressWarnings("unchecked")
    public static String getSqlWhereQuery(List<QueryFilter> filters) {

        String defaultOperator = "AND";
        String sql = "";

        List<QueryFilter> filteredFilters = new ArrayList<>();

        for (QueryFilter filter : filters) {
            if (!isInvalidSqlQueryFilter(filter))
                filteredFilters.add(filter);
        }

        for (int i = 0; i < filteredFilters.size(); i++) {

            QueryFilter f = filteredFilters.get(i);

            if (f.getValue() instanceof List) {

                String values = "";

                for (int k = 0; k < ((List<Object>) f.getValue()).size(); k++) {
                    values += ":p" + (i + 1) + "_" + k;

                    if (k < ((List<Object>) f.getValue()).size() - 1)
                        values += " , ";
                }

                sql += f.getField() + " " + f.getOperator() + " (" + values + ")";

            } else {

                if (f.isNamedParameter())
                    sql += f.getField() + " " + f.getOperator() + " :p" + (i + 1);
                else
                    sql += f.getField() + " " + f.getOperator() + " " + f.getValue();
            }

            if (i < (filteredFilters.size() - 1))
                sql += " " + defaultOperator + " ";

        }
        if (filters.size() > 0)
            return "WHERE " + sql;
        else
            return "";
    }

    @SuppressWarnings("unchecked")
    public static void loadQueryFiltersValues(Query q, List<QueryFilter> filters) {

        Integer i = 1;
        for (QueryFilter f : filters) {

            if (f.isNamedParameter() && !isInvalidSqlQueryFilter(f)) {

                if (f.getValue() instanceof List) {

                    int k = 0;
                    for (Object o : ((List<Object>) f.getValue())) {
                        q.setParameter("p" + (i).toString() + "_" + k++, o);

                    }

                } else {
                    q.setParameter("p" + (i).toString(), f.getValue());
                }
                i++;
            }

        }
    }

    public static void setStmtStringOrNull(PreparedStatement stmt, int index, String value) throws Exception {
        if (value != null)
            stmt.setString(index, value);
        else
            stmt.setNull(index, Types.VARCHAR);
    }

    public static void setStmtBooleanOrNull(PreparedStatement stmt, int index, Boolean value) throws Exception {
        if (value != null)
            stmt.setBoolean(index, value);
        else
            stmt.setNull(index, Types.BOOLEAN);
    }

    public static void setStmtLongOrNull(PreparedStatement stmt, int index, Long value) throws Exception {
        if (value != null)
            stmt.setLong(index, value);
        else
            stmt.setNull(index, Types.NUMERIC);
    }

    public static void setStmtIntegerOrNull(PreparedStatement stmt, int index, Integer value) throws Exception {
        if (value != null)
            stmt.setInt(index, value);
        else
            stmt.setNull(index, Types.INTEGER);
    }

    public static void setStmtDateOrNull(PreparedStatement stmt, int index, Date value) throws Exception {
        if (value != null)
            stmt.setDate(index, new java.sql.Date(value.getTime()));
        else
            stmt.setNull(index, Types.DATE);
    }

    public static void setStmtTimestampOrNull(PreparedStatement stmt, int index, Timestamp value) throws Exception {
        if (value != null)
            stmt.setTimestamp(index, value);
        else
            stmt.setNull(index, Types.TIMESTAMP);
    }


    public static String betweenWithoutEqualQuery(String campo, String campoMin, String campoMax) {
        return "(" + campo + " > " + campoMin + " AND " + campo + " < " + campoMax + ")";
    }

    public static SQLException findSqlException(Throwable e) {

        if (e == null || e instanceof SQLException)
            return (SQLException) e;

        Throwable cause = e.getCause();

        if (cause == e) {
            if (e instanceof SQLException) {
                return (SQLException) e;
            } else {
                return null;
            }
        } else {
            return findSqlException(cause);
        }

    }

    public static String findSqlExceptionSybase(Throwable ex, InternationalizationService i18n) {

        String result = null;
        SQLException sqlex = DAOUtils.findSqlException(ex);
        if (sqlex != null) {
            if (sqlex.getErrorCode() == 2601) {
                result = i18n.getMessage("bd_err_unique_constraint");
            } else if (sqlex.getErrorCode() == 547) {
                result = i18n.getMessage("bd_err_dependent_foreign_key");
            } else {
                result = "Erro de acesso ao banco de dados: " + sqlex.getMessage();
            }
        }

        return result;
    }
}
