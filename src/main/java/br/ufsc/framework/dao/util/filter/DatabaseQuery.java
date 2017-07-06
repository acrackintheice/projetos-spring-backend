package br.ufsc.framework.dao.util.filter;

import org.hibernate.HibernateException;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

import java.util.List;

public interface DatabaseQuery {

    public ScrollableResults scroll() throws HibernateException;

    public ScrollableResults scroll(ScrollMode scrollMode) throws HibernateException;

    public List list() throws HibernateException;

    public Object uniqueResult() throws HibernateException;

}
