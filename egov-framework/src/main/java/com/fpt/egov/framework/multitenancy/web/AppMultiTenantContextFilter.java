package com.fpt.egov.framework.multitenancy.web;

import com.fpt.egov.framework.multitenancy.context.AppMultiTenantContext;
import com.fpt.egov.framework.multitenancy.context.AppMultiTenantContextThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by tungnq on 7/29/2014.
 */
public class AppMultiTenantContextFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AppMultiTenantContextFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        AppMultiTenantContextThreadLocal.init();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest)servletRequest;

            //Populate X-TenantId from request header
            AppMultiTenantContext multiTenantContext = AppMultiTenantContextThreadLocal.get();
            if(multiTenantContext == null) {
                multiTenantContext = new AppMultiTenantContext();
            }

            String tenantId = request.getHeader(AppMultiTenantContext.TENANT_ID_KEY);

            if(LOG.isInfoEnabled()) {
                LOG.info(MessageFormat.format("AppMultiTenantContextFilter - Request for TenantId=\"{0}\"", tenantId));
            }

            multiTenantContext.setTenantId(tenantId);

            AppMultiTenantContextThreadLocal.set(multiTenantContext);

            //Forward to next process
            filterChain.doFilter(servletRequest, servletResponse);
        }
        finally {
            // Clean up ThreadLocal
            AppMultiTenantContextThreadLocal.unset();
        }
    }

    @Override
    public void destroy() {
    }
}
