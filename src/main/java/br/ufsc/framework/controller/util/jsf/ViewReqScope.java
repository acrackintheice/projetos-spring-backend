package br.ufsc.framework.controller.util.jsf;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import javax.faces.context.FacesContext;
import java.util.Map;

public class ViewReqScope implements Scope {

    @Override
    public Object get(String name, ObjectFactory objectFactory) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> viewMap = context.getViewRoot().getViewMap();
        Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

        if (viewMap.containsKey(name)) {
            Object object = viewMap.get(name);
            requestMap.put(name, object);
            return object;
        } else if (requestMap.containsKey(name)) {
            Object object = requestMap.get(name);
            viewMap.put(name, object);
            return object;
        } else {
            Object object = objectFactory.getObject();
            viewMap.put(name, object);
            requestMap.put(name, object);

            return object;
        }

    }

    @Override
    public Object remove(String name) {
//		return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        //Not supported
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }
}
