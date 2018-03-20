package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class SelectItem implements Serializable {

    private static final long serialVersionUID = 8064504868841722213L;

    private String id;
    private String valor;

    /**
     *
     * @param id
     * @param valor
     */
    public SelectItem(String id, String valor) {
        this.id = id;
        this.valor = valor;
    }

    /**
     *
     */
    public SelectItem() {
        this.id = "";
        this.valor = "";
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getValor() {
        return valor;
    }

    /**
     *
     * @param valor
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}
