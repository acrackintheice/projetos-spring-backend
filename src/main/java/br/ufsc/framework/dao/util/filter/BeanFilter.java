package br.ufsc.framework.dao.util.filter;

import br.ufsc.framework.services.context.AppContext;
import br.ufsc.framework.services.util.Strings;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.Collator;
import java.util.*;

public class BeanFilter extends SearchFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanFilter.class);

    private static final long serialVersionUID = 1L;

    private List<?> list = new ArrayList<Object>();

    public BeanFilter(Class<?> clazz) {
        super(clazz);
    }

    public BeanFilter(Class<?> clazz, List<?> list) {
        super(clazz);
        this.list = list;
    }

    public boolean filter(Object o) {
        return filter(o, getFilters());
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    @SuppressWarnings("unchecked")
    public boolean filter(Object o, List<Filter> filters, Map<String, Object> fieldValues) {

        boolean result = true;

        for (Filter e : filters) {

            if (e instanceof ORFilter) {

                boolean orResult = false;

                for (Filter orf : ((ORFilter) e).getFilters()) {
                    List<Filter> orFilter = new ArrayList<>();
                    orFilter.add(orf);
                    orResult = orResult || filter(o, orFilter, fieldValues);
                }
                result = result && orResult;

            } else if (e instanceof ANDFilter) {

                boolean andResult = true;

                for (Filter andf : ((ANDFilter) e).getFilters()) {
                    List<Filter> andFilter = new ArrayList<>();
                    andFilter.add(andf);
                    andResult = andResult && filter(o, andFilter, fieldValues);
                }
                result = result && andResult;

            } else {

                FilterEntry entry = (FilterEntry) e;

                Object inputValue = fieldValues.get(entry.getFieldKey());

                Object value;

                try {
                    value = PropertyUtils.getNestedProperty(o, entry.getTargetBeanProperty());
                } catch (Exception ex) {
                    value = null;
                }

                if (entry.getComparisonType() == ComparisonType.ID_EQUALS) {

                    result = result
                        && ((inputValue != null && inputValue.toString().length() == 0) || (new Long(inputValue.toString()) == 0L) || (value != null && Long.parseLong(inputValue.toString()) == Long.parseLong(value.toString())));
                }

                if (entry.getComparisonType() == ComparisonType.LESS_THAN) {

                    result = result && ((inputValue == null) || (value != null && ((Comparable) value).compareTo(inputValue) > 0));
                }

                if (entry.getComparisonType() == ComparisonType.LESS_OR_EQUALS_THAN) {

                    result = result && ((inputValue == null) || (value != null && ((Comparable) value).compareTo(inputValue) >= 0));
                }

                if (entry.getComparisonType() == ComparisonType.EQUALS) {

                    result = result && ((inputValue == null) || (value != null && value.equals(inputValue)));

                }

                if (entry.getComparisonType() == ComparisonType.NOT_NULL) {

                    result = result && (value != null);

                }

                if (entry.getComparisonType() == ComparisonType.BOOLEAN_EQUALS) {

                    result = result
                        && ((inputValue == null || inputValue.toString().length() == 0) || (value != null && Boolean.valueOf(value.toString())
                        .equals(Boolean.valueOf(inputValue.toString()))));

                }

                if (entry.getComparisonType() == ComparisonType.NOT_EQUALS) {

                    result = result && ((inputValue == null) || (value != null && !value.equals(inputValue)));

                }

                if (entry.getComparisonType() == ComparisonType.NOT_EMPTY) {

                    result = result && ((value != null) && (!value.toString().trim().equals("")));

                }

                if (entry.getComparisonType() == ComparisonType.MORE_THAN) {

                    result = result && ((inputValue == null) || (value != null && ((Comparable) value).compareTo(inputValue) < 0));
                }

                if (entry.getComparisonType() == ComparisonType.MORE_OR_EQUALS_THAN) {

                    result = result && ((inputValue == null) || (value != null && ((Comparable) value).compareTo(inputValue) <= 0));
                }

                if (entry.getComparisonType() == ComparisonType.CONTAINS_TEXT) {
                    result = result
                        && (inputValue == null || inputValue.toString().length() == 0 || (value != null && Strings.findInsideString(value.toString(),
                        inputValue.toString())));

                }
            }

        }

        return result;
    }

    public boolean filter(Object o, Map<String, Object> fieldValues) {
        return filter(o, getFiltersList(), fieldValues);

    }

    @SuppressWarnings("unchecked")
    public List<Object> filterValues(List<?> objects, Map<String, Object> mapValues) {

        List<Object> result = new ArrayList();

        for (Object o : objects) {
            if (filter(o, mapValues))
                result.add(o);
        }

        Collections.sort(result, new Comparator() {


            @Override
            public int compare(Object m1, Object m2) {

                Collator c = Collator.getInstance();

                try {

                    Object o1 = PropertyUtils.getProperty(m1, getSortField());
                    Comparable p1 = (o1 instanceof Comparable) ? (Comparable) o1 : (o1 != null) ? o1.toString() : null;

                    Object o2 = PropertyUtils.getProperty(m2, getSortField());
                    Comparable p2 = (o2 instanceof Comparable) ? (Comparable) o2 : (o2 != null) ? o2.toString() : null;

                    return (!sortDescending) ? c.compare(p1, p2) : c.compare(p2, p1);

                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }

                return 0;
            }

        });

        return result;
    }

    public List<Object> filterValues(List<?> objects) {
        return filterValues(objects, getConvertedFilters());
    }


    @Override
    public List<Object> filterValues() throws Exception {

        if (list == null || list.size() == 0) {

            AbstractPlatformTransactionManager tx = AppContext.getBean(AbstractPlatformTransactionManager.class, "txManager");

            TransactionStatus txStatus = tx.getTransaction(new DefaultTransactionAttribute(TransactionAttribute.ISOLATION_READ_UNCOMMITTED));

            Collection<EntityManagerFactory> collection = AppContext.getBeans(EntityManagerFactory.class);

            EntityManager em = null;

            for (EntityManagerFactory emf : collection) {
                em = emf.createEntityManager();
                break;
            }

            Criteria crit = ((Session) em.getDelegate()).createCriteria(persistentClass);

            list = crit.list();

            tx.commit(txStatus);

            em.close();
        }

        return filterValues(list);
    }


    @Override
    public List<Object> filterValues(boolean paginated) throws Exception {
        return filterValues();
    }


    @Override
    public List<Map<String, Object>> projectValues(boolean paginated) throws Exception {
        return null;
    }


    @Override
    public List<Map<String, Object>> projectValues() throws Exception {
        return null;
    }


    @Override
    public Integer countValues() throws Exception {
        return filterValues().size();
    }


    @Override
    public List<Map<String, Object>> projectValues(boolean paginated, SearchFilter inFilter) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Integer countValues(SearchFilter inFilter) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void scrollValues(boolean paginated, ScrollableResultsVisitor visitor) throws Exception {
        // TODO Auto-generated method stub

    }


    @Override
    public void scrollValues(ScrollableResultsVisitor visitor) throws Exception {
        // TODO Auto-generated method stub

    }


    @Override
    public void scrollValues(boolean paginated, SearchFilter inFilter, ScrollableResultsVisitor visitor) throws Exception {
        // TODO Auto-generated method stub

    }

}
