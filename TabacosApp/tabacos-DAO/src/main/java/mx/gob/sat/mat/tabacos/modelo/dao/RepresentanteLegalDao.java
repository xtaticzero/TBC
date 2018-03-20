package mx.gob.sat.mat.tabacos.modelo.dao;

import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.exceptions.RepLegalDaoException;

public interface RepresentanteLegalDao {

    String SQL_REPLEGAL_RFC = "SELECT IDREPLEGAL,IDTABACALERA,IDPROVEEDOR,NOMBRE,APELLIDOPATERNO,APELLIDOMATERNO,CORREOELECTRONICO,TELEFONO,FECINICIO,FECFIN,IDESTATUS,RFC,IDTIPOREPLEGAL,RAZONSOCIAL FROM TBCT_REPLEGAL WHERE RFC = ? AND IDESTATUS = 1";
    

    RepresentanteLegal consultarPorRfc(String rfc) throws RepLegalDaoException;
}
