package com.usersauth.model;

import java.util.List;

public class Domain {
    private String domain;
    private List<String> entitlements;

    public Domain(String domain, List<String> entitlements) {
        this.domain = domain;
        this.entitlements = entitlements;
    }

    public String getDomain() {
        return domain;
    }

    public List<String> getEntitlements() {
        return entitlements;
    }
}
