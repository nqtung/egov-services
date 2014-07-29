package com.fpt.egov.framework.multitenancy.context;

/**
 * Created by tungnq on 7/29/2014.
 */
public class AppMultiTenantContextThreadLocal {
    public static final ThreadLocal<AppMultiTenantContext> multiTenantCtxThreadLocal = new ThreadLocal();

    /**
     *
     */
    public static void init() {
        set(new AppMultiTenantContext());
    }

    /**
     *
     * @param user
     */
    public static void set(AppMultiTenantContext user) {
        multiTenantCtxThreadLocal.set(user);
    }

    /**
     *
     */
    public static void unset() {
        multiTenantCtxThreadLocal.remove();
    }

    /**
     *
     * @return
     */
    public static AppMultiTenantContext get() {
        return multiTenantCtxThreadLocal.get();
    }
}
