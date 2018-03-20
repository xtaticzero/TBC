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
import mx.gob.sat.mat.tabacos.constants.Constantes;
import mx.gob.sat.mat.tabacos.constants.enums.EstadoEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.negocio.ArchivoFoliosProduccionService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ArchivoFoliosServiceException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
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
@Qualifier("archivoFoliosProdServiceImpl")
public class ArchivoFoliosProduccionServiceImpl implements ArchivoFoliosProduccionService {

    protected static final Logger LOGGER = Logger.getLogger(ArchivoFoliosProduccionServiceImpl.class);
    public static final int NUMERO_CAMPOS_ARCHIVO_FOLIOS_CANCELADOS = 2;
    private static final String ERROR_FILA = "Error en la fila [";
    private static final String ERROR_COLUMNA = "]  la columna solo acepta formato numerico en un rango entre ";
    private static final String AND = " y ";
    private static final String ERROR_FOLIO_FINAL = " y el folio final debe ser mayor al inicial";
    private static final String ERRORFILA = "] la columna ";
    private static final String ERROR_OBLIGATORIO = "] todos los campos son obligatorios";
    private static final String ERROR_VALIDA = " no es valida";

    /**
     *
     * @param inputStream
     * @param nombreArchivo
     * @return
     * @throws ArchivoFoliosServiceException
     */
    @Override
    public List<RangoFolio> leerArchivoFolios(InputStream inputStream, String nombreArchivo) throws ArchivoFoliosServiceException {
        FileInputStream archivoEntrada = null;
        OutputStream salidaArchivo = null;
        File archivo = null;
        
        try {
            archivo = new File(nombreArchivo);
            salidaArchivo = new FileOutputStream(archivo);
            int longitud;
            final int numByte = 1024;
            byte[] bufer = new byte[numByte];

            while ((longitud = inputStream.read(bufer)) > 0) {
                salidaArchivo.write(bufer, 0, longitud);
            }
            salidaArchivo.flush();
            IOUtils.closeQuietly(salidaArchivo);
            archivoEntrada = new FileInputStream(archivo);
            
            if (nombreArchivo.endsWith(Constantes.EXCEL_ANTES_2007)) {
                return validarExcelXLS(archivoEntrada);
            } else if (nombreArchivo.endsWith(Constantes.EXCEL_DESPUES_2007)) {
                return validarExcelXLSX(archivoEntrada);
            } else {
                throw new ArchivoFoliosServiceException("Error: Formato de archivo no soportado");
            }

        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ArchivoFoliosServiceException("Error archivo no encontrado, " + e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ArchivoFoliosServiceException("Error al leer el archivo" + e.getMessage(), e);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new ArchivoFoliosServiceException("Error con el archivo " + ex.getMessage(), ex);
        } finally {
            try {

                IOUtils.closeQuietly(salidaArchivo);
                IOUtils.closeQuietly(archivoEntrada);

                if (salidaArchivo != null) {
                    salidaArchivo.close();
                }
                if (archivoEntrada != null) {
                    archivoEntrada.close();
                }
                if (archivo != null) {
                    boolean resultado = archivo.delete();
                    if (resultado) {
                        LOGGER.info("Se ejecuto correctamente leerArchivoFolios.");
                    }
                }

            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
    }

    /**
     *
     * @param fileInputStream
     * @return rangoFolioList
     */
    private List<RangoFolio> validarExcelXLS(final FileInputStream fileInputStream) throws ArchivoFoliosServiceException {
        List<RangoFolio> rangoFolioList = new ArrayList<RangoFolio>();
        RangoFolio rangoFolio;
        try {
            HSSFWorkbook libroExcel = new HSSFWorkbook(fileInputStream);
            HSSFSheet hoja = libroExcel.getSheetAt(0);
            Iterator renglones = hoja.rowIterator();
            int contadorFila = 0;
            StringBuilder erroresArchivo = new StringBuilder();
            boolean errorArchivo = Boolean.FALSE;

            for (int i = 0; i < 1 && renglones.hasNext(); i++) {
                renglones.next();
            }

            while (renglones.hasNext()) {
                contadorFila++;
                HSSFRow renglon = ((HSSFRow) renglones.next());

                try {
                    rangoFolio = validarRegistro(renglon, contadorFila);

                } catch (ArchivoFoliosServiceException e) {
                    errorArchivo = Boolean.TRUE;
                    erroresArchivo.append(e.getMessage());
                    erroresArchivo.append(Constantes.SALTO_LINEA);
                    break;
                }

                if (!errorArchivo && rangoFolio != null) {
                    rangoFolioList.add(rangoFolio);
                }
            }
            if (errorArchivo) {
                throw new ArchivoFoliosServiceException(erroresArchivo.toString());
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ArchivoFoliosServiceException("Error al leer el archivo, " + e.getMessage(), e);
        }
        Collections.sort(rangoFolioList);
        return rangoFolioList;
    }

    /**
     *
     * @param fileInputStream
     * @return rangoFoliosList
     */
    private List<RangoFolio> validarExcelXLSX(final FileInputStream fileInputStream) throws ArchivoFoliosServiceException {

        List<RangoFolio> rangoFoliosList = new ArrayList<RangoFolio>();
        RangoFolio rangoFolio;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            int contadorFila = 1;
            StringBuilder erroresArchivo = new StringBuilder();
            boolean errorArchivo = Boolean.FALSE;

            for (int i = 0; i < 1 && rows.hasNext(); i++) {
                rows.next();
            }

            while (rows.hasNext()) {
                contadorFila++;
                XSSFRow row = ((XSSFRow) rows.next());

                try {
                    rangoFolio = validarRegistro(row, contadorFila);

                } catch (ArchivoFoliosServiceException e) {
                    errorArchivo = Boolean.TRUE;
                    erroresArchivo.append(e.getMessage());
                    erroresArchivo.append(Constantes.SALTO_LINEA);
                    break;
                }

                if (!errorArchivo && rangoFolio != null) {
                    rangoFoliosList.add(rangoFolio);
                }
            }
            if (errorArchivo) {
                throw new ArchivoFoliosServiceException(erroresArchivo.toString());
            }

        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ArchivoFoliosServiceException("Error archivo no encontrado, " + e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ArchivoFoliosServiceException("Error al leer el archivo, " + e.getMessage(), e);
        }
        Collections.sort(rangoFoliosList);
        return rangoFoliosList;
    }

    /**
     *
     * @param row
     * @param contadorFila
     * @return rangoFolio
     */
    private RangoFolio validarRegistro(final Row row, final int contadorFila) throws ArchivoFoliosServiceException {
        Boolean errorArchivo = Boolean.FALSE;
        StringBuilder erroresRegistro = new StringBuilder();
        RangoFolio rangoFolio = new RangoFolio();
        rangoFolio.setEstado(EstadoEnum.CANCELADO.getId());

        loop:
        for (int i = 0; i < NUMERO_CAMPOS_ARCHIVO_FOLIOS_CANCELADOS; i++) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                switch (i) {
                    case 0:
                        if (!this.validarRangoFolioInicial(cell)) {
                            errorArchivo = Boolean.TRUE;
                            erroresRegistro.append(this.agregarMensajeErrorRangoFolioInicial(contadorFila));
                            break loop;
                        } else {
                            rangoFolio.setFolioInicial((long) cell.getNumericCellValue());
                        }
                        break;

                    case 1:
                        if (!this.validarRangoFolioFinal(rangoFolio.getFolioInicial(), cell)) {
                            errorArchivo = Boolean.TRUE;
                            erroresRegistro.append(this.agregarMensajeErrorRangoFolioFinal(contadorFila));
                            break loop;
                        } else {
                            rangoFolio.setFolioFinal((long) cell.getNumericCellValue());
                        }
                        break;

                    default:
                        errorArchivo = Boolean.TRUE;
                        erroresRegistro.append(this.agregarMensajeErrorColumnaNoValida(contadorFila, i));
                        break;
                }

            } else {
                errorArchivo = Boolean.TRUE;
                erroresRegistro.append(this.agregarMensajeErrorCeldaObligatoria(contadorFila));
            }
        }

        if (errorArchivo) {
            throw new ArchivoFoliosServiceException(erroresRegistro.toString());
        }
        return rangoFolio;
    }

    /**
     *
     * @param cellRangoFolioInicial
     * @return boolean
     */
    private boolean validarRangoFolioInicial(final Cell cellRangoFolioInicial) {
        boolean resultado = Boolean.TRUE;
        if (cellRangoFolioInicial.getCellType() != XSSFCell.CELL_TYPE_NUMERIC) {
            resultado = Boolean.FALSE;
        } else {
            long folio = (long) cellRangoFolioInicial.getNumericCellValue();
            resultado = folio >= Constantes.FOLIO_MINIMO && folio <= mx.gob.sat.mat.tabacos.constants.Constantes.FOLIO_MAXIMO;
        }
        return resultado;
    }

    /**
     *
     * @param folioInicial
     * @param cellRangoFolioFinal
     * @return boolean
     */
    private boolean validarRangoFolioFinal(final long folioInicial, final Cell cellRangoFolioFinal) {
        boolean resultado = Boolean.TRUE;
        if (cellRangoFolioFinal.getCellType() != XSSFCell.CELL_TYPE_NUMERIC) {
            resultado = Boolean.FALSE;
        } else {
            long folioFinal = (long) cellRangoFolioFinal.getNumericCellValue();
            resultado = (folioFinal >= Constantes.FOLIO_MINIMO && folioFinal <= mx.gob.sat.mat.tabacos.constants.Constantes.FOLIO_MAXIMO);
            if (resultado) {
                resultado = folioFinal >= folioInicial;
            }
        }
        return resultado;
    }

    /**
     *
     * @param fila
     * @return msjError
     */
    private String agregarMensajeErrorRangoFolioInicial(final int fila) {
        StringBuilder msjError = new StringBuilder();
        msjError.append(ERROR_FILA);
        msjError.append(fila);
        msjError.append(ERROR_COLUMNA);
        msjError.append(Constantes.FOLIO_MINIMO);
        msjError.append(AND);
        msjError.append(Constantes.FOLIO_MAXIMO);
        msjError.append(Constantes.SALTO_LINEA);
        return msjError.toString();
    }

    /**
     *
     * @param fila
     * @return msjError
     */
    private String agregarMensajeErrorRangoFolioFinal(final int fila) {
        StringBuilder msjError = new StringBuilder();
        msjError.append(ERROR_FILA);
        msjError.append(fila);
        msjError.append(ERROR_COLUMNA);
        msjError.append(Constantes.FOLIO_MINIMO);
        msjError.append(AND);
        msjError.append(Constantes.FOLIO_MAXIMO);
        msjError.append(ERROR_FOLIO_FINAL);
        msjError.append(Constantes.SALTO_LINEA);
        return msjError.toString();
    }

    /**
     *
     * @param fila
     * @return msjError
     */
    private String agregarMensajeErrorCeldaObligatoria(final int fila) {
        StringBuilder msjError = new StringBuilder();
        msjError.append(ERROR_FILA);
        msjError.append(fila);
        msjError.append(ERROR_OBLIGATORIO);
        msjError.append(Constantes.SALTO_LINEA);
        return msjError.toString();
    }

    /**
     * *
     *
     * @param fila
     * @param columna
     * @return msjError
     */
    private String agregarMensajeErrorColumnaNoValida(final int fila, final int columna) {
        StringBuilder msjError = new StringBuilder();
        msjError.append(ERROR_FILA);
        msjError.append(fila);
        msjError.append(ERRORFILA);
        msjError.append(columna);
        msjError.append(ERROR_VALIDA);
        msjError.append(Constantes.SALTO_LINEA);
        return msjError.toString();
    }

}
