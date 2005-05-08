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
package com.germinus.easyconf;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.configuration.DatabaseConfiguration;

/**
 * Represents the URL to a datasource as specified in a properties file
 *
 * @author  Jorge Ferrer
 * @version $Revision$
 *
 */
public class DatasourceURL {

    private static final String DATASOURCE_PREFIX = Conventions.DATASOURCE_PREFIX;
    private static InitialContext ctx = null;
    private String dataSourceName;
    private String companyId;
    private String componentName;

    /**
     * @param datasourcePath
     * @param companyId
     * @param componentName
     */
    public DatasourceURL(String datasourcePath, String companyId, String componentName) {
        this.dataSourceName = datasourcePath.substring(DATASOURCE_PREFIX.length());
        this.companyId = companyId;
        this.componentName = componentName;
    }

    public DataSource getDatasource() {        
        try {
            return (DataSource)getContext().lookup(dataSourceName);
        } catch (NamingException e) {
            throw new ConfigurationException("Error loading datasource " + dataSourceName);
        }
    }

    private synchronized InitialContext getContext() throws NamingException {
        if (ctx == null) {
            ctx = new InitialContext();
        }
        return ctx;
    }

    protected String getTableName() {
        return "EasyConfProperties";
    }

    protected String getComponentColumnName() {
        return "component";
    }

    protected String getKeyColumnName() {
        return "key";
    }

    protected String getValueColumnName() {
        return "value";
    }

    public static boolean isDatasource(String fileName) {
        return fileName.startsWith(DATASOURCE_PREFIX);
    }

    public DatabaseConfiguration getConfiguration() {
        return new DatabaseConfiguration(getDatasource(),
                getTableName(),
                getComponentColumnName(),
                getKeyColumnName(),
                getValueColumnName(),
                componentName);
    }

}
