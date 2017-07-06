package br.ufsc.framework.dao.util.filter;

import br.ufsc.framework.dao.util.filter.SearchFilter.ComparisonType;

import java.util.HashMap;
import java.util.Map;

public class FilterEntry<T> implements Filter {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String fieldKey;
    private T defaultValue = null;

    private ComparisonType comparisonType;
    private String targetBeanProperty;

    private Class<T> clazz;

    private String delegateFilterKey;

    private Map<String, Object> params = new HashMap<>();

    public FilterEntry(String fieldKey, Class<T> clazz, ComparisonType comparisonType, String targetBeanProperty) {
        this.fieldKey = fieldKey;
        this.clazz = clazz;
        this.comparisonType = comparisonType;
        this.targetBeanProperty = targetBeanProperty;

    }

    public FilterEntry(String fieldKey, Class<T> clazz, T defaultValue, ComparisonType comparisonType, String targetBeanProperty) {
        this.fieldKey = fieldKey;
        this.clazz = clazz;
        this.defaultValue = defaultValue;
        this.comparisonType = comparisonType;
        this.targetBeanProperty = targetBeanProperty;

    }

    public FilterEntry(String fieldKey, Class<T> clazz, T defaultValue, ComparisonType comparisonType, String targetBeanProperty,
                       Map<String, Object> params) {
        this.fieldKey = fieldKey;
        this.clazz = clazz;
        this.defaultValue = defaultValue;
        this.comparisonType = comparisonType;
        this.targetBeanProperty = targetBeanProperty;
        this.params = params;

    }

    public FilterEntry(String fieldKey, Class<T> clazz, T defaultValue, String delegateFilterKey) {
        this.fieldKey = fieldKey;
        this.clazz = clazz;
        this.defaultValue = defaultValue;
        this.delegateFilterKey = delegateFilterKey;
        this.comparisonType = ComparisonType.DELEGATE_FILTER;

    }

    public ComparisonType getComparisonType() {
        return comparisonType;
    }

    public void setComparisonType(ComparisonType comparisonType) {
        this.comparisonType = comparisonType;
    }

    public String getTargetBeanProperty() {
        return targetBeanProperty;
    }

    public void setTargetBeanProperty(String targetBeanProperty) {
        this.targetBeanProperty = targetBeanProperty;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getDelegateFilterKey() {
        return delegateFilterKey;
    }

    public void setDelegateFilterKey(String delegateFilterKey) {
        this.delegateFilterKey = delegateFilterKey;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

}
