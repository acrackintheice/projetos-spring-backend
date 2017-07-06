package br.ufsc.framework.dao.util.filter;

public class DelegateFilter {

    private Object delegate;

    public DelegateFilter(Object o) {
        this.delegate = o;
    }

    public Object getDelegate() {
        return delegate;
    }

}
