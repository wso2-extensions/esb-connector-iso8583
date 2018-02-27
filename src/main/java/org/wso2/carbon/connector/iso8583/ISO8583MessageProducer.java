/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.connector.iso8583;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.synapse.MessageContext;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

import javax.xml.namespace.QName;
import java.util.Base64;
import java.util.Iterator;

/**
 * Class for get host, port and iso fields from messageContext
 * @since 1.0.2
 */
public class ISO8583MessageProducer extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            String host = (String) messageContext.getProperty(ISO8583Constant.HOST);
            int port = Integer.parseInt((String) messageContext.getProperty(ISO8583Constant.PORT));
            packedISO8583Message(messageContext, host, port);
        } catch (NumberFormatException e) {
            handleException("The port number does not contain a parsable integer", e, messageContext);
        }
    }

    /**
     * packed the fields to get String of isoMessage
     * @param host  the localhost
     * @param port  5010
     * @param msgContext the message context
     */
    public void packedISO8583Message(MessageContext msgContext, String host, int port) {
        try {
            SOAPEnvelope soapEnvelope = msgContext.getEnvelope();
            OMElement getElements = soapEnvelope.getBody().getFirstElement();
            ISOMsg isoMsg = new ISOMsg();
            int headerLength = 0;
            OMElement header = getElements.getFirstChildWithName(new QName(ISO8583Constant.HEADER));
            if (header != null) {
                isoMsg.setHeader(Base64.getDecoder().decode(header.getText()));
                headerLength = (Base64.getDecoder().decode(header.getText())).length;
            }
            isoMsg.setPackager(ISO8583PackagerFactory.getPackager(headerLength));
            Iterator fields = getElements.getFirstChildWithName(
                    new QName(ISO8583Constant.TAG_DATA)).getChildrenWithLocalName(ISO8583Constant.TAG_FIELD);
            while (fields.hasNext()) {
                OMElement element = (OMElement) fields.next();
                String getValue = element.getText();
                try {
                    int fieldID = Integer.parseInt(element.getAttribute(
                            new QName(ISO8583Constant.TAG_ID)).getAttributeValue());
                    isoMsg.set(fieldID, getValue);
                } catch (NumberFormatException e) {
                    log.error("The fieldID does not contain a parsable integer" + e.getMessage(), e);
                }
            }
            byte[] data = isoMsg.pack();
            new ISO8583MessageHandler(msgContext, data, host, port);
        } catch (ISOException e) {
            handleException("Couldn't packed iso8583 Messages", e, msgContext);
        }
    }   
}