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

/**
 * Builds filters from arrays of strings or up to three string paramters
 * 
 * @author jferrer
 */
public class Filter {

    private String[] selectors;

    public static Filter by(String first) {
        return new Filter(new String[]{first});
    }

    public static Filter by(String first, String second) {
        return new Filter(new String[]{first, second});
    }

    public static Filter by(String first, String second, String third) {
        return new Filter(new String[]{first, second, third});
    }

    public Filter(String[] selectors) {
        this.selectors = selectors;
    }

    public int size() {
        return selectors.length;
    }

    /**
     * Get a fragment of the filter which includes the first 'n' selectors
     * concatenated.
     *
     * Example: if the filter has two selectors (bar and foo). Fragments would
     * be:
     * <ul>
     *  <li>For n=2: "[bar][foo]"
     *  <li>For n=1: "[bar]"
     *  <li>For n=0: ""
     *  <li>Otherwise: throws IllegalArgumentException
     * </ul>
     * @param n
     * @return
     * @throws IllegalArgumentException if n < 1 or n > size()
     */
    public String getFilterSuffix(int n) {
        if ((n<0) || (n > size())) {
            throw new IllegalArgumentException("Imposible to obtain filter fragment for n="+n);
        }
        if (n==0) {
            return "";
        }
        StringBuffer filter = new StringBuffer();
        for (int i = 0; i < n; i++) {
            filter.append(Conventions.SELECTOR_START);
            filter.append(selectors[i]);
            filter.append(Conventions.SELECTOR_END);
        }
        return filter.toString();
    }

    public String toString() {
        return getFilterSuffix(size());
    }
}
