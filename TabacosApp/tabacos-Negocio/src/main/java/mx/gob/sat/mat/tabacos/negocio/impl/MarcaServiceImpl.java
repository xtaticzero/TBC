/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.constants.enums.TipoAcuse;
import mx.gob.sat.mat.tabacos.modelo.dao.MarcaDao;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudAltaMarca;
import mx.gob.sat.mat.tabacos.modelo.exceptions.MarcaDaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PersistenceException;
import mx.gob.sat.mat.tabacos.negocio.CommonService;
import mx.gob.sat.mat.tabacos.negocio.MarcaService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CommonServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.MarcaServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;
import mx.gob.sat.mat.tabacos.negocio.util.FileUtil;
import mx.gob.sat.mat.tabacos.util.FechaUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MTI Agustin Romero Mata
 */
@Service
@Qualifier("marcaService")
public class MarcaServiceImpl implements MarcaService {

    private static final Logger LOGGER = Logger.getLogger(
            MarcaServiceImpl.class);

    @Autowired
    @Qualifier("marcaDaoImpl")
    private MarcaDao marcaDaoImpl;

    @Value("#{tbc_prop_ambiente['base.path.file']}")
    private String directorioBase;
    
    @Value("#{tbc_prop_ambiente['ruta.archivo.alta.marca']}")
    private String dirDoctosSolicitudAlta;

    @Autowired
    @Qualifier("fileUtil")
    private FileUtil fileUtil;

    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;
    
    @Autowired
    private ReporterService reporterService;
    
