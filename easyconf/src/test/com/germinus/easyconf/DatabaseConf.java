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

import java.util.ArrayList;
import java.util.List;


/**
 * <a href="DatabaseConf.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer
 * @version $Revision$
 *
 */
public class DatabaseConf {
    private List tables = new ArrayList();
    
    public void addTable(Table table) {
        tables.add(table);
    }

    public List getTables() {
        return tables;
    }
}
