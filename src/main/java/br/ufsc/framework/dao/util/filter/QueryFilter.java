package br.ufsc.framework.dao.util.filter;

public class QueryFilter {

    private String field;
    private String operator;
    private Object value;
    private boolean namedParameter = true;


    public QueryFilter(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public QueryFilter(String field, String operator, Object value, boolean namedParameter) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.namedParameter = namedParameter;
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isNamedParameter() {
        return namedParameter;
    }

    public void setNamedParameter(boolean namedParameter) {
        this.namedParameter = namedParameter;
    }

}
