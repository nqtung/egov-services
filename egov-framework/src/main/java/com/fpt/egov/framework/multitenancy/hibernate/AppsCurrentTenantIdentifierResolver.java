package com.fpt.egov.framework.multitenancy.hibernate;

import com.fpt.egov.framework.multitenancy.context.AppMultiTenantContext;
import com.fpt.egov.framework.multitenancy.context.AppMultiTenantContextThreadLocal;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * Created by tungnq on 7/29/2014.
 */
public class AppsCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private static final Logger LOG = LoggerFactory.getLogger(AppsCurrentTenantIdentifierResolver.class);

    @Override
    public String resolveCurrentTenantIdentifier() {
        AppMultiTenantContext multiTenantContext = AppMultiTenantContextThreadLocal.get();

        String tenantId = null;

        if(multiTenantContext != null) {
            tenantId = multiTenantContext.getTenantId();
        }

        LOG.info(MessageFormat.format("Found TenantId=\"{0}\"", tenantId));

        return tenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
