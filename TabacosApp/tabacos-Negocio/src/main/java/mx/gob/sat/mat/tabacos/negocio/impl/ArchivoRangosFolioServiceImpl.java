/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import mx.gob.sat.mat.tabacos.constants.Constantes;
import mx.gob.sat.mat.tabacos.constants.enums.EstadoEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.negocio.ArchivoRangosFolioService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.BusinessException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangosException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
@Service
@Qualifier("archivoFoliosServiceImpl")
public class ArchivoRangosFolioServiceImpl implements ArchivoRangosFolioService {
    protected static final Logger LOGGER = Logger.getLogger(ArchivoRangosFolioServiceImpl.class);
    
    private static final int NUMERO_CAMPOS_ARCHIVO_FOLIOS_CANCELADOS = 2;
    private static final String ERROR_COLUMNA = "Error las columnas solo aceptan "
            + "formato númerico en un rango entre ";

    @Override
    public List<RangoFolio> leerArchivoFolios(InputStream inputStream,
            String nombreArchivo) throws RangosException {
        FileInputStream fileInputStream = null;
        List<RangoFolio> validarListExcel = null;
        OutputStream salida = null;
        File f = null;

        try {

            f = new File(nombreArchivo);
            salida = new FileOutputStream(f);
            int len;
            final int numByte = 1024;
            byte[] buf = new byte[numByte];

            while ((len = inputStream.read(buf)) > 0) {
                salida.write(buf, 0, len);
            }
            salida.flush();
            fileInputStream = new FileInputStream(f);
            validarListExcel = esExtensionValida(nombreArchivo, fileInputStream);
            return validarListExcel;

        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new RangosException("Error archivo no encontrado, " + e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RangosException("Error al leer el archivo" + e.getMessage(), e);
        } catch (BusinessException ex) {
            throw new RangosException("Error al leer el archivo" + ex.getMessage(), ex);
        } finally {
            try {
                cierraArchivoOutputStream(salida);
                cierraArchivoInputStream(fileInputStream);
                
                if (f != null) {
                    boolean r = f.delete();
                    LOGGER.info("Se cerro correctamente archivo de leerArchivoFolios."+r);
                }
            } catch (IOException ex) {
                LOGGER.error( ex);
            }
        }
    }
    
    @Override
    public List<List<RangoFolio>> rangosFolioSeparadosPorSolicitud (List<RangoFolio> foliosTotales) {
        List<List<RangoFolio>> conjunto = null;
        List<RangoFolio> foliosSolicitudesDistintas = recuperaSolicitudesDistintas(foliosTotales);
        List<RangoFolio> listaTemporal = null;
        boolean existeSolicitud = true;
        RangoFolio folioTemp = null;
        
        if (foliosTotales != null && !foliosTotales.isEmpty() && foliosSolicitudesDistintas != null) {
            conjunto = new ArrayList<List<RangoFolio>>();
            while (existeSolicitud) {

                folioTemp = foliosSolicitudesDistintas.remove(0);

                listaTemporal = separaRangosPorSolicitud(foliosTotales, folioTemp);
                if (listaTemporal != null) {
                    conjunto.add(listaTemporal);
                }
                if (foliosSolicitudesDistintas.size() <= 0 || foliosSolicitudesDistintas.isEmpty()) {
                    existeSolicitud = false;
                }
            }
        }
        return conjunto;
    }
    
    /**
     * Recupera los RangoFolio con id de solicitudes distintas, dado que se puede tener rangos
     * pertenecientes a diferentes solicitudes.
     */
    private List<RangoFolio> recuperaSolicitudesDistintas (List<RangoFolio> foliosTotales) {
        List<RangoFolio> listaFolios = null;
        if (foliosTotales != null) {
            listaFolios = new ArrayList<RangoFolio>();
            for (RangoFolio folio : foliosTotales) {
                if (listaFolios.isEmpty()) {
                    listaFolios.add(folio);
                } else {
                    //si no existe en la lista, agregar el rango que lleva el idSolicitud
                    if (!contiene(listaFolios, folio)) {
                        listaFolios.add(folio);
                    }
                }
            }
        }
        return listaFolios;
    }
    
    private List<RangoFolio> separaRangosPorSolicitud(List<RangoFolio> foliosTotales, RangoFolio rango) {
        List<RangoFolio> rangosSep = new ArrayList<RangoFolio>();
        boolean estaContenido = false;
        if (foliosTotales != null && rango != null && rango.getIdSolicitud() != null ) {
            for (int i = 0; i < foliosTotales.size(); i++) {
                if ( foliosTotales.get(i).getIdSolicitud() != null &&
                        rango.getIdSolicitud().equals(foliosTotales.get(i).getIdSolicitud())) {
                    estaContenido = contiene(foliosTotales, foliosTotales.get(i));
                    
                    if (rangosSep.isEmpty() || !estaContenido) {
                        rangosSep.add(foliosTotales.get(i));
                    }
                }
            }
        }
        return rangosSep;
    }
    
    @Override
    public List<RangoFolio> separaRangosPorSolicitud(List<RangoFolio> foliosTotales) {
        List<RangoFolio> rangosSep = new ArrayList<RangoFolio>();
        boolean estaContenido = false;

        for (int i = 0; i < foliosTotales.size(); i++) {
            estaContenido = contiene(rangosSep, foliosTotales.get(i));

            if (!estaContenido) {
                rangosSep.add(foliosTotales.get(i));
            }
        }

        return rangosSep;
    }

    private boolean contiene(List<RangoFolio> rangos, RangoFolio r) {
        for (RangoFolio rango : rangos) {
            if (rango.getIdSolicitud().equals(r.getIdSolicitud())) {
                return true;
            }
        }
        return false;
    }
    
    private void cierraArchivoInputStream (FileInputStream fileInputStream) throws IOException{
        if (fileInputStream != null) {
            fileInputStream.close();
        }
    }
    
    private void cierraArchivoOutputStream(OutputStream salida) throws IOException {
        if (salida != null) {
            salida.close();
        }
    }

    private List<RangoFolio> validarExcelXLS(final FileInputStream fileInputStream) throws BusinessException{
        List<RangoFolio> amtRangoRangoFoliosCanceladosList = new ArrayList<RangoFolio>();
        RangoFolio amtRangoRangoFolios = null;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            int contadorFila = 0;
            String erroresArchivo = "";
            boolean errorArchivo = Boolean.FALSE;

            for (int i = 0; i < 1 && rows.hasNext(); i++) {
                rows.next();
            }

            while (rows.hasNext()) {
                contadorFila++;
                HSSFRow row = ((HSSFRow) rows.next());

                try {
                    amtRangoRangoFolios = validarRegistro(row, contadorFila);

                } catch (BusinessException serEx) {
                    errorArchivo = Boolean.TRUE;
                    erroresArchivo = erroresArchivo.concat(serEx.getMessage());
                    break;
                }

                if (!errorArchivo && amtRangoRangoFolios != null) {
                    amtRangoRangoFoliosCanceladosList.add(amtRangoRangoFolios);
                }
            }
            if (errorArchivo) {
                throw new BusinessException(erroresArchivo);
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error al leer el archivo, " + e.getMessage(), e);
        }

        Collections.sort(amtRangoRangoFoliosCanceladosList);

        return amtRangoRangoFoliosCanceladosList;
    }

    private List<RangoFolio> validarExcelXLSX(final FileInputStream fileInputStream) throws BusinessException{

        List<RangoFolio> amtRangoRangoFoliosCanceladosList = new ArrayList<RangoFolio>();
        RangoFolio amtRangoRangoFolios = null;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            int contadorFila = 1;
            String erroresArchivo = "";
            boolean errorArchivo = Boolean.FALSE;

            for (int i = 0; i < 1 && rows.hasNext(); i++) {
                rows.next();
            }

            while (rows.hasNext()) {
                contadorFila++;
                XSSFRow row = ((XSSFRow) rows.next());

                try {
                    amtRangoRangoFolios = validarRegistro(row, contadorFila);

                } catch (BusinessException e) {
                    errorArchivo = Boolean.TRUE;
                    erroresArchivo = erroresArchivo.concat(e.getMessage());
                    erroresArchivo = erroresArchivo.concat(Constantes.SALTO_LINEA);
                    break;
                }

                if (!errorArchivo && amtRangoRangoFolios != null) {
                    amtRangoRangoFoliosCanceladosList.add(amtRangoRangoFolios);
                }
            }
            if (errorArchivo) {
                throw new BusinessException(erroresArchivo);
            }

        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error archivo no encontrado, " + e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error al leer el archivo, " + e.getMessage(), e);
        }
        Collections.sort(amtRangoRangoFoliosCanceladosList);
        return amtRangoRangoFoliosCanceladosList;
    }


    private RangoFolio validarRegistro(final Row row, final int contadorFila) throws BusinessException {
        Boolean errorArchivo = Boolean.FALSE;
        String erroresRegistro = "";
        RangoFolio rangoFolio = new RangoFolio();
        rangoFolio.setEstado(EstadoEnum.CANCELADO.getId());

        loop: for (int i = 0; i < NUMERO_CAMPOS_ARCHIVO_FOLIOS_CANCELADOS; i++) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                switch (i) {
                    case 0:
                        if (!this.validarRangoFolioInicial(cell)) {
                            errorArchivo = Boolean.TRUE;
                            erroresRegistro = erroresRegistro.concat(agregarMensajeErrorRangoFolioInicial());
                            break loop;
                        } else {
                            rangoFolio.setFolioInicial((long) cell.getNumericCellValue());
                        }
                        break;

                    case 1:
                        if (!this.validarRangoFolioFinal(rangoFolio.getFolioInicial(), cell)) {
                            errorArchivo = Boolean.TRUE;
                            erroresRegistro = erroresRegistro.concat(agregarMensajeErrorRangoFolioFinal());
                            break loop;
                        } else {
                            rangoFolio.setFolioFinal((long) cell.getNumericCellValue());
                        }
                        break;

                    default:
                        errorArchivo = Boolean.TRUE;
                        erroresRegistro = erroresRegistro.concat(this.agregarMensajeErrorColumnaNoValida(contadorFila, i));
                        break;
                }

            } else {
                errorArchivo = Boolean.TRUE;
                erroresRegistro += this.agregarMensajeErrorCeldaObligatoria(contadorFila);
            }
        }

        if (errorArchivo) {
            throw new BusinessException(erroresRegistro);
        }
        return rangoFolio;
    }

    private boolean validarRangoFolioInicial(final Cell cellRangoFolioInicial) {
        boolean resultado = Boolean.TRUE;
        if (cellRangoFolioInicial.getCellType() != XSSFCell.CELL_TYPE_NUMERIC) {
            resultado = Boolean.FALSE;
        } else {
            long folio = (long) cellRangoFolioInicial.getNumericCellValue();
            resultado = folio >= Constantes.FOLIO_MINIMO && folio <= Constantes.FOLIO_MAXIMO;
        }
        return resultado;
    }

    private boolean validarRangoFolioFinal(final long folioInicial, final Cell cellRangoFolioFinal) {
        boolean resultado = Boolean.TRUE;
        if (cellRangoFolioFinal.getCellType() != XSSFCell.CELL_TYPE_NUMERIC) {
            resultado = Boolean.FALSE;
        } else {
            long folioFinal = (long) cellRangoFolioFinal.getNumericCellValue();
            resultado = (folioFinal >= Constantes.FOLIO_MINIMO && folioFinal <= Constantes.FOLIO_MAXIMO);
            if (resultado) {
                resultado = folioFinal >= folioInicial;
            }
        }
        return resultado;
    }
    private List<RangoFolio> esExtensionValida(String nombreArchivo, FileInputStream fileInputStream) throws BusinessException{
        List<RangoFolio> validarListExcel;
        
        if (nombreArchivo.endsWith(Constantes.EXCEL_ANTES_2007)) {
            validarListExcel = validarExcelXLS(fileInputStream);
        } else if (nombreArchivo.endsWith(Constantes.EXCEL_DESPUES_2007)) {
            validarListExcel = validarExcelXLSX(fileInputStream);
        } else {
            throw new BusinessException("Error: Formato de archivo no soportado");
        }
        return validarListExcel;
    }

    private String agregarMensajeErrorRangoFolioInicial() {
        String msjError = "";
        msjError = msjError.concat(ERROR_COLUMNA);
        msjError = msjError.concat(""+Constantes.FOLIO_MINIMO);
        msjError = msjError.concat(" y ");
        msjError = msjError.concat(""+Constantes.FOLIO_MAXIMO);
        msjError = msjError.concat(Constantes.SALTO_LINEA);
        return msjError;
    }

    private String agregarMensajeErrorRangoFolioFinal() {
        String msjError = "";
        msjError = msjError.concat(ERROR_COLUMNA);
        msjError = msjError.concat(""+Constantes.FOLIO_MINIMO);
        msjError = msjError.concat(" y ");
        msjError = msjError.concat(""+Constantes.FOLIO_MAXIMO);
        msjError = msjError.concat(" y el folio f)inal debe ser mayor al inicial");
        msjError = msjError.concat(Constantes.SALTO_LINEA);
        return msjError;
    }

    private String agregarMensajeErrorCeldaObligatoria(final int fila) {
        String msjError = "";
        msjError = msjError.concat("Error en la fila [");
        msjError = msjError.concat(""+fila);
        msjError = msjError.concat("] todos los campos son obligatorios");
        msjError = msjError.concat(Constantes.SALTO_LINEA);
        return msjError;
    }

    private String agregarMensajeErrorColumnaNoValida(final int fila, final int columna) {
        String msjError = "";
        msjError = msjError.concat("Error en la fila [");
        msjError = msjError.concat(""+fila);
        msjError = msjError.concat("] la columna ");
        msjError = msjError.concat(""+columna);
        msjError = msjError.concat(" no es válida");
        msjError = msjError.concat(Constantes.SALTO_LINEA);
        return msjError;
    }

}
