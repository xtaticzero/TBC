/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.mat.tabacos.constants;

/**
 * Clase que contiene todos los valores duros que necesita la aplicaci&oacute;n
 */
public interface Constantes {

    //Session para Empleados
    String SESSION_PE = "accesoEF";

    //Session para contribuyentes
    String SESSION_PTSC = "acceso";

    //Parametro de ambiente
    String SCOOP_DEV = "develop";
    String SCOOP_UAT = "uat";
    String SCOOP_PROD = "prod";

    //Tamaño minimo de RFC
    int RFC_LENGTH_MIN = 10;

    /**
     * Valor que significa si el contribuyente existe en el PCBA.
     */
    int EXISTE_EN_PCBA = 1;

    /**
     * Atributo que representa el c&oacute;digo para el pa&iacute;s
     * M&eacute;xico
     */
    String CODIGO_PAIS_MEXICO = "MEX";

    /**
     * Atributo que representa la longitud que debe de tener un rfc de persona
     * moral
     */
    Integer LONGITUD_RFC_PERSONA_MORAL = 12;

    /**
     * Atributo que representa la longitud que debe de tener un rfc de persona
     * fisica
     */
    Integer LONGITUD_RFC_PERSONA_FISICA = 13;

    /**
     * Nombre con el que se reconoce el sistema de marbetes
     */
    String NOMBRE_MODULO = "TBC";

    /**
     * Atributo que representa los milisegundos de un d&iacute;a
     */
    long MILISEGUNDOS_POR_DIA = 24 * 60 * 60 * 1000;

    /**
     * Atributeo que representa salto de linea para windows
     */
    String SALTO_LINEA = "\r\n";

    /**
     * Atributo que representa un espacio vacio.
     */
    String ESPACIO_VACIO = "";

    /**
     * Postfijo de archivos excel con el formato mayor a 2007.
     */
    String EXCEL_DESPUES_2007 = ".xlsx";

    /**
     * Postfijo de archivos excel con el formato de 2007 o inferior.
     */
    String EXCEL_ANTES_2007 = ".xls";

    /**
     * Postfijo de archivos csv con el formato de 2007 o inferior.
     */
    String ARCHIVO_CSV = ".csv";

    /**
     * Extension de archivos word.
     */
    String WORD_2003 = ".doc";

    String WORD_2007 = ".docx";

    /**
     * Postfijo de archivos pdf.
     */
    String ARCHIVO_PDF = ".pdf";

    /**
     * Postfijo de archivos png.
     */
    String ARCHIVO_PNG = "png";

    /**
     * Postfijo de archivos cer.
     */
    String POSTFIJO_ARCHIVO_CER = ".cer";

    /**
     * Postfijo de archivos key.
     */
    String POSTFIJO_ARCHIVO_KEY = ".key";

    /**
     * Postfijo de archivos txt
     */
    String ARCHIVO_TXT = ".txt";

    /**
     * Atributo que indica el n&uacute;mero de campos que analiza en cada
     * registro del archivo para folios cancelados.
     */
    int NUMERO_CAMPOS_ARCHIVO_FOLIOS_CANCELADOS = 3;

    /**
     * Atributo que indica el n&uacute;mero de campos que analiza en cada
     * registro del archivo para folios cancelados.
     */
    int NUMERO_CAMPOS_ARCHIVO_FOLIOS_CANCELADOS_MERMA = 4;

    /**
     * Folio m&iacute;nimo permitido para los rangos
     */
    long FOLIO_MINIMO = 1;

    /**
     * Folio m&aacute;ximo permitido para los rangos
     */
    long FOLIO_MAXIMO = 9999999999L;

    /**
     * Directorio donde se alojan los archivos jasper para la generaci&oacute;n
     * de reportes.
     */
    String DIRECTORIO_REPORTES = "/siat/reportes/tabacos/";

    /**
     * Ruta reportes.
     */
    String RESOUCES_REPORTS = "reports/";

    /**
     * Ruta imagenes reportes.
     */
    String RESOUCES_IMAGES_REPORTS = "reports/images/";

    /**
     * Nombre del reporte prueba.
     */
    String NOMBRE_REPORTE_PRUEBAS = "ConsultaDocumentosReport";

    /**
     * Extencion de archivos jasper.
     */
    String TERMINACION_ARCHIVO_JASPER = ".jasper";

    String EXP_REG_EMAIL = "^([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,4})*$";

    /**
     * Directorio donde se alojan los archivos que carga el contribuyente.
     */
    String PAGINA_ADD_ENCONSTRUCCION_ERROR = "/pages/enConstruccionError/enConstruccionError.jsf";

    /**
     * *
     * constantes de Selladora
     */
    Integer UNO = 1;
    Integer DOS = 2;
    Integer TRES = 3;
    Integer CINCUENTA = 50;

}
