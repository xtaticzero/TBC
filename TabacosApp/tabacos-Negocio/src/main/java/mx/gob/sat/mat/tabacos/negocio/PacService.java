/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import mx.gob.sat.mat.tabacos.negocio.excepcion.PacServiceException;
import mx.gob.sat.mat.tabacos.ws.dto.TBCAcuRboSlc;
import mx.gob.sat.mat.tabacos.ws.dto.TBCAcuseRetroInfo;
import mx.gob.sat.mat.tabacos.ws.dto.TBCConsulFolio;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRespConsulFolio;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRespDescarga;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRetroInfo;
import mx.gob.sat.mat.tabacos.ws.dto.TBCSlcCodSeg;
import mx.gob.sat.mat.tabacos.ws.dto.TBCSolDescarga;

/**
 *
 * @author root
 */
public interface PacService {

    String PAIS_NO_ENCONTRADO = "PAIS ORIGEN NO ENCONTRADO";
    String PLANTA_NO_ENCONTRADA = "PLANTA NO ENCONTRADA";
    String MARCA_NO_ENCONTRADA = "MARCA NO ENCONTRADA";
    String RETRO_REGISTRADA = "RETROALIMENTACION REGISTRADA";
    String RETROALIMENTAR_UNA_TABACALERA = "LA RETROALIMENTACION ES SOLO PARA UN CONTRIBUYENTE, VALIDAR RFC";

    TBCAcuRboSlc solicitarCodigos(TBCSlcCodSeg tbcSlcCodSeg) throws PacServiceException;

    TBCRespConsulFolio consultarEstatusSolicitud(TBCConsulFolio tbcConsulFolio) throws PacServiceException;

    TBCRespDescarga descargarCodigos(TBCSolDescarga tbcSolDescarga) throws PacServiceException;

    TBCAcuseRetroInfo retroalimentarCodigos(TBCRetroInfo tbcRetroInfo) throws PacServiceException;
}