    private static final String CADENA_ORIGINAL_SEPARADOR = "|";

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Marcas insertaMarca(Marcas info, InputStream contenidoArchivo, String nombreArchivo) throws MarcaServiceException{
        Marcas marca = null;
        try { 
            String rutaArchivo = fileUtil.getRutaArchivoValida(directorioBase, info.getRfc(),dirDoctosSolicitudAlta,info.getClave());
            
            info.setRutaArchivo(rutaArchivo+nombreArchivo);
            
            marca = marcaDaoImpl.insertaMarca(info);
            
            if (!fileUtil.cearArchivo(contenidoArchivo, rutaArchivo, nombreArchivo)) {
                throw new ServiceException("No se pudo cargar el documento legal");
            } 
        } catch (PersistenceException e) {
            LOGGER.error("Error al insertar los datos de la marca"
                    + e.getMessage(), e);
            throw new PersistenceException(e);
        } catch (ServiceException s) {
            LOGGER.error(
                    "Error al insertar los datos de la marca - Representante"
                    + s.getMessage(), s);
            throw new MarcaServiceException(s);
        }
        return marca;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Marcas modificaMarca(Marcas info, String claveBusq) throws MarcaServiceException{
        Marcas marca = null;
        try {
            marca = marcaDaoImpl.modificaMarca(info, claveBusq);
        } catch (PersistenceException e) {
            LOGGER.error("Error al modificar los datos de la marca"
                    + e.getMessage(), e);
            throw new PersistenceException(e);
        } catch (SecurityException s) {
            LOGGER.error(""
                    + "Error al modificar los datos de la marca - Representante"
                    + s.getMessage(), s);
            throw new MarcaServiceException(s);
        }
        return marca;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int borraMarca(Marcas info) throws MarcaServiceException{
        int retorno = 0;
        try {
            retorno = marcaDaoImpl.borraMarca(info);
        } catch (PersistenceException e) {
            LOGGER.error("Error al borrar los datos de la marca"
                    + e.getMessage(), e);
            throw new PersistenceException(e);
        } catch (SecurityException s) {
            LOGGER.error(
                    "Error al borrar los datos de la marca - Representante"
                    + s.getMessage(), s);
            throw new MarcaServiceException(s);
        } catch (MarcaDaoException ex){
            LOGGER.error(
                    "Error al borrar los datos de la marca - Representante", ex);
            throw new MarcaServiceException(ex);
        }
        return retorno;
    }

    @Override
    public List<SelectItem> consultaMarcas() throws MarcaServiceException{
        List<SelectItem> retorno = new ArrayList<SelectItem>();
        List<Marcas> temp;

        try {
            temp = marcaDaoImpl.consultaMarcas();

            for (Marcas item : temp) {
                SelectItem info = new SelectItem(
                        item.getClave(), item.getMarca());

                retorno.add(info);
            }

        } catch (PersistenceException e) {
            LOGGER.error("Error al obtener los datos de la marca"
                    + e.getMessage(), e);
            throw new PersistenceException(e);
        } catch (SecurityException s) {
            LOGGER.error(
                    "Error al obtener los datos de la marca - Representante"
                    + s.getMessage(), s);
            throw new MarcaServiceException(s);
        }
        return retorno;
    }
    
    @Override
    public List<SelectItem> consultaTabacaleras() {
        return marcaDaoImpl.getCombos(marcaDaoImpl.SQL_SELECT_TABACALERASCB, 
                "IDTABACALERA", "RFC");
    }

    @Override
    public boolean guardaArchivoMarca(InputStream contenidoArchivo, String rutaDirectorio, String nombreArchivo) {

        if (contenidoArchivo != null && rutaDirectorio != null && nombreArchivo != null) {
            return fileUtil.cearArchivo(contenidoArchivo, rutaDirectorio, nombreArchivo);
        }

        return false;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public SolicitudAltaMarca generaSolicitudAltaMarca(Marcas marcaSolicitada, InputStream contenidoArchivo, String nombreArchivo, String cadenaOriginal, String selloDigital) throws MarcaServiceException {

        SolicitudAltaMarca solicitudAltaMarca;
        String rutaArchivo;
        Acuse acuse;
        
        
        try {
            solicitudAltaMarca = new SolicitudAltaMarca();
            
            try {
                if(marcaDaoImpl.numeroClaves(marcaSolicitada.getClave())!=0){
                    throw new MarcaServiceException("Se intenta insertar una marca con clave no valida ...");
                }
            } catch (MarcaDaoException ex) {
                throw new MarcaServiceException("Se intenta insertar una marca con clave no valida ...",ex);
            }
            
            solicitudAltaMarca.setMarcas(marcaDaoImpl.insertaMarca(marcaSolicitada));
            acuse = new Acuse();
            acuse.setSerieAcuse(TipoAcuse.SOLICITUD_ALTA_MARCA);
            acuse.setSelloDigital(selloDigital);
            acuse.setCadenaOriginal(cadenaOriginal);
            acuse.setFecCaptura(new Date());
            acuse.setIdMarca(solicitudAltaMarca.getMarcas().getIdMarca());
            String folioAcuse = null;
            try{
                folioAcuse = commonService.crearAcuse(acuse);
            }catch(CommonServiceException e){
                throw new MarcaServiceException(e);
            }
            solicitudAltaMarca.setIdAcuseRecibo(folioAcuse);
            rutaArchivo = fileUtil.getRutaArchivoValida(directorioBase, marcaSolicitada.getRfc(),dirDoctosSolicitudAlta,marcaSolicitada.getClave());
            
            if (!fileUtil.cearArchivo(contenidoArchivo, rutaArchivo, nombreArchivo)) {
                throw new MarcaServiceException("No se pudo cargar el documento legal");
            } 

            return solicitudAltaMarca;
        } catch (PersistenceException e) {
            LOGGER.error("Error al insertar los datos de la marca"
                    + e.getMessage(), e);
            throw new MarcaServiceException(e);
        } 
    }
    
    @Override
    public String generaCadenaOriginal(String rfc,String marca,String clave){
        String cadenaOriginal = "";
        
        if(rfc!=null&&marca!=null&&clave!=null){
            cadenaOriginal=cadenaOriginal.concat(CADENA_ORIGINAL_SEPARADOR);
            cadenaOriginal=cadenaOriginal.concat(rfc).concat(CADENA_ORIGINAL_SEPARADOR);
            cadenaOriginal=cadenaOriginal.concat(marca).concat(CADENA_ORIGINAL_SEPARADOR);
            cadenaOriginal=cadenaOriginal.concat(clave).concat(CADENA_ORIGINAL_SEPARADOR);
            cadenaOriginal=cadenaOriginal.concat(FechaUtil.formatFecha(new Date(), FechaUtil.FORMATO_CADENA_ORIGINAL)).concat(CADENA_ORIGINAL_SEPARADOR);
        }
        
        return cadenaOriginal;
    }
    
    @Override
    public byte[] generarAcuse(Marcas info) {        
        try {
            Map parameteros = new HashMap();
            
            parameteros.put("rfc", info.getRfc());
            parameteros.put("clave", info.getClave());
            parameteros.put("marca", info.getMarca());
            parameteros.put("fecha", info.getFechaAcuse());
            
            return reporterService.makeReport(ReportesTabacosEnum.
                    REPORT_ACUSE_AMARCA, ARCHIVO_PDF, parameteros, null);
        } catch (ReporterJasperException ex) {
            LOGGER.error(ex);
            return null;
        }
    }

    @Override
    public String getRutaDocumentacion(String rfcTabacalera, String claveMarca)  {
        return fileUtil.getRutaArchivoValida(directorioBase,rfcTabacalera,dirDoctosSolicitudAlta,claveMarca);
    }

    @Override
    public String getDirectorioBase() {
        return directorioBase;
    }

    public void setDirectorioBase(String directorioBase) {
        this.directorioBase = directorioBase;
    }

    @Override
    public FileUtil getFileUtil() {
        return fileUtil;
    }

    @Override
    public String getDirDoctosSolicitudAlta() {
        return dirDoctosSolicitudAlta;
    }

    public void setDirDoctosSolicitudAlta(String dirDoctosSolicitudAlta) {
        this.dirDoctosSolicitudAlta = dirDoctosSolicitudAlta;
    }

    @Override
    public boolean existeMarca(Marcas info) {
        return marcaDaoImpl.consultaMarcas(info);
    }

    @Override
    public String obtieneMarca(String idMarca) {
        return marcaDaoImpl.obtieneMarca(idMarca);
    }

    @Override
    public Marcas buscaMarca(String nombre) throws MarcaServiceException {
        try {
          return marcaDaoImpl.buscaMarca(nombre);
        }catch (MarcaDaoException ex) {
            throw new MarcaServiceException(ex);
        }
    }

    @Override
    public boolean isClaveRepetida(String clave) throws MarcaServiceException {
        try {
            return !((marcaDaoImpl.buscaMarcaXAtributo(null,null,clave)).isEmpty());
        }catch (MarcaDaoException ex) {
            throw new MarcaServiceException(ex);
        }
    }

    @Override
    public boolean isNombreRepetido(String nomMarca) throws MarcaServiceException {
        try {
            return !((marcaDaoImpl.buscaMarcaXAtributo(null,nomMarca,null)).isEmpty());
        }catch (MarcaDaoException ex) {
            throw new MarcaServiceException(ex);
        }
    }
    
    @Override
    public List<Marcas> getMarcasByRFCTabacalera(String rfcTabacalera) throws MarcaServiceException {
        try {
            return marcaDaoImpl.getMarcasByRFCTabacalera(rfcTabacalera);
        } catch (MarcaDaoException e) {
            LOGGER.error(e);
            throw new MarcaServiceException(e);
        }
    }

    @Override
    public List<Marcas> getMarcas() throws MarcaServiceException {
        try {
            return marcaDaoImpl.getMarcas();
        } catch (MarcaDaoException e) {
            LOGGER.error(e);
            throw new MarcaServiceException(e);
        }
    }
    
    /**
     *
     * @param idTabacalera
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.MarcaServiceException
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Marcas> selectedMarcas(Long idTabacalera) throws MarcaServiceException {
        try {
            return marcaDaoImpl.selectedMarcas(idTabacalera);
        } catch (MarcaDaoException e) {
            LOGGER.error("ERROR - Al consultar las Marcas" + e.getMessage(), e);
            throw new MarcaServiceException(e);
        }
    }
}
