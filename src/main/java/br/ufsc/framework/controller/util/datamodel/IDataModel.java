package br.ufsc.framework.controller.util.datamodel;

import br.ufsc.framework.dao.util.filter.SearchFilter;

import java.util.Map;
import java.util.Set;

public interface IDataModel extends DataModelUpdateListener {

    public abstract Map<String, Object> getFilters();

    public abstract void setItemsPerPage(Integer i);

    public abstract Integer getItemsPerPage();

    public abstract Integer size() throws Exception;

    public SearchFilter getSearchFilter();

    public String getSortField();

    public boolean isSortDescending();

    public void setSortDescending(boolean sortDescending);

    public void setSortField(String sortField);

    public boolean isFilterEnabled();

    public Integer getStartItem();

    public Integer getStartItemNextPage();

    public Integer getStartItemPreviousPage();

    public Integer getStartItemFirstPage();

    public Integer getStartItemLastPage();

    public Integer getCurrentPage();

    public void updateModel(boolean isDataChange, String table);

    public Set<Integer> getRowsToUpdate();

    public void clearFilters();

}
