package com.germinus.easyconf.taglib;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.configuration.Configuration;

import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;
import javax.servlet.jsp.tagext.TagData;

/**
 * Used to declare the property value as a JSP scripting variable
 * 
 * @author jferrer
 */
public class PropertyTei extends TagExtraInfo {

    /**
     * Return information about the scripting variables to be created.
     */
    public VariableInfo[] getVariableInfo(TagData data) {

        String type = (String)data.getAttribute("type");
        if (StringUtils.isEmpty(type)) {
            type = "java.lang.String";
        }

        return new VariableInfo[] {
            new VariableInfo(data.getAttributeString("id"),
                             type,
                             true,
                             VariableInfo.AT_END )
        };

    }
}
