package br.ufsc.framework.dao.util.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ORFilter implements Filter {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<Filter> filters = new ArrayList<>();

    public ORFilter(Filter... filtersArray) {
        Collections.addAll(filters, filtersArray);
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }


}
