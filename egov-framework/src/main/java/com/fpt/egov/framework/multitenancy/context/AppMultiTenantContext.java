package com.fpt.egov.framework.multitenancy.context;

/**
 * Created by tungnq on 7/29/2014.
 */
public class AppMultiTenantContext {
    public final static String TENANT_ID_KEY = "X-TenantId";

    /**
     * tenantId = request.getHeader("X-TenantId");
     */
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     *
     * @return
     */
    public boolean isTenantIdEmpty() {
        return (tenantId == null || tenantId.trim().length() == 0);
    }

    @Override
    public String toString() {
        return "AppMultiTenantContext{" +
                "tenantId='" + tenantId + '\'' +
                '}';
    }
}
