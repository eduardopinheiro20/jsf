
package org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10-b140803.1500
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "Operacionais", targetNamespace = "http://tempuri.org/", wsdlLocation = "https://www.sil.sp.gov.br/Webservice/Operacionais.svc/basic?wsdl")
public class Operacionais
    extends Service
{

    private final static URL OPERACIONAIS_WSDL_LOCATION;
    private final static WebServiceException OPERACIONAIS_EXCEPTION;
    private final static QName OPERACIONAIS_QNAME = new QName("http://tempuri.org/", "Operacionais");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://www.sil.sp.gov.br/Webservice/Operacionais.svc/basic?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        OPERACIONAIS_WSDL_LOCATION = url;
        OPERACIONAIS_EXCEPTION = e;
    }

    public Operacionais() {
        super(__getWsdlLocation(), OPERACIONAIS_QNAME);
    }

    public Operacionais(WebServiceFeature... features) {
        super(__getWsdlLocation(), OPERACIONAIS_QNAME, features);
    }

    public Operacionais(URL wsdlLocation) {
        super(wsdlLocation, OPERACIONAIS_QNAME);
    }

    public Operacionais(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, OPERACIONAIS_QNAME, features);
    }

    public Operacionais(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Operacionais(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IOperacionais
     */
    @WebEndpoint(name = "BasicHttpBinding_IOperacionais")
    public IOperacionais getBasicHttpBindingIOperacionais() {
        return super.getPort(new QName("http://tempuri.org/", "BasicHttpBinding_IOperacionais"), IOperacionais.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IOperacionais
     */
    @WebEndpoint(name = "BasicHttpBinding_IOperacionais")
    public IOperacionais getBasicHttpBindingIOperacionais(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "BasicHttpBinding_IOperacionais"), IOperacionais.class, features);
    }

    private static URL __getWsdlLocation() {
        if (OPERACIONAIS_EXCEPTION!= null) {
            throw OPERACIONAIS_EXCEPTION;
        }
        return OPERACIONAIS_WSDL_LOCATION;
    }

}