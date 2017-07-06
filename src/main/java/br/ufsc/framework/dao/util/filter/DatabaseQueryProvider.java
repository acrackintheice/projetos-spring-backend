package br.ufsc.framework.dao.util.filter;

import org.hibernate.Session;

public interface DatabaseQueryProvider {

    public DatabaseQuery getQuery(Session session, Class<?> clazz, SearchFilter inFilter, boolean isCount, boolean isProjected, boolean isPaginated);

}
