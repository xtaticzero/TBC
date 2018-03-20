/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.Date;
import javax.servlet.http.HttpSession;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ModuloEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;
import mx.gob.sat.mat.tabacos.negocio.BitacoraTbcService;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@Service
@Qualifier("bitacoraTbcService")
public class BitacoraTbcServiceImpl implements BitacoraTbcService {

    private static final Logger LOG = Logger.getLogger(ValidadorAccesoServiceImpl.class);
    
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;

    @Override
    public void registroMovimientoBitacora(HttpSession session, IdentificadorProcesoEnum identificadorModulo, Date fechaSesion, Date fechaTramite, MovimientosBitacoraEnum mov) {

        try {
            SegbMovimientoDTO dto;
            dto = MovimientoUtil.addMovimiento(session, ModuloEnum.MODULO_TBC.getClaveDominio(), identificadorModulo.getDescripcion(), new Date(),
                    new Date(), mov.getIdMovimiento(), mov.getMovimiento());
            if (dto != null) {
                segbMovimientosDAO.insert(dto);
                LOG.error("Bitacora : MovimientoUtil.addMovimiento : " + mov.getGrupo() + " - " + mov.getModulo() + " - " + mov.getMovimiento());
            }

        } catch (AccesoDenegadoException aex) {
            LOG.error(aex);
        } catch (Exception ex) {
            LOG.error(ex);
        }

    }

}
