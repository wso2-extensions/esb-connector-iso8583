package org.wso2.carbon.connector.integration.test.iso8583;

/*
 *  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.HashMap;
import java.util.Map;

import org.apache.axiom.om.OMElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

public class ISO8583ConnectorIntegrationTest extends ConnectorIntegrationTestBase {

    private Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();

    /**
     * Set up the environment.
     */
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        String connectorName = System.getProperty("connector_name") + "-connector-" +
                System.getProperty("connector_version") + ".zip";
        init(connectorName);
        getApiConfigProperties();
        esbRequestHeadersMap.put("Content-Type", "application/xml");

    }

    @Test(groups = {"wso2.esb"}, description = "ISO8583 connector send message integration test")
    public void testSendMessageMandatory() throws Exception {
        String methodName = "iso8583";
        String esbEndPoint = getProxyServiceURLHttp(methodName) + "?serverHost=" + connectorProperties.
                getProperty("serverHost") + "&serverPort=" + connectorProperties.getProperty("serverPort");

        RestResponse<OMElement> esbRestResponse =
                sendXmlRestRequest(esbEndPoint, "POST", esbRequestHeadersMap, "produceMessageMandatory.xml");
        System.out.println(" --- Success Response -- " + esbRestResponse.getBody());
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
    }

    @Test(groups = {"wso2.esb"}, description = "ISO8583 connector send message integration test " +
            "with negative parameters")
    public void testSendMessageNegative() throws Exception {
        String methodName = "iso8583";
        String esbEndPoint = getProxyServiceURLHttp(methodName) + "?serverHost=" + connectorProperties.
                getProperty("serverHost") + "&serverPort=" + connectorProperties.getProperty("serverPort");

        RestResponse<OMElement> esbRestResponse =
                sendXmlRestRequest(esbEndPoint, "POST", esbRequestHeadersMap, "produceMessageNegative.xml");
        System.out.println(" --- Failed Response -- " + esbRestResponse.getBody());
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 202);
    }
}
