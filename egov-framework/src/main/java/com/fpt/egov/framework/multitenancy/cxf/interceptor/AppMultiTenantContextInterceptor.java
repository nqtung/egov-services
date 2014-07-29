package com.fpt.egov.framework.multitenancy.cxf.interceptor;

import com.fpt.egov.framework.multitenancy.context.AppMultiTenantContext;
import com.fpt.egov.framework.multitenancy.context.AppMultiTenantContextThreadLocal;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import java.text.MessageFormat;
import java.util.List;
import org.w3c.dom.Element;

/**
 * Created by tungnq on 7/29/2014.
 */
public class AppMultiTenantContextInterceptor extends AbstractSoapInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(AppMultiTenantContextInterceptor.class);
    private QName msgTenantIdHeader = new QName(AppMultiTenantContext.TENANT_ID_KEY);

    public AppMultiTenantContextInterceptor() {
        super(Phase.PRE_INVOKE);
        if (LOG.isInfoEnabled()) {
            LOG.info("AppMultiTenantContextInterceptor init...");
        }
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        AppMultiTenantContext multiTenantContext = AppMultiTenantContextThreadLocal.get();
        if(multiTenantContext == null) {
            multiTenantContext = new AppMultiTenantContext();
        }

        if(multiTenantContext.isTenantIdEmpty()) {
            //Populate X-TenantId from request header
            Header tenantIdHeader = message.getHeader(msgTenantIdHeader);
            Element tenantIdElement = (tenantIdHeader == null ? null : (Element) tenantIdHeader.getObject());
            String tenantIdValue = (tenantIdElement == null ? null : tenantIdElement.getTextContent());

            if (tenantIdValue != null && tenantIdValue.length() > 0) {
                if (LOG.isInfoEnabled()) {
                    LOG.info(MessageFormat.format("Request for TenantId=\"{0}\"", tenantIdValue));
                }

                multiTenantContext.setTenantId(tenantIdValue);
            } else {
                if (LOG.isInfoEnabled()) {
                    LOG.info("Can not find TenantId in header!");
                }
                String faultMsg = "Missing tenant id value!";
                QName faultCode = new QName("S001");
                throw new SoapFault(faultMsg, faultCode);
            }
        }

        //set multiTenantContext back to ThreadLocal
        AppMultiTenantContextThreadLocal.set(multiTenantContext);
    }
}
