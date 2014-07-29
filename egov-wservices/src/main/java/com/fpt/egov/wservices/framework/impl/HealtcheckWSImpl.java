package com.fpt.egov.wservices.framework.impl;

import com.fpt.egov.wservices.framework.ws.HealtcheckWS;

import javax.jws.WebService;

/**
 * Created by tungnq on 7/29/2014.
 */
@WebService(endpointInterface="com.fpt.egov.wservices.framework.ws.HealtcheckWS")
public class HealtcheckWSImpl implements HealtcheckWS {

    @Override
    public String healtcheck() {
        return "OK";
    }
}
