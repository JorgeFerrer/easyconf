package com.germinus.easyconf;

/**
 * Builds filters from arrays of strings or up to three string paramters
 * 
 * @author jferrer
 */
public class Filter {
    private String[] selectors;
    private static final char SEPARATOR = '.';

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
     * be (assuming the SEPARATOR='.'):
     * <ul>
     *  <li>For n=2: bar.foo
     *  <li>For n=1: bar
     *  <li>Otherwise: throws IllegalArgumentException
     * </ul>
     * @param n
     * @return
     * @throws IllegalArgumentException if n < 1 or n > size()
     */
    public String getFilterFragment(int n) {
        if ((n<1) || (n > size())) {
            throw new IllegalArgumentException("Imposible to obtain filter fragment for n="+n);
        }
        StringBuffer filter = new StringBuffer();
        for (int i = 0; i < n; i++) {
            if (i != 0) {
                filter.append(SEPARATOR);
            }
            filter.append(selectors[i]);
        }
        return filter.toString();
    }
}
