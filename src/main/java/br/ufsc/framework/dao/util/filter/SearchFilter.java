package br.ufsc.framework.dao.util.filter;

import br.ufsc.framework.services.context.AppContext;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

public abstract class SearchFilter implements Serializable, Cloneable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchFilter.class);

    private static final long serialVersionUID = 1L;

    protected Class<?> persistentClass;

    public SearchFilter(Class<?> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public enum ComparisonType {
        LESS_THAN, LESS_OR_EQUALS_THAN, EQUALS, NOT_EQUALS, MORE_THAN, MORE_OR_EQUALS_THAN, CONTAINS_TEXT, STARTS_WITH, ENDS_WITH, ID_EQUALS, BOOLEAN_EQUALS, NOT_EMPTY, BOOLEAN_NOT_EQUALS, IN, DELEGATE_FILTER, IS_EMPTY, IS_NOT_EMPTY, CONTAINS_TEXT_PRESERVE_CASE, NOT_NULL, PHONETIC
    }

    public enum ProjectionType {
        COUNT, DEFAULT, LIST, LUCENE_INDEXED
    }

    public static class SortEntry {
        private String field;
        private boolean descending = false;

        private boolean ignoreLuceneSortSufix = false;

        public SortEntry(String field, boolean descending) {
            this.field = field;
            this.descending = descending;
        }

        public SortEntry(String field, boolean descending, boolean ignoreLuceneSortSufix) {
            this.field = field;
            this.descending = descending;
            this.ignoreLuceneSortSufix = ignoreLuceneSortSufix;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public boolean isDescending() {
            return descending;
        }

        public void setDescending(boolean descending) {
            this.descending = descending;
        }

        public boolean isIgnoreLuceneSortSufix() {
            return ignoreLuceneSortSufix;
        }

        public void setIgnoreLuceneSortSufix(boolean ignoreLuceneSortSufix) {
            this.ignoreLuceneSortSufix = ignoreLuceneSortSufix;
        }

    }

    ;

    protected Integer startItem = 0;

    protected Integer itemsPerPage;

    protected String sortField;

    protected boolean sortDescending;

    private String countProperty;

    protected List<Filter> filtersList = new ArrayList<>();

    protected Map<String, Object> filters = new FilterValuesMap();

    protected Map<String, Object> filtersDefaultValues = new HashMap<>();

    protected Map<String, Class<?>> filtersCast = new HashMap<>();

    protected List<SortEntry> sortList = new ArrayList<>();

    protected Map<String, String> projectionMap = new HashMap<>();

    protected Map<String, List<String>> inverseProjectionMap = new HashMap<>();

    protected Map<String, ProjectionType> projectionType = new HashMap<>();

    protected List<String> groups = new ArrayList<>();

    protected List<SearchFilterInterceptor> interceptors = new ArrayList<>();

    public SearchFilter() {
    }

//	public HibernateLuceneSearchFilter toHibernateLuceneSearchFilter() {
//		return new HibernateLuceneSearchFilter(persistentClass, startItem, itemsPerPage, sortField, sortDescending, filtersList, filters, filtersDefaultValues, filtersCast, sortList, projectionMap, projectionType, groups,
//				interceptors);
//	}

    public void addInterceptor(SearchFilterInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public void addProjection(String mapKey, String fieldName) {
        addProjection(mapKey, fieldName, ProjectionType.DEFAULT);
    }

    public void addGroupBy(String fieldName) {
        groups.add(fieldName);
    }

    public List<String> getGroupByList() {
        return Collections.unmodifiableList(groups);
    }

    public void addProjection(String mapKey, String fieldName, ProjectionType projectionType) {
        projectionMap.put(mapKey, fieldName);
        this.projectionType.put(mapKey, projectionType);

        List<String> l = this.inverseProjectionMap.get(fieldName);

        if (l == null)
            l = new ArrayList<>();

        inverseProjectionMap.put(fieldName, l);
        l.add(mapKey);
    }

    public Map<String, String> getProjections() {
        return Collections.unmodifiableMap(projectionMap);
    }

    public Map<String, ProjectionType> getProjectionsType() {
        return Collections.unmodifiableMap(projectionType);
    }

    public <K> SearchFilter addFilter(String fieldKey, Class<K> clazz, K defaultValue, ComparisonType comparisonType, String targetBeanProperty, Map<String, Object> params) {

        addFilter(new FilterEntry<>(fieldKey, clazz, defaultValue, comparisonType, targetBeanProperty, params));
        return this;

    }

    public <K> SearchFilter addFilter(String fieldKey, Class<K> clazz, K defaultValue, ComparisonType comparisonType, String targetBeanProperty) {

        addFilter(new FilterEntry<>(fieldKey, clazz, defaultValue, comparisonType, targetBeanProperty));
        return this;

    }

    public void addOrder(String fieldKey, boolean isDescending) {
        sortList.add(new SortEntry(fieldKey, isDescending));
    }

    public <K> SearchFilter addDelegateFilter(String fieldKey, Class<K> clazz, K defaultValue, String delegateFilterKey) {
        addFilter(buildFilter(fieldKey, clazz, defaultValue, delegateFilterKey));
        return this;
    }

    public static <K> Filter buildFilter(String fieldKey, Class<K> clazz, K defaultValue, String delegateFilterKey) {
        return new FilterEntry<>(fieldKey, clazz, defaultValue, delegateFilterKey);
    }

    public static <K> Filter buildFilter(String fieldKey, Class<K> clazz, K defaultValue, ComparisonType comparisonType, String targetBeanProperty) {
        return new FilterEntry<>(fieldKey, clazz, defaultValue, comparisonType, targetBeanProperty);
    }

    public boolean isProjected() {
        return getProjections().size() > 0;
    }

    public SearchFilter addFilter(Filter e) {
        filtersList.add(e);
        addValues(e);
        return this;
    }

    public SearchFilter addDisjunction(Filter... filters) {
        ORFilter or = new ORFilter();

        or.setFilters(Arrays.asList(filters));

        addFilter(or);
        return this;
    }

    @SuppressWarnings("unchecked")
    private void addValues(Filter e) {

        if (e instanceof ORFilter) {
            for (Filter e1 : ((ORFilter) e).getFilters())
                addValues(e1);
        } else if (e instanceof FilterEntry) {
            FilterEntry<?> entry = (FilterEntry<?>) e;
            filters.put(entry.getFieldKey(), entry.getDefaultValue());
            filtersDefaultValues.put(entry.getFieldKey(), entry.getDefaultValue());
            filtersCast.put(entry.getFieldKey(), entry.getClazz());

        }

    }

    public List<Filter> getFiltersList() {
        return Collections.unmodifiableList(filtersList);
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public Map<String, Object> getConvertedFilters() {
        return filters;
    }

    @SuppressWarnings("unchecked")
    public boolean isFilterEnabled() {
        return isFilterEnabled(filtersList);
    }

    private boolean isFilterEnabled(List<Filter> filtersList) {

        boolean empty = true;

        for (Filter f : filtersList) {
            if (f instanceof FilterEntry) {
                FilterEntry<?> entry = (FilterEntry<?>) f;

                empty = empty && (filters.get(entry.getFieldKey()) == null || filters.get(entry.getFieldKey()).toString().trim().equals("")

                    || (entry.getClazz().getSuperclass() == Number.class && filters.get(entry.getFieldKey()).toString().trim().equals("0")));

            } else if (f instanceof ORFilter)
                empty = empty && !isFilterEnabled(((ORFilter) f).getFilters());
        }

        return !empty;
    }

    public void clearFilters() {
        for (Entry<String, Object> e : filtersDefaultValues.entrySet())
            filters.put(e.getKey(), e.getValue());
    }

    public Map<String, Object> getFiltersDefaultValues() {
        return filtersDefaultValues;
    }

    public Integer getStartItem() {
        return startItem;
    }

    public void setStartItem(Integer startItem) {
        this.startItem = startItem;
    }

    public String getCountProperty() {
        return countProperty;
    }

    public void setCountProperty(String countProperty) {
        this.countProperty = countProperty;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {

        if (this.sortField != null && sortField != null) {
            if (this.sortField.equals(sortField))
                sortDescending = !sortDescending;
        }

        this.sortField = sortField;
    }

    public boolean isSortDescending() {
        return sortDescending;
    }

    public void setSortDescending(boolean sortDescending) {
        this.sortDescending = sortDescending;
    }

    public Map<String, Class<?>> getFiltersCast() {
        return filtersCast;
    }

    public List<SortEntry> getSortList() {
        return sortList;
    }

    public Map<String, String> getProjectionMap() {
        return projectionMap;
    }

    public Map<String, ProjectionType> getProjectionType() {
        return projectionType;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setFiltersList(List<Filter> filtersList) {
        this.filtersList = filtersList;
    }

    public void clearFiltersList() {
        this.filtersList.clear();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return BeanUtils.cloneBean(this);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new CloneNotSupportedException(e.getMessage());
        }
    }

    protected void doInterception() {
        for (SearchFilterInterceptor i : interceptors)
            i.intercept(this);
    }

    private class FilterValuesMap extends HashMap<String, Object> {

        private static final long serialVersionUID = 1L;

        private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        @Override
        public Object get(Object key) {
            Object o = super.get(key);

            Class<?> filterCastClass = filtersCast.get(key);

            if (filterCastClass == null)
                return o;

            if (o != null && filterCastClass != null)
                return typeConverter.convertIfNecessary(o, filterCastClass);
            else
                return o;
        }

    }

    public Object getById(Object id) throws Exception {
        AbstractPlatformTransactionManager tx = AppContext.getBean(AbstractPlatformTransactionManager.class);

        TransactionStatus txStatus = tx.getTransaction(new DefaultTransactionAttribute(TransactionAttribute.ISOLATION_READ_UNCOMMITTED));

        EntityManager em = AppContext.getBean(AbstractEntityManagerFactoryBean.class).getObject().createEntityManager();

        Object result = em.find(persistentClass, id);

        tx.commit(txStatus);

        em.close();

        return result;
    }

    public Map<String, Map<Integer, DelegateFilter>> getMapDelegateFilters(Criteria criteria, Map<String, Object> filterValues) {
        return new HashMap<>();
    }

    public abstract Integer countValues() throws Exception;

    public abstract Integer countValues(SearchFilter inFilter) throws Exception;

    public abstract List<Object> filterValues() throws Exception;

    public abstract List<Object> filterValues(boolean paginated) throws Exception;

    public abstract List<Map<String, Object>> projectValues(boolean paginated) throws Exception;

    public abstract List<Map<String, Object>> projectValues() throws Exception;

    public abstract List<Map<String, Object>> projectValues(boolean paginated, SearchFilter inFilter) throws Exception;

    public abstract void scrollValues(boolean paginated, ScrollableResultsVisitor visitor) throws Exception;

    public abstract void scrollValues(ScrollableResultsVisitor visitor) throws Exception;

    public abstract void scrollValues(boolean paginated, SearchFilter inFilter, ScrollableResultsVisitor visitor) throws Exception;

}
