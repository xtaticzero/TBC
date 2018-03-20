package mx.gob.sat.mat.tabacos.modelo.dto.tipo;

import java.io.Serializable;

/**
 *
 * @author
 */
public class TipoCodigo implements Serializable {

    private static final long serialVersionUID = -456380528647851513L;

    private int id;
    private String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
