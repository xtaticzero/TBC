/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.util.List;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class ReportesRegistrosYDespedicios extends ReportesFiltroBase 
                                     {
    private static final long serialVersionUID = 4214228453402463416L;
    public static final String LOTE = "Lote de producción";
    public static final String PLANTA = "Planta de producción";
    public static final String CANTIDAD = "Cantidad de producción";
    public static final String PAIS = "Pais de producción";
    public static final String MAQUINARIA = "Máquina de producción";
    public static final String ORIGEN = "Origen";
    public static final String NUMERO = "Numero de producción";
    public static final String PRODUCTO = "Producto";
    public static final String FECHA = "Fecha y Hora";
    public static final String CANTDEST = "Cantidad de destrucción";
    public static final String NUMREG = "Número de registro";
    
    private int     loteProduccion;
    private int     plantaProduccion;
    private int     cantidadProduccion;
    private long    proveedor;
    private String  contribuyenteCB;
    private String  proveedorCB;
    private int     paisProducto;
    private int     maquinariaProduccion;
    private int     origen;
    private int     numeroProduccion;
    private int     producto;
    private int     fechaYHora;
    private int     cantidadDestruccion;
    private int     numeroDeRegistro;
    private int     numOpciones;

    /**
     * 
     * @param item 
     */
    public ReportesRegistrosYDespedicios(ReportesFiltroBase item) {
        super(item);
        this.loteProduccion = 0;
        this.plantaProduccion = 0;
        this.cantidadProduccion = 0;
        this.proveedor = 0L;        
        this.paisProducto = 0;
        this.maquinariaProduccion = 0;
        this.origen = 0;
        this.numeroProduccion = 0;
        this.producto = 0;
        this.fechaYHora = 0;
        this.cantidadDestruccion = 0;
        this.numeroDeRegistro = 0;
        this.contribuyenteCB = "";
        this.proveedorCB = "";
    }
    
    public ReportesRegistrosYDespedicios() {
        super();        
    }
    
    /**
     * 
     * @return 
     */
    public int getPlantaProduccion() {
        return plantaProduccion;
    }

    /**
     * 
     * @param plantaProduccion 
     */
    public void setPlantaProduccion(int plantaProduccion) {
        this.plantaProduccion = plantaProduccion;
    }

    /**
     * 
     * @return 
     */
    public int getCantidadProduccion() {
        return cantidadProduccion;
    }

    /**
     * 
     * @param cantidadProduccion 
     */
    public void setCantidadProduccion(int cantidadProduccion) {
        this.cantidadProduccion = cantidadProduccion;
    }

    /**
     * 
     * @return 
     */
    public long getProveedor() {
        return proveedor;
    }

    /**
     * 
     * @param proveedor 
     */
    public void setProveedor(long proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * 
     * @return 
     */
    public String getContribuyenteCB() {
        return contribuyenteCB;
    }

    /**
     * 
     * @param contribuyenteCB 
     */
    public void setContribuyenteCB(String contribuyenteCB) {
        this.contribuyenteCB = contribuyenteCB;
    }

    /**
     * 
     * @return 
     */
    public String getProveedorCB() {
        return proveedorCB;
    }

    /**
     * 
     * @param proveedorCB 
     */
    public void setProveedorCB(String proveedorCB) {
        this.proveedorCB = proveedorCB;
    }    

    public int getPaisProducto() {
        return paisProducto;
    }

    /**
     * 
     * @param paisProducto 
     */
    public void setPaisProducto(int paisProducto) {
        this.paisProducto = paisProducto;
    }

    /**
     * 
     * @return 
     */
    public int getMaquinariaProduccion() {
        return maquinariaProduccion;
    }

    /**
     * 
     * @param maquinariaProduccion 
     */
    public void setMaquinariaProduccion(int maquinariaProduccion) {
        this.maquinariaProduccion = maquinariaProduccion;
    }

    /**
     * 
     * @return 
     */
    public int getOrigen() {
        return origen;
    }

    /**
     * 
     * @param origen 
     */
    public void setOrigen(int origen) {
        this.origen = origen;
    }

    /**
     * 
     * @return 
     */
    public int getNumeroProduccion() {
        return numeroProduccion;
    }

    /**
     * 
     * @param numeroProduccion 
     */
    public void setNumeroProduccion(int numeroProduccion) {
        this.numeroProduccion = numeroProduccion;
    }

    /**
     * 
     * @return 
     */
    public int getProducto() {
        return producto;
    }

    /**
     * 
     * @param producto 
     */
    public void setProducto(int producto) {
        this.producto = producto;
    }

    /**
     * 
     * @return 
     */
    public int getFechaYHora() {
        return fechaYHora;
    }

    /**
     * 
     * @param fechaYHora 
     */
    public void setFechaYHora(int fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    /**
     * 
     * @return 
     */
    public int getCantidadDestruccion() {
        return cantidadDestruccion;
    }

    /**
     * 
     * @param cantidadDestruccion 
     */
    public void setCantidadDestruccion(int cantidadDestruccion) {
        this.cantidadDestruccion = cantidadDestruccion;
    }

    /**
     * 
     * @return 
     */
    public int getNumeroDeRegistro() {
        return numeroDeRegistro;
    }

    /**
     * 
     * @param numeroDeRegistro 
     */
    public void setNumeroDeRegistro(int numeroDeRegistro) {
        this.numeroDeRegistro = numeroDeRegistro;
    }                           

    public int getLoteProduccion() {
        return loteProduccion;
    }

    public void setLoteProduccion(int loteProduccion) {
        this.loteProduccion = loteProduccion;
    }
       
    /**
     * 
     * @param opciones 
     */
    public void initOpciones(List<String> opciones) {
        numOpciones = 0;
        for(String info : opciones) {
            if (info.equals(LOTE)) {
                loteProduccion = 1;
                numOpciones++;
            }    
            if (info.equals(PLANTA)) {
                plantaProduccion = 1;
                numOpciones++;
            }
            
            if (info.equals(CANTIDAD)) {
                cantidadProduccion = 1;
                numOpciones++;
            }
            
            if (info.equals(PAIS)) {
                paisProducto = 1;
                numOpciones++;
            }
            
            if (info.equals(MAQUINARIA)) {
                maquinariaProduccion = 1;
                numOpciones++;
            }
        }
    }
    
    
     /**
     * 
     * @param opciones 
     */
    public void initOpciones2(List<String> opciones) {
            
        for(String info : opciones) {
            if (info.equals(ORIGEN)) {
                origen = 1;
                numOpciones++;
            }
            
            if (info.equals(NUMERO)) {
                numeroProduccion = 1;
                numOpciones++;
            }
            
            if (info.equals(PRODUCTO)) {
                producto = 1;
                numOpciones++;
            }
            
            if (info.equals(FECHA)) {
                fechaYHora = 1;
                numOpciones++;
            }
            
            if (info.equals(CANTDEST)) {
                cantidadDestruccion = 1;  
                numOpciones++;
            }
            
            if (info.equals(NUMREG)) {
                numeroDeRegistro = 1; 
                numOpciones++;
            }
        }
    }

    public int getNumOpciones() {
        return numOpciones;
    }       
}