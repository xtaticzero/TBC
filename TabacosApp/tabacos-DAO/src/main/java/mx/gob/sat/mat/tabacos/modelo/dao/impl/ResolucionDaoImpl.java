/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.ResolucionDao;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MMMF
 */
@Repository
@Transactional
@Qualifier("resolucionDaoImpl")
public class ResolucionDaoImpl implements ResolucionDao{
    protected static final Logger LOGGER = Logger.getLogger(ResolucionDaoImpl.class);
    
    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;
    
    public Resolucion validaRango(Long folioInicia, Long folioFinal, Long idSolicitud) throws DaoException{
         
        Object[] sqlParams = {idSolicitud, folioInicia, folioFinal};
        List<Resolucion> resoluciones  = jdbcTemplate.query(SQL_SELECT_RESOLUCION_FOLIOS_IDSOLICITUD, new ResolucionMapper(), sqlParams);
        Resolucion r = resoluciones != null && !resoluciones.isEmpty() ? resoluciones.get(0): null;
        
        LOGGER.info("Resultado de validacion de Rango (RESOLUCION -IDSOLICITUD: "+idSolicitud+"): " + r);
        return r;
    }

    public List<Resolucion> buscarPorFoliosLimites(Long folioInicia, Long folioFinal, Long idSolicitud) throws DaoException{
        Object[] sqlParams = {idSolicitud, folioInicia, folioFinal, folioInicia, folioFinal};
        List<Resolucion> resoluciones;
        int result;

        resoluciones = jdbcTemplate.query(SQL_SELECT_RESOLUCION_TODOS_INTERMEDIOS, new ResolucionMapper(), sqlParams);
        result = resoluciones != null ? resoluciones.size() : 0;
        if (resoluciones != null) {
            for (Resolucion r : resoluciones) {
                LOGGER.info("Desde BD Resolucion: "+r);
            }
        }
        LOGGER.info("Num de solicitudes para el proveedor: " + result);
        return resoluciones;
    }
    
    
    private static class ResolucionMapper implements ParameterizedRowMapper<Resolucion> {
        /*
        "SELECT IDRESOLUCION, IDSOLICITUD, IDESTRESOLUCION, FECRESOLUCION,"
            + " NUMSERVIDORPUBLICO, FOLIOINICIAL, FOLIOFINAL, FECHAINICIO, FECHAFIN, IDESTCARGADOR "
            + " FROM tbct_resolucion"
        */

        @Override
        public Resolucion mapRow(ResultSet rs, int row) throws SQLException {
            Resolucion resolucion = new Resolucion();
            resolucion.setIdResolucion(rs.getLong("IDRESOLUCION"));
            resolucion.setIdSolicitud(rs.getLong("IDSOLICITUD"));
            resolucion.setIdEstResolucion(rs.getLong("IDESTRESOLUCION"));
            resolucion.setFecResolucion(rs.getDate("FECRESOLUCION"));
            resolucion.setNumServidorPublico(rs.getLong("NUMSERVIDORPUBLICO"));
            resolucion.setFolioInicial(rs.getLong("FOLIOINICIAL"));
            resolucion.setFolioFinal(rs.getLong("FOLIOFINAL"));
            resolucion.setFechaInicio(rs.getDate("FECHAINICIO"));
            resolucion.setFechaFin(rs.getDate("FECHAFIN"));
            resolucion.setIdEstCargador(rs.getLong("IDESTCARGADOR"));

            return resolucion;
        }
    }
}
