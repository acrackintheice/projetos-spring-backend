package br.ufsc.framework.services.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.Map.Entry;

public class Maps {

    private static final Logger LOGGER = LoggerFactory.getLogger(Maps.class);

    private Maps() {

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Map convertMapToComparacao(Map m1, Boolean converterEmptyEmNull, Boolean convertDateToTime) {

        for (Object e : m1.entrySet()) {

            Object value = ((Entry) e).getValue();

            if (converterEmptyEmNull != null && converterEmptyEmNull &&
                value != null && value.toString().isEmpty()) {
                value = null;
            }

            if (convertDateToTime != null && convertDateToTime &&
                value != null && !value.toString().isEmpty() && (value instanceof Date)) {
                value = ((Date) value).getTime();
            }

            m1.put(((Entry) e).getKey(), value);
        }

        return m1;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Collection<String> getCamposDiferentes(Map m1, Map m2) {
        Collection<Entry<String, Object>> diferencas = (Collection<Entry<String, Object>>) CollectionUtils.disjunction(m1.entrySet(), m2.entrySet());

        Set<String> camposQueMudaram = new HashSet<>();

        for (Entry<String, Object> e : diferencas)
            camposQueMudaram.add(e.getKey());

        return camposQueMudaram;
    }


    @SuppressWarnings({"rawtypes"})
    public static Collection<String> getCamposDiferentes(Map m1, Map m2, Boolean converterEmptyEmNull, Boolean convertDateToTime, Boolean recursivo) {

        if (recursivo) {

            m1 = getMapFromMapRecursivo(m1, converterEmptyEmNull, convertDateToTime, recursivo);
            m2 = getMapFromMapRecursivo(m2, converterEmptyEmNull, convertDateToTime, recursivo);

        } else if (converterEmptyEmNull || convertDateToTime) {
            m1 = convertMapToComparacao(m1, converterEmptyEmNull, convertDateToTime);
            m2 = convertMapToComparacao(m2, converterEmptyEmNull, convertDateToTime);
        }

        return getCamposDiferentes(m1, m2);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <K> Collection<K> getCamposDiferentes(Map m1, Map m2, Class<K> keyClass) {
        Collection<Entry<K, Object>> diferencas = (Collection<Entry<K, Object>>) CollectionUtils.disjunction(m1.entrySet(), m2.entrySet());

        Set<K> camposQueMudaram = new HashSet<>();

        for (Entry<K, Object> e : diferencas)
            camposQueMudaram.add(e.getKey());

        return camposQueMudaram;
    }


    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> getMapFromCollection(Collection<V> list, Class<K> keyClass, String keyProperty) {

        try {

            Map<K, V> result = new HashMap<>();

            for (V item : list) {

                K key = (K) PropertyUtils.getProperty(item, keyProperty);

                result.put(key, item);
            }

            return result;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }


    public static Map<String, Object> getMapFromBean(Object bean, Boolean converterEmptyEmNull, Boolean convertDateToTime, Boolean recursivo) {

        Set<Integer> checkedObjects = new HashSet<>();
        return getMapFromBean(bean, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);
    }


    public static Map<String, Object> getMapFromBean(Object bean) {

        Set<Integer> checkedObjects = new HashSet<>();
        return getMapFromBean(bean, false, false, false, checkedObjects);
    }


    @SuppressWarnings("rawtypes")
    private static Map<String, Object> getMapFromBean(Object bean, Boolean converterEmptyEmNull, Boolean convertDateToTime, Boolean recursivo, Set<Integer> checkedObjects) {

        try {
            //checkedObjects é usado para prevenir loop infinito [ex: um objeto A referencia um objeto B. E o objeto B referencia esse mesmo objeto A]
            if (checkedObjects == null)
                checkedObjects = new HashSet<>();

            Integer valueIdentity = System.identityHashCode(bean);
            if (checkedObjects.contains(valueIdentity))
                return null;
            else
                checkedObjects.add(valueIdentity);

            Map<String, Object> m = new LinkedHashMap<>();

            for (PropertyDescriptor b : PropertyUtils.getPropertyDescriptors(bean)) {
                if (!b.getName().equals("class")) {
                    Object value = PropertyUtils.getProperty(bean, b.getName());

                    if (value != null && !value.toString().isEmpty()) {

                        if (convertDateToTime != null && convertDateToTime && (value instanceof Date))
                            value = ((Date) value).getTime();

                        if (recursivo) {
                            if (!value.getClass().getName().startsWith("java"))
                                value = getMapFromBean(value, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);

                            else if (value instanceof Collection)
                                value = getListMapFromCollection((Collection) value, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);

                            else if (value instanceof Map)
                                value = getMapFromMapRecursivo((Map) value, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);
                        }


                    } else if (converterEmptyEmNull != null && converterEmptyEmNull) {
                        value = null;
                    }

                    m.put(b.getName(), value);
                }
            }
            return m;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

    }


    public static List<Map<String, Object>> getListMapFromCollection(Collection<?> list, Boolean converterEmptyEmNull, Boolean convertDateToTime, Boolean recursivo) {

        Set<Integer> checkedObjects = new HashSet<>();
        return getListMapFromCollection(list, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);
    }


    private static List<Map<String, Object>> getListMapFromCollection(Collection<?> list, Boolean converterEmptyEmNull, Boolean convertDateToTime, Boolean recursivo, Set<Integer> checkedObjects) {

        //checkedObjects é usado para prevenir loop infinito [ex: um objeto A referencia um objeto B. E o objeto B referencia esse mesmo objeto A]
        if (checkedObjects == null)
            checkedObjects = new HashSet<>();

        Integer valueIdentity = System.identityHashCode(list);
        if (checkedObjects.contains(valueIdentity))
            return null;
        else
            checkedObjects.add(valueIdentity);

        List<Map<String, Object>> retorno = new ArrayList<>();

        try {

            Integer index = -1;
            for (Object value : list) {
//				Map<String, Object> m = getMapFromBean(item,converterEmptyEmNull,convertDateToTime,recursivo,checkedObjects);
//				if (m != null)
//					retorno.add(m);

                index++;
                if (value != null && !value.toString().isEmpty()) {

                    if (convertDateToTime != null && convertDateToTime && (value instanceof Date))
                        value = ((Date) value).getTime();

                    if (recursivo) {

                        if (!value.getClass().getName().startsWith("java")) {
                            Map<String, Object> m = getMapFromBean(value, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);
                            retorno.add(m);
                            continue;
                        } else if (value instanceof Collection) {
                            List<Map<String, Object>> lm = getListMapFromCollection((Collection) value, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);
                            Map<String, Object> mp = new HashMap<>();
                            mp.put(index.toString(), lm);
                            retorno.add(mp);
                            continue;
                        } else if (value instanceof Map) {
                            Map<String, Object> m = getMapFromMapRecursivo((Map) value, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);
                            retorno.add(m);
                            continue;
                        }

                    }

                } else if (converterEmptyEmNull != null && converterEmptyEmNull) {
                    value = null;
                }

                Map<String, Object> mp = new HashMap<>();
                mp.put(index.toString(), value);
                retorno.add(mp);

            }

            return retorno;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

    public static Map<String, Object> getMapFromMapRecursivo(Map map, Boolean converterEmptyEmNull, Boolean convertDateToTime, Boolean recursivo) {

        Set<Integer> checkedObjects = new HashSet<>();
        return getMapFromMapRecursivo(map, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);
    }


    private static Map<String, Object> getMapFromMapRecursivo(Map map, Boolean converterEmptyEmNull, Boolean convertDateToTime, Boolean recursivo, Set<Integer> checkedObjects) {

        //checkedObjects é usado para prevenir loop infinito [ex: um objeto A referencia um objeto B. E o objeto B referencia esse mesmo objeto A]
        if (checkedObjects == null)
            checkedObjects = new HashSet<>();

        Integer valueIdentity = System.identityHashCode(map);
        if (checkedObjects.contains(valueIdentity))
            return null;
        else
            checkedObjects.add(valueIdentity);

        Map<String, Object> retorno = new HashMap<>();

        try {

            for (Object o : map.entrySet()) {

                Object value = ((Entry) o).getValue();
                if (value != null && !value.toString().isEmpty()) {

                    if (convertDateToTime != null && convertDateToTime && (value instanceof Date))
                        value = ((Date) value).getTime();

                    if (recursivo) {
                        if (!value.getClass().getName().startsWith("java"))
                            value = getMapFromBean(value, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);

                        else if (value instanceof Collection)
                            value = getListMapFromCollection((Collection) value, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);

                        else if (value instanceof Map)
                            value = getMapFromMapRecursivo((Map) value, converterEmptyEmNull, convertDateToTime, recursivo, checkedObjects);
                    }

                } else if (converterEmptyEmNull != null && converterEmptyEmNull) {
                    value = null;
                }

                retorno.put(((Entry) o).getKey().toString(), value);
            }

            return retorno;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }

    }


//	public static Map<String, Object> getMapFromBean(Object bean, Boolean converterEmptyEmNull, Boolean convertDateToTime) {
//
//		try {
//			Map<String, Object> m = new LinkedHashMap<String, Object>();
//
//			for (PropertyDescriptor b : PropertyUtils.getPropertyDescriptors(bean)) {
//				if (!b.getName().equals("class")){
//					Object value = PropertyUtils.getProperty(bean, b.getName());
//
//					if (value != null && !value.toString().isEmpty()){
//
//						if (convertDateToTime !=null && convertDateToTime && (value instanceof Date))
//							value = ((Date)value).getTime();
//
//					}else if (converterEmptyEmNull != null && converterEmptyEmNull){
//						value = null;
//					}
//
//					m.put(b.getName(), value);
//				}
//			}
//			return m;
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			return null;
//		}
//
//	}


    public static Map<String, Object> getMapFromBean(Object bean, Set<String> propriedades, Boolean converterEmptyEmNull, Boolean convertDateToTime) {

        try {

            Map<String, Object> m = new LinkedHashMap<>();

            for (PropertyDescriptor b : PropertyUtils.getPropertyDescriptors(bean)) {
                if (!b.getName().equals("class") && (propriedades == null || propriedades.isEmpty() || propriedades.contains(b.getName()))) {
                    Object value = PropertyUtils.getProperty(bean, b.getName());

                    if (converterEmptyEmNull != null && converterEmptyEmNull &&
                        value != null && value.toString().isEmpty()) {
                        value = null;
                    }

                    if (convertDateToTime != null && convertDateToTime &&
                        value != null && !value.toString().isEmpty() && (value instanceof Date))
                        value = ((Date) value).getTime();

                    m.put(b.getName(), value);
                }
            }
            return m;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

    }

    public static Map<String, String> getMapStringFromBean(Object bean, Boolean converterEmptyEmNull, Boolean convertDateToTime, String... projecoes) {

        if (bean == null)
            return null;
        try {

            Set<String> propriedades = new HashSet<>();

            if (projecoes != null)
                for (String s : projecoes)
                    if (s != null)
                        propriedades.add(s.trim());

            Map<String, String> m = new LinkedHashMap<>();

            for (PropertyDescriptor b : PropertyUtils.getPropertyDescriptors(bean)) {
                if (!b.getName().equals("class") && (propriedades == null || propriedades.isEmpty() || propriedades.contains(b.getName()))) {
                    Object value = PropertyUtils.getProperty(bean, b.getName());

                    if (converterEmptyEmNull != null && converterEmptyEmNull &&
                        value != null && value.toString().isEmpty()) {
                        value = null;
                    }

                    if (convertDateToTime != null && convertDateToTime &&
                        value != null && !value.toString().isEmpty() && (value instanceof Date))
                        value = ((Date) value).getTime();

                    m.put(b.getName(), value.toString());
                }
            }
            return m;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

    }

    public static List<Map<String, String>> getMapStringFromCollection(Collection<?> list, Boolean converterEmptyEmNull, Boolean convertDateToTime, String... projecoes) {

        List<Map<String, String>> retorno = new ArrayList<>();

        try {

            for (Object item : list) {
                Map<String, String> m = getMapStringFromBean(item, converterEmptyEmNull, convertDateToTime, projecoes);
                if (m != null)
                    retorno.add(m);
            }

            return retorno;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

}
