/*
 * Copyright 2004-2005 Germinus XXI
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.germinus.easyconf.taglib;

import com.germinus.easyconf.ConfReader;
import com.germinus.easyconf.ComponentProperties;

import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;
import org.apache.struts.util.RequestUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.ArrayList;


/**
 * Read a configuration property and expose it as a page variable and attribute
 * Examples of use:
 *
 * &gt;%@ taglib uri="/WEB-INF/tld/easyconf.tld" prefix="easyconf" %>
 *
 * &gt;easyconf:property id="registration_list"
 *                  component="registration"
 *                  property="registration.list"
 *                  type="java.util.List"/>
 * &gt;logic:iterate id="item" name="registration_list">
 *   &gt;bean:write name="item"/>    &gt;br/>
 * &gt;/logic:iterate>
 *
 * &gt;easyconf:property id="registration_disabled"
 *                  component="registration"
 *                  property="registration.disabled"/>
 * &gt;logic:equal name="registration_disabled" value="true">
 *   The registration is disabled
 * &gt;/logic:equal>
 */
public class PropertyTag extends BodyTagSupport {

    protected String id = null;
    protected String component = null;
    protected String property = null;
    protected String type = null;
    protected String defaultValue = "not-configured";
    private static final List EMPTY_LIST = new ArrayList();

    public String getId() {
        return (this.id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getProperty() {
        return (this.property);
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getType() {
        return (this.type);
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getDefaultValue() {
        return (this.defaultValue);
    }

    /**
     * Note: currently this is only used if type is String
     * @param defaultValue
     */
    public void setValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Check if we need to evaluate the body of the tag
     *
     * @exception javax.servlet.jsp.JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        return (EVAL_BODY_BUFFERED);

    }


    /**
     * Save the body content of this tag (if any), or throw a JspException
     * if the value was already defined.
     *
     * @exception JspException if value was defined by an attribute
     */
    public int doAfterBody() throws JspException {
        return (SKIP_BODY);

    }


    /**
     * Retrieve the required property and expose it as a scripting variable.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
        Object value = null;
        ComponentProperties conf = ConfReader.getConfiguration(component).
        	getProperties();
        value = readProperty(conf);

       
        if (value == null) {
            JspException e = new JspException("The value of the property is null");
            RequestUtils.saveException(pageContext, e);
            throw e;
        }
        pageContext.setAttribute(id, value);
        return (EVAL_PAGE);

    }

    private Object readProperty(ComponentProperties conf) throws JspException {
        String type = this.type;
        if (StringUtils.isEmpty(type)) {
            type = "java.lang.String";
        }
        Object value = this.defaultValue;
        if (type.equals("java.util.List")) {
            value = conf.getList(property, EMPTY_LIST);
        } else if (type.equals("java.lang.Integer")) {
            value = conf.getInteger(property, new Integer(0));
        } else if (type.equals("java.lang.String")) {
            value = conf.getString(property, defaultValue);
        } else if (type.equals("java.lang.Double")) {
            value = new Double(conf.getDouble(property));
        } else if (type.equals("java.lang.Float")) {
            value = new Float(conf.getFloat(property));
        } else if (type.equals("java.lang.Byte")) {
            value = new Byte(conf.getByte(property));
        } else if (type.equals("java.math.BigDecimal")) {
            value = conf.getBigDecimal(property);
        } else if (type.equals("java.lang.BigInteger")) {
            value = conf.getBigInteger(property);
        } else if (type.equals("java.lang.Boolean")) {
            value = new Boolean(conf.getBoolean(property));
        } else if (type.equals("java.lang.Short")) {
            value = new Short(conf.getShort(property));
        } else if (type.equals("java.lang.Long")) {
            value = new Long(conf.getLong(property));
        } else {
                JspException e = new JspException("Unsupported type: " +type);
                RequestUtils.saveException(pageContext, e);
                throw e;
        }
        return value;
    }

    public void release() {
        super.release();
        id = null;
        component = null;
        property = null;
        type = null;
        defaultValue = null;
    }


}
