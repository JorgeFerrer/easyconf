/**
 * ${NAME} does ...
 * @author jferrer
 */
package com.germinus.easyconf.struts;

import org.apache.struts.action.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.germinus.easyconf.ConfReader;

/**
 * Refresh the configuration of a given component which uses EasyConf. If
 * no component is specified all components of the current JVM are refreshed.
 * @author jferrer
 */
public class RefreshConfigurationAction extends Action {
    private static final String SUCCESS = "SUCCESS";

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest req,
                                 HttpServletResponse response) throws Exception {
        DynaActionForm dform = (DynaActionForm) form;
        if (dform != null) {
            String componentName = (String) dform.get("componentName");
            if (StringUtils.isBlank(componentName)) {
                ConfReader.getInstance().refreshAll();
            } else {
                ConfReader.getInstance().refreshComponent(componentName);
            }
        } else {
            ConfReader.getInstance().refreshAll();
        }
        return mapping.findForward(SUCCESS);
    }
}
