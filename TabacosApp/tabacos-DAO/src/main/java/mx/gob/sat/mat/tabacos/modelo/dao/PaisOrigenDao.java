package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PaisDaoException;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public interface PaisOrigenDao {

    String SQL_SELECT_PAIS = "SELECT IDPAIS, DESLARGA, DESCORTA FROM RFCC_PAIS WHERE IDPAIS > 0 ORDER BY DESCORTA";
    
    String SQL_SELECT_ORIGEN = "SELECT IDPAIS, DESLARGA, DESCORTA FROM RFCC_PAIS WHERE IDPAIS > 0 ORDER BY DESCORTA";

    String SQL_SELECT_PAIS_BY_CODIGO = "SELECT * FROM RFCC_PAIS WHERE CLAVEPAIS = ?";

    String SQL_SELECT_PAIS_BY_ID = "SELECT * FROM RFCC_PAIS WHERE IDPAIS = ?";

    String SQL_SELECT_PAIS_HEDER = "SELECT IDPAIS,\n"
            + "CLAVEPAIS,\n"
            + "DESCORTA,\n"
            + "DESLARGA\n"
            + "FROM RFCC_PAIS \n"
            + "WHERE \n";

    List<PaisOrigen> selectedPais() throws PaisDaoException;

    List<PaisOrigen> selectedOrigen() throws PaisDaoException;

    PaisOrigen getPaisByCodigo(String codigo) throws PaisDaoException;

    PaisOrigen getPaisById(Long idPais) throws PaisDaoException;

    List<PaisOrigen> getLstPaisOrigen(Long... idPaises) throws PaisDaoException;
}
