package br.ufsc.framework.controller.util.datamodel;

import br.ufsc.framework.controller.util.FacesUtil;
import br.ufsc.framework.dao.util.filter.BeanFilter;
import br.ufsc.framework.dao.util.filter.SearchFilter;
import org.richfaces.component.UIDataScroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanFilterDataModel extends ListDataModel implements IDataModel {

    private List<Object> list;
    private BeanFilter searchFilter;

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanFilterDataModel.class);

    private Set<Integer> rowsToUpdate = new HashSet<>();

    public BeanFilterDataModel(BeanFilter searchFilter) throws Exception {
        this.searchFilter = searchFilter;
        // searchFilter.getService().addDataModelUpdateListener(this);
        updateModel(true);
    }

	/*
     * (non-Javadoc)
	 *
	 * @see br.com.roxs.br.ufsc.framework.controller.util.IDataModel#getFilters()
	 */

    @Override
    public Map<String, Object> getFilters() {
        return searchFilter.getFilters();
    }

	/*
     * (non-Javadoc)
	 *
	 * @see
	 * br.com.roxs.br.ufsc.framework.controller.util.IDataModel#setItemsPerPage(java
	 * .lang .Integer)
	 */

    @Override
    public void setItemsPerPage(Integer i) {
        searchFilter.setItemsPerPage(i);
    }

	/*
     * (non-Javadoc)
	 *
	 * @see br.com.roxs.br.ufsc.framework.controller.util.IDataModel#getItemsPerPage()
	 */

    @Override
    public Integer getItemsPerPage() {
        return searchFilter.getItemsPerPage();
    }

	/*
     * (non-Javadoc)
	 *
	 * @see br.com.roxs.br.ufsc.framework.controller.util.IDataModel#size()
	 */

    @Override
    public Integer size() {
        return getRowCount();
    }


    @Override
    public SearchFilter getSearchFilter() {
        return searchFilter;
    }


    @Override
    public String getSortField() {
        return searchFilter.getSortField();
    }


    @Override
    public boolean isSortDescending() {
        return searchFilter.isSortDescending();
    }


    @Override
    public void setSortDescending(boolean sortDescending) {
        searchFilter.setSortDescending(sortDescending);

    }


    @Override
    public void setSortField(String sortField) {
        searchFilter.setSortField(sortField);

    }

    //
    // public void updateModel(boolean isDataChange) throws Exception {
    // list = searchFilter.filterValues();
    // setWrappedData(list);
    // }


    @Override
    public void updateModel(boolean isDataChange) {
        updateModel(isDataChange, null);
    }


    @Override
    public void updateModel(boolean isDataChange, String table) {

        try {
            list = searchFilter.filterValues();
            setWrappedData(list);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
        }

        if (table != null) {
            UIData uiData = (UIData) FacesUtil.findComponent(FacesContext.getCurrentInstance().getViewRoot(), table);
            if (uiData != null) {
                uiData.setFirst(0);
                uiData.getAttributes().put(UIDataScroller.SCROLLER_STATE_ATTRIBUTE, 1);
            }
        }

    }


    @Override
    public boolean isFilterEnabled() {
        return searchFilter.isFilterEnabled();
    }


    @Override
    public Integer getStartItem() {
        return searchFilter.getStartItem();
    }


    @Override
    public Integer getCurrentPage() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Integer getStartItemFirstPage() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Integer getStartItemLastPage() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Integer getStartItemNextPage() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Integer getStartItemPreviousPage() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void clearFilters() {
        searchFilter.clearFilters();

    }

    @Override
    public Set<Integer> getRowsToUpdate() {
        return rowsToUpdate;
    }

    public void setRowsToUpdate(Set<Integer> rowsToUpdate) {
        this.rowsToUpdate = rowsToUpdate;
    }

}
