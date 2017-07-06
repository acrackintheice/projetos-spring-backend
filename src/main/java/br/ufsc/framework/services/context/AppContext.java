package br.ufsc.framework.services.context;

import br.ufsc.framework.services.properties.CustomPropertyPlaceholderConfigurer;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AppContext {

    private static ApplicationContext ctx;

    private static final Map<String, Object> props = new ConcurrentHashMap<>();

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        if (ctx == null) {
            SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("system", "system", new ArrayList<GrantedAuthority>()));

            setApplicationContext(new ClassPathXmlApplicationContext(new String[]{"**/applicationContext*.xml"}));
        }

        return ctx;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> c) {

        Map m = AppContext.getApplicationContext().getBeansOfType(c);

        if (m.size() == 1)
            return (T) m.values().iterator().next();

        return null;

    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> c, String beanName) {

        T service = AppContext.getApplicationContext().getBean(beanName, c);

        return service;
    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> getBeans(Class<T> c) {

        Map m = AppContext.getApplicationContext().getBeansOfType(c);

        return m.values();

    }

    @SuppressWarnings("unchecked")
    public static <T> T getProperty(String propertyName, Class<T> c) {

        Object value = props.get(propertyName);

        if (value == null) {
            CustomPropertyPlaceholderConfigurer propertyConfigurer = getBean(CustomPropertyPlaceholderConfigurer.class);
            value = propertyConfigurer.getProps().get(propertyName);
            props.put(propertyName, value);
        }

        return new SimpleTypeConverter().convertIfNecessary(value, c);

    }

    public static Properties getProperties() {
        CustomPropertyPlaceholderConfigurer propertyConfigurer = getBean(CustomPropertyPlaceholderConfigurer.class);

        return propertyConfigurer.getProps();

    }

    public static List<?> listBeans() {
        for (String s : AppContext.getApplicationContext().getBeanDefinitionNames()) {
            System.out.println(s);
        }
        return null;
    }

}
