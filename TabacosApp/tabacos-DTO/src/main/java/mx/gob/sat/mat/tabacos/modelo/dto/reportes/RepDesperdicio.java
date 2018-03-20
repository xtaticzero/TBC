package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class RepDesperdicio {

    private String marca;
    private String origen;
    private String fechaHora;
    private String planta;
    private String maquinaria;
    private String cantidadDestruccion;
    private String cantidadCigarros;
    private String cantidadProduccion;
    private String lote;
    private String numeroRegistro;
    private String cantidadAutorizada;
    private long folioInicial;
    private long folioFinal;

    /**
     *
     */
    public RepDesperdicio() {
        this.marca = "";
        this.origen = "";
        this.fechaHora = "";
        this.planta = "";
        this.maquinaria = "";
        this.cantidadDestruccion = "";
        this.cantidadProduccion = "";
        this.cantidadAutorizada = "";
        this.cantidadCigarros = "";
        this.folioInicial = 0L;
        this.folioFinal = 0L;
        this.lote = "";
        this.numeroRegistro = "";
    }

    /**
     *
     * @return
     */
    public String getMarca() {
        return marca;
    }

    /**
     *
     * @param marca
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     *
     * @return
     */
    public String getOrigen() {
        return origen;
    }

    /**
     *
     * @param origen
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     *
     * @return
     */
    public String getFechaHora() {
        return fechaHora;
    }

    /**
     *
     * @param fechaHora
     */
    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     *
     * @return
     */
    public String getPlanta() {
        return planta;
    }

    /**
     *
     * @param planta
     */
    public void setPlanta(String planta) {
        this.planta = planta;
    }

    /**
     *
     * @return
     */
    public String getMaquinaria() {
        return maquinaria;
    }

    /**
     *
     * @param maquinaria
     */
    public void setMaquinaria(String maquinaria) {
        this.maquinaria = maquinaria;
    }

    /**
     *
     * @return
     */
    public String getCantidadDestruccion() {
        return cantidadDestruccion;
    }

    /**
     *
     * @param cantidadDestruccion
     */
    public void setCantidadDestruccion(String cantidadDestruccion) {
        this.cantidadDestruccion = cantidadDestruccion;
    }

    /**
     *
     * @return
     */
    public String getCantidadProduccion() {
        return cantidadProduccion;
    }

    /**
     *
     * @param cantidadProduccion
     */
    public void setCantidadProduccion(String cantidadProduccion) {
        this.cantidadProduccion = cantidadProduccion;
    }

    /**
     *
     * @return
     */
    public String getLote() {
        return lote;
    }

    /**
     *
     * @param lote
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     *
     * @return
     */
    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     *
     * @param numeroRegistro
     */
    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    /**
     *
     * @return
     */
    public String getCantidadAutorizada() {
        return cantidadAutorizada;
    }

    /**
     *
     * @param cantidadAutorizada
     */
    public void setCantidadAutorizada(String cantidadAutorizada) {
        this.cantidadAutorizada = cantidadAutorizada;
    }

    /**
     *
     * @return
     */
    public long getFolioInicial() {
        return folioInicial;
    }

    /**
     *
     * @param folioInicial
     */
    public void setFolioInicial(long folioInicial) {
        this.folioInicial = folioInicial;
    }

    /**
     *
     * @return
     */
    public long getFolioFinal() {
        return folioFinal;
    }

    /**
     *
     * @param folioFinal
     */
    public void setFolioFinal(long folioFinal) {
        this.folioFinal = folioFinal;
    }

    /**
     *
     * @return
     */
    public String getCantidadCigarros() {
        return cantidadCigarros;
    }

    /**
     *
     * @param cantidadCigarros
     */
    public void setCantidadCigarros(String cantidadCigarros) {
        this.cantidadCigarros = cantidadCigarros;
    }
}
