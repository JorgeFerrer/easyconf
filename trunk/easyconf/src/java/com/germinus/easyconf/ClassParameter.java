package com.germinus.easyconf;

import org.apache.commons.configuration.Configuration;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Métodos de utilidad para trabajar con parámetros de configuración que representan
 * clases
 * Fecha: 09-jul-2004 -- 12:06:34
 * @author Jesús Jáimez Rodríguez <jesusjaimez@germinus.com>
 */
public class ClassParameter {

    private static final Log log = LogFactory.getLog(ClassParameter.class);

    public static Object getNewInstance(Configuration conf, String propertyName)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        String className = conf.getString(propertyName);
        log.info("Returning " + className + " class instance.");
        return ClasspathUtil.locateClass(className).newInstance();
    }

    public static Object getNewInstance(Properties props, String propertyName)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        String className = props.getProperty(propertyName);
        log.info("Returning " + className + " class instance.");
        return ClasspathUtil.locateClass(className).newInstance();
    }
}