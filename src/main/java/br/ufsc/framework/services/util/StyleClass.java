package br.ufsc.framework.services.util;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.richfaces.component.UITab;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.Iterator;
import java.util.List;

public class StyleClass {

    public StyleClass() {
    }

    public static String getSelectedTabFromTabView(String idTabView, Integer activeIndex) {
        try {

            FacesContext f = FacesContext.getCurrentInstance();
            if (f != null) {
                UIComponent component = f.getViewRoot().findComponent(idTabView);
                if (component != null) {
                    if (component instanceof TabView) { //primefaces

                        //---------
//			        	Integer activeIndex = ((TabView)component).getActiveIndex();
//			        	List<UIComponent> tabs = ((TabView)component).getChildren();
//			        	return tabs.get(activeIndex).getId();

                        //-----------

                        List<UIComponent> tabs = component.getChildren();
                        Iterator<UIComponent> itTabs = tabs.iterator();

                        Integer i = 0;
                        while (itTabs.hasNext()) {
                            UIComponent tab = itTabs.next();
                            if (tab instanceof Tab) {

                                if (tab.isRendered()) {
                                    if (i.equals(activeIndex))
                                        return tab.getId();
                                    else
                                        i++;
                                }
                            }
                        }
                    }
                }
            }

            return "";

        } catch (Exception e) {
            return "";
        }
    }


    public static String getSelectedTab(String selectedTabAtual) {
        try {
            //se nao existir mensagem de erro retorna a tab atual que está selevionada
            if (!FacesContext.getCurrentInstance().getMessages().hasNext()) {
                return selectedTabAtual;
            }

            FacesContext f = FacesContext.getCurrentInstance();
            if (f != null) {
                Iterator<String> itClientIds = f.getClientIdsWithMessages();
                while (itClientIds.hasNext()) {
                    UIComponent component = f.getViewRoot().findComponent(itClientIds.next());
                    UIComponent htmlTab = getParentTab(component);
                    if (htmlTab != null)
                        return htmlTab.getId();
                }
            }
            return selectedTabAtual;

        } catch (Exception e) {
//			return "";
            return selectedTabAtual;
        }
    }


    public static String getStyleByTab(String id, String styleError, String styleOk) {
        if (!FacesContext.getCurrentInstance().getMessages().hasNext()) {
            return styleOk;//"";
        }

        FacesContext f = FacesContext.getCurrentInstance();

        Iterator<String> itClientIds = f.getClientIdsWithMessages();
        while (itClientIds.hasNext()) {
            String strID = itClientIds.next();
            if (strID != null) {
                UIComponent component = f.getViewRoot().findComponent(strID);
                UIComponent htmlTab = null;
                do {
                    htmlTab = getParentTab(component);
                    if (htmlTab != null) {
                        if (htmlTab.getId().equals(id))
                            return styleError;
                        try {
                            if (htmlTab.getClientId().equals(id))
                                return styleError;
                        } catch (Exception e) {
                        }
                        component = htmlTab.getParent();
                    }
                } while (htmlTab != null);
            }
        }

        return styleOk;//"";
    }


    private static UIComponent getParentTab(UIComponent component) {
        if (component == null)
            return null;

        if (component instanceof UITab) //richfaces
            return component;

        if (component instanceof Tab) //primefaces
            return component;

        UIComponent htmlTabComponent = null;
        if (component.getParent() != null) {
            htmlTabComponent = getParentTab(component.getParent());
        }

        return htmlTabComponent;
    }


    public static String getStyleByComponent(String id, String styleError, String styleOk) {
        if (FacesContext.getCurrentInstance().getMessages(id).hasNext()) {
            return styleError;
        } else {
            return styleOk;//"";
        }
    }

}
