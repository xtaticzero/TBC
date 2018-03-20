package mx.gob.sat.mat.tabacos.negocio.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.SelloDigitalException;
import mx.gob.sat.sgi.ClienteSelladora;
import mx.gob.sat.sgi.SelladoraException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Daniel
 */
@Component("selloDigitalUtil")
public class SelloDigitalUtil {

    public static final Logger LOGGER = Logger.getLogger(SelloDigitalUtil.class);

    @Autowired
    @Qualifier("selloDigitalConf")
    private Properties selloDigitalConf;

    public String generaSelloDigital(String cadenaOriginal) throws SelloDigitalException{
        String selloDigital = "";

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(getSelloDigitalConf().getProperty("sello.digital.ruta.parametros")));
            doc.getDocumentElement().normalize();
            NodeList listaPersonas = doc.getElementsByTagName("parametro");
            Node persona = listaPersonas.item(0);
            if (persona.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) persona;
                String ip = obtenerValorTag("ip", elemento);
                String puerto = obtenerValorTag("puerto", elemento);
                selloDigital = this.generarSello(ip, puerto, cadenaOriginal);
            }

        } catch (ParserConfigurationException ex) {
            throw new SelloDigitalException("Hubo un error configurar el archivo de parametros de la selladora", ex);
        } catch (SAXException ex) {
            throw new SelloDigitalException("Hubo un error alparsear el archivo de parametros de la selladora", ex);
        } catch (IOException ex) {
            throw new SelloDigitalException("Hubo un error al obtener el archivo de parametros de la selladora", ex);
        } catch (SelladoraException ex) {
            LOGGER.error("Hubo un error en el servicio de la selladora no se genero sello", ex);
        }

        return selloDigital;
    }

    private String obtenerValorTag(String tag, Element valor) {
        NodeList nlLista = valor.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValor = (Node) nlLista.item(0);
        return nValor.getNodeValue();
    }

    private String generarSello(String ip, String puerto, String cadenaOriginal) throws SelladoraException {
        String sSello = "";

        int puertoPrueba = Integer.parseInt(puerto);
        ClienteSelladora sello = new ClienteSelladora(ip, puertoPrueba);
        if (cadenaOriginal == null) {
            LOGGER.error("la cadena original es nulla");
        } else {
            try {
                byte[] byteStr = cadenaOriginal.getBytes("UTF-8");
                if (sello.conectaSelladora()) {
                    sSello = sello.solSello(byteStr, true);
                    sello.desconectaSelladora();
                }
            } catch (UnsupportedEncodingException ex) {
                LOGGER.error("la cadena original no esta en formato UTF-8",ex);
            }
        }
        return sSello;
    }

    public Properties getSelloDigitalConf() {
        return selloDigitalConf;
    }

    public void setSelloDigitalConf(Properties selloDigitalConf) {
        this.selloDigitalConf = selloDigitalConf;
    }

}
