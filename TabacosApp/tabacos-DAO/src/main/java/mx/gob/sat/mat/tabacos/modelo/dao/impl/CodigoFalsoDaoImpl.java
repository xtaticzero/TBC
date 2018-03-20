/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.CodigoFalsoDao;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MMMF
 */
@Repository
@Transactional
@Qualifier("codigoFalsoDaoImpl")
public class CodigoFalsoDaoImpl implements CodigoFalsoDao {
    
    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    public int guardar(Codigo codigo) throws DaoException {
        int r = 0;
        final Long id = jdbcTemplate.queryForObject(SQL_SELECT_NEXTID_CODIGOFALSO, Long.class);
        codigo.setIdCodigoFalso(id);
        
        if (validaCamposCodigoFalso(codigo)) {
            r = jdbcTemplate.update(INSERT_CODIGOFALSO,
                    new Object[]{
                        codigo.getIdCodigoFalso(),
                        codigo.getIdMarca(),
                        codigo.getFecRegistro(),
                        codigo.getJustificacion(),
                        codigo.getNumeroCodigo()
                    });
        } else {
            throw new DaoException("No se pudo guardar CODIGO_FALSO verificar valores de IDCODIGO, IDRANGO, JUSTIFICACION, CODIGO no sean NULL");
        }
        return r;
    }
    
    /**
     * Busqueda para reporte de codigos falsos
     *
     * @param filtro
     * @return
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException
     */
    @Override
    public List<Codigo> getCodigosFalsos(ReportesFiltroBase filtro) throws DaoException {
        List<Codigo> codigos;
        String strQuery = SQL_SELECT_FALSOS;
        Object[] parametros = null;

        try {
            switch (selectOpciones(filtro)) {
                case OPCION1:
                    
                    strQuery = SQL_SELECT_FALSOS + SQL_SELECT_FALSOS_OP2;
                    parametros = new Object[]{filtro.getRfc()};
                    
                    break;
                case OPCION2:
                    strQuery = SQL_SELECT_FALSOS + SQL_SELECT_FALSOS_OP1;
                    parametros = new Object[]{filtro.getFechaInicio(),
                        filtro.getFechaFin()};
                    break;
                case OPCION3:
                    strQuery = SQL_SELECT_FALSOS + SQL_SELECT_FALSOS_OP2;
                    parametros = new Object[]{filtro.getRfc()};
                    break;
                default:
                    break;
            }

            codigos = jdbcTemplate.query( strQuery, parametros, new CodigosFalsosRowMapper());
            if (codigos == null || codigos.isEmpty()){
                
                boolean flgFechas = (filtro.getFechaInicio()!=null) && (filtro.getFechaFin()!=null);
                boolean flgRFC = filtro.getRfc()!=null;
                
                if (flgFechas&&!flgRFC){
                    codigos = jdbcTemplate.query( SQL_SELECT_FALSOS_SINMARCA, parametros, new CodigosFalsosRowMapper());
                }
                
                
                
            }

        } catch (Exception e) {
            throw new DaoException(e);
        }

        return codigos;
    }
    
    protected ReportesExtension.OPCIONES selectOpciones(ReportesFiltroBase filtro) {
        ReportesExtension.OPCIONES retorno = ReportesExtension.OPCIONES.OPCION0;
        
        if ((!filtro.getRfc().isEmpty()) && 
                (filtro.getFechaInicio()!=null) && 
                (filtro.getFechaFin()!=null)) {
            retorno = ReportesExtension.OPCIONES.OPCION1;        
        } else if ((filtro.getFechaInicio()!=null) && 
                (filtro.getFechaFin()!=null)) {
            retorno = ReportesExtension.OPCIONES.OPCION2;
        } else if (!filtro.getRfc().isEmpty()) {
            retorno = ReportesExtension.OPCIONES.OPCION3;
        }
        
        return retorno;
    }

    private boolean validaCamposCodigoFalso(Codigo codigo) {
        if (codigo != null && codigo.getIdCodigoFalso() != null 
                && codigo.getJustificacion() != null && codigo.getNumeroCodigo() != null) {
            return true;
        }
        return false;
    }
    
    private static class CodigosFalsosRowMapper implements RowMapper {

        public Codigo mapRow(ResultSet resultSet, int rowNum) throws SQLException {

            ResultSetMetaData rmData = resultSet.getMetaData();
            int nColumnas = rmData.getColumnCount();
            String nomColumna = null;
            boolean incluirRfc = false;

            for (int c = 1; c <= nColumnas; c++) {
                nomColumna = rmData.getColumnName(c);
                if (nomColumna.compareToIgnoreCase("RFC") == 0) {
                    incluirRfc = true;
                }
            }
            Codigo codigo = new Codigo();
            codigo.setFecMovimiento(resultSet.getDate("FECREGISTRO"));
            codigo.setNumeroCodigo(resultSet.getString("CODIGO"));
            codigo.setTipoCodigo("FALSOS");
            if (incluirRfc) {
                codigo.setRfc(resultSet.getString("RFC"));
            } else {
                codigo.setRfc(" ");
            }

            return codigo;
        }
    }

}
