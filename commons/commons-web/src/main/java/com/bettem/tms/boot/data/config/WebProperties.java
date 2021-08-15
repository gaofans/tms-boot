package com.bettem.tms.boot.data.config;

import java.util.List;

/**
 * @author GaoFans
 */
public class WebProperties {

    private List<String> xssExcludes;

    public List<String> getXssExcludes() {
        return xssExcludes;
    }

    public void setXssExcludes(List<String> xssExcludes) {
        this.xssExcludes = xssExcludes;
    }
}
