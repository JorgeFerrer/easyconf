package com.germinus.easyconf;


/**
 * Thrown when an XML configuration file for a requested component exists
 * but there is not a file which defines de digester rules which should be
 * used to parse it 
 *
 * @author  Jorge Ferrer
 * @version $Revision$
 *
 */
public class DigesterRulesNotFoundException extends ConfigurationException {

    private String digesterRulesFileName;
    public DigesterRulesNotFoundException(String componentName, String digesterRulesFileName) {
        super(componentName);
        this.digesterRulesFileName = digesterRulesFileName;
    }

    public String getDigesterRulesFileName() {
        return digesterRulesFileName;
    }
    
    public String getMessage() {
        return super.getMessage() + ". There should be a file named " + 
        	digesterRulesFileName + " which should contain the digester rules " +
        	"for parsing the XML file";
    }
}
