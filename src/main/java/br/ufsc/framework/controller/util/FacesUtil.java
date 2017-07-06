package br.ufsc.framework.controller.util;

import br.ufsc.framework.services.InternationalizationService;
import br.ufsc.framework.services.util.Strings;
import org.apache.commons.beanutils.PropertyUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.Collator;
import java.util.*;

public class FacesUtil {

    private FacesUtil() {

    }

    public static HttpSession getSession() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);

        return session;

    }

//	public static void addToReRender(String id) {
//		AjaxContext ac = AjaxContext.getCurrentInstance();
//		UIComponent component = FacesUtil.findComponent(FacesContext.getCurrentInstance().getViewRoot(), id);
//		ac.addComponentToAjaxRender(component);
//	}

    @SuppressWarnings("unchecked")
    public static UIComponent findComponent(UIComponent component, final String id) {

        final String componentId = component.getClientId(FacesContext.getCurrentInstance());
        Iterator kids;
        UIComponent kid;
        UIComponent found;

        if (componentId.endsWith(id)) {
            return component;
        }

        kids = component.getChildren().iterator();

        while (kids.hasNext()) {
            kid = (UIComponent) kids.next();
            found = findComponent(kid, id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static List<SelectItem> getSelectItemsFromList(List list, final String idKey, final String nameKey, boolean sort)
        throws Exception {

        List<SelectItem> result = new ArrayList<>();

        if (sort) {

            Collections.sort(list, new Comparator() {

                @Override
                public int compare(Object m1, Object m2) {

                    Collator c = Collator.getInstance();

                    try {
                        return c.compare(PropertyUtils.getProperty(m1, nameKey),
                            PropertyUtils.getProperty(m2, nameKey));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return 0;
                }

            });
        }

        for (Object o : list) {

            result.add(new SelectItem(PropertyUtils.getProperty(o, idKey).toString(), PropertyUtils.getProperty(o, nameKey).toString()));

        }

        return result;

    }

    public static void generateFacesMessages(Severity severity, List<String> messages) {

        for (String message : messages) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));
        }
    }

    public static void generateFacesMessages(Severity severity, List<String> messages, InternationalizationService i18n) {

        for (String message : messages) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, i18n.getMessage(message), null));
        }
    }

    public static void generateFacesMessage(Severity severity, String message, InternationalizationService i18n) {

        List<String> messages = new ArrayList<>();
        messages.add(message);
        generateFacesMessages(severity, messages, i18n);
    }

    public static void generateFacesMessage(Severity severity, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));
    }

    public static void generateFacesMessage(Exception e) {

        StringWriter sw = new StringWriter();

        PrintWriter writ = new PrintWriter(sw);

        e.printStackTrace(writ);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, sw.toString(), null));
    }

    public static <T extends UIComponent> T createComponent(Class<T> componentClass) {

        try {

            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

            T component = componentClass.newInstance();
            component.setId(viewRoot.createUniqueId());

            return component;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static <T extends UIComponent> T createComponent(Class<T> componentClass, String id) {

        try {

            T component = componentClass.newInstance();
            component.setId(id);

            return component;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getFullUrl(FacesContext fc, String url) {
        return getFullUrl(fc, url, false);
    }

    public static String getFullUrl(FacesContext fc, String url, boolean forceHttps) {
        String scheme = ((HttpServletRequest) fc.getExternalContext().getRequest()).getScheme();
        scheme = (forceHttps) ? "https" : scheme;
        String serverName = ((HttpServletRequest) fc.getExternalContext().getRequest()).getServerName();
        Integer serverPort = ((HttpServletRequest) fc.getExternalContext().getRequest()).getServerPort();
        String requestContextPath = fc.getExternalContext().getRequestContextPath();

        String urlPath = scheme + "://" + serverName + ((serverPort != 8080 && serverPort != 443) ? ":" + serverPort : "")
            + requestContextPath;

        return urlPath + "/" + url;

    }

    public static ValueExpression getValueExpression(FacesContext fc, String expression, Class<?> expressionClass) {

        ELContext ec = fc.getELContext();
        Application app = fc.getApplication();
        ExpressionFactory factory = app.getExpressionFactory();

        return factory.createValueExpression(ec, expression, expressionClass);

    }

    public static MethodExpression getMethodExpression(FacesContext fc, String expression) {
        return getMethodExpression(fc, expression, Void.TYPE, new Class<?>[0]);

    }

    public static MethodExpression getMethodExpression(FacesContext fc, String expression, Class<?> returnType, Class<?>[] args) {
        ELContext ec = fc.getELContext();
        Application app = fc.getApplication();
        ExpressionFactory factory = app.getExpressionFactory();

        return factory.createMethodExpression(ec, expression, returnType, args);

    }

    public static void downloadFile(String filename, byte[] content, int fileSize, String mimeType) throws IOException {

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        HttpServletResponse response = (HttpServletResponse) context.getResponse();

        response.setHeader("Content-Disposition", "attachment;filename=" + Strings.removeAccents(filename).replaceAll(" ", "_"));
        response.setContentLength(fileSize);
        response.setContentType(mimeType);

        InputStream in = new ByteArrayInputStream(content);
        OutputStream out = response.getOutputStream();

        byte[] buf = new byte[fileSize];
        int count;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        in.close();
        out.flush();
        out.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public static void redirect(String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext()
            .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + url);
        FacesContext.getCurrentInstance().responseComplete();

    }

    public static void refreshModal(String comp) {
        FacesContext.getCurrentInstance().getViewRoot().findComponent(comp).getChildren().clear();
    }

}
