/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.ws.impl;

import com.sun.org.apache.xerces.internal.dom.DOMInputImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import org.apache.log4j.Logger;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

/**
 *
 * @author root
 */
public class PacHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOGGER = Logger.getLogger(PacHandler.class);

    private static final String VERSION_TAG = "WS-Version";
    private static final String VERSION_RELEASE = "0.08";
    private static final String RELEASE_TAG = "Release Date";
    private static final String RELEASE_DATE = "2015-07-14";

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        SOAPMessage soapMsg = context.getMessage();
        try {
            if (soapMsg != null) {
                SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                SOAPHeader soapHeader = soapEnv.getHeader();
                SOAPBody soapBody = soapEnv.getBody();
                Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
                if (!outboundProperty) {
                    if (soapHeader == null) {
                        soapEnv.addHeader();
                        generateSOAPErrMessage(soapMsg, "No SOAP header.");
                    }
                    if (soapBody == null) {
                        soapBody = soapEnv.addBody();
                        generateSOAPErrMessage(soapMsg, "No SOAP body.");
                    }
                    SOAPBodyElement nodoOp = this.searchNode(soapBody.getChildElements());
                    if (nodoOp == null) {
                        generateSOAPErrMessage(soapMsg, "No SOAP Operation.");
                    } else {
                        SOAPBodyElement nodoParam = this.searchNode(nodoOp.getChildElements());
                        if (nodoParam != null) {
                            validarPeticion(nodoParam);
                        } else {
                            generateSOAPErrMessage(soapMsg, "Not found parameter request");
                        }
                    }
                } else {
                    Map<String, List<String>> headers = (Map<String, List<String>>) context.get(MessageContext.HTTP_RESPONSE_HEADERS);
                    if (headers != null) {
                        headers.put(VERSION_TAG, this.getValuesHeader());
                    } else {
                        headers = new HashMap<String, List<String>>();
                        headers.put(VERSION_TAG, this.getValuesHeader());
                        context.put(MessageContext.HTTP_RESPONSE_HEADERS, headers);
                    }
                }
            }
        } catch (SOAPException e) {
            LOGGER.error(e);
            generateSOAPErrMessage(soapMsg, e.getMessage());
        } catch (SAXException e) {
            LOGGER.error(e);
            generateSOAPErrMessage(soapMsg, e.getMessage());
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    private void validarPeticion(SOAPBodyElement nodoParam) throws SAXException, SOAPException {
        try {
            if (nodoParam != null) {
                final String pathXsd = "../xsd/";
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                sf.setResourceResolver(new LSResourceResolver() {

                    @Override
                    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
                        try {
                            InputStream xsdCat = getClass().getResourceAsStream(pathXsd + systemId);
                            return new DOMInputImpl(publicId, systemId, baseURI, xsdCat, "utf-8");
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });

                InputStream xsdPojo = getClass().getResourceAsStream(pathXsd + nodoParam.getLocalName() + ".xsd");
                if (xsdPojo != null) {
                    Schema schema = sf.newSchema(new StreamSource(xsdPojo));
                    Validator validator = schema.newValidator();
                    validator.validate(new DOMSource(nodoParam));
                } else {
                    throw new SOAPException("PARAMETER NAME INVALID");
                }
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private SOAPBodyElement searchNode(Iterator ite) {
        try {
            if (ite != null) {
                while (ite.hasNext()) {
                    Object obj = ite.next();
                    if (obj instanceof SOAPBodyElement) {
                        return (SOAPBodyElement) obj;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    private void generateSOAPErrMessage(SOAPMessage msg, String reason) {
        try {
            SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
            SOAPFault soapFault = soapBody.addFault();
            soapFault.setFaultString(reason);
            throw new SOAPFaultException(soapFault);
        } catch (SOAPException e) {
            LOGGER.error(e);
        }
    }

    private List<String> getValuesHeader() {
        List<String> values = new ArrayList<String>();
        values.add(VERSION_RELEASE.concat("; ").concat(RELEASE_TAG).concat("=").concat(RELEASE_DATE));

        return values;
    }
}
