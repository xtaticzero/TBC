/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.CodigoInvalidoDao;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MMMF
 */
@Repository
@Transactional
@Qualifier("codigoInvalidoDaoImpl")
public class CodigoInvalidoDaoImpl implements CodigoInvalidoDao {

    protected static final Logger LOGGER = Logger.getLogger(CodigoInvalidoDaoImpl.class);

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    @Override
    public int guardar(Codigo codigo) throws DaoException {
        int r = 0;
        final Long id = jdbcTemplate.queryForObject(SQL_SELECT_NEXTID_CODIGOINVALIDO, Long.class);
        codigo.setIdCodigoFalso(id);

        if (validaCamposCodigoInvalido(codigo)) {
            r = jdbcTemplate.update(INSERT_CODIGOINVALIDO,
                    new Object[]{
                        codigo.getIdCodigoFalso(),
                        codigo.getIdRangoFolio(),
                        codigo.getFolioInicial(),
                        codigo.getFolioFinal(),
                        codigo.getFecRegistro(),
                        //codigo.getFecCaptura(),
                        codigo.getJustificacion()
                    });
        } else {
            throw new DaoException("No se pudo guardar CODIGO_INVALIDO verificar IDCODIGO, IDRANGO, JUSTIFICACION no sean NULL");
        }
        LOGGER.info("Se afecto " + r + " registros en CODINVALIDO");
        return r;
    }

    /**
     * Busqueda para reporte de codigos no validos
     *
     * @param filtro
     * @return
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException
     */
    @Override
    public List<Codigo> getCodigosInvalidos(ReportesFiltroBase filtro) throws DaoException {
        List<Codigo> codigos;
        String strQuery = null;
        Object[] parametros = null;

        try {
            switch (selectOpciones(filtro)) {
                case OPCION1:
                    strQuery = SQL_SELECT_CODIGO
                            + SQL_SELECT_CODIGO_OP3;
                    parametros = new Object[]{filtro.getRfc(),
                        filtro.getFechaInicio(), filtro.getFechaFin()};
                    break;
                case OPCION2:
                    strQuery = SQL_SELECT_CODIGO
                            + SQL_SELECT_CODIGO_OP1;
                    parametros = new Object[]{filtro.getFechaInicio(),
                        filtro.getFechaFin()};
                    break;
                case OPCION3:
                    strQuery = SQL_SELECT_CODIGO
                            + SQL_SELECT_CODIGO_OP2;
                    parametros = new Object[]{filtro.getRfc()};
                    break;
                default:
                    break;
            }

            codigos = jdbcTemplate.query(
                    strQuery, parametros,
                    new RowMapper<Codigo>() {
                        @Override
                        public Codigo mapRow(ResultSet resultSet, int i)
                        throws SQLException {
                            Codigo codigo = new Codigo();
                            codigo.setRfc(resultSet.getString("RFC"));
                            codigo.setFecMovimiento(resultSet.getDate("FECREGISTRO"));
                            codigo.setTipoCodigo("NO VALIDO");
                            BigDecimal finicial = resultSet.getBigDecimal("FOLIOINICIAL");
                            BigDecimal ffinal = resultSet.getBigDecimal("FOLIOFINAL");
                            codigo.setNumeroCodigo(rangoFechas(finicial, ffinal));
                            return codigo;
                        }
                    });
        } catch (Exception e) {
            LOGGER.error(e);
            throw new DaoException(e);
        }

        return codigos;
    }

    protected ReportesExtension.OPCIONES selectOpciones(ReportesFiltroBase filtro) {
        ReportesExtension.OPCIONES retorno = ReportesExtension.OPCIONES.OPCION0;

        if ((!filtro.getRfc().isEmpty())
                && (filtro.getFechaInicio() != null)
                && (filtro.getFechaFin() != null)) {
            retorno = ReportesExtension.OPCIONES.OPCION1;
        } else if ((filtro.getFechaInicio() != null)
                && (filtro.getFechaFin() != null)) {
            retorno = ReportesExtension.OPCIONES.OPCION2;
        } else if (!filtro.getRfc().isEmpty()) {
            retorno = ReportesExtension.OPCIONES.OPCION3;
        }

        return retorno;
    }

    /**
     *
     * @param finicial
     * @param ffinal
     * @return
     */
    protected String rangoFechas(BigDecimal finicial, BigDecimal ffinal) {
        String resultado = null;
        if ((finicial == null) && (ffinal == null)) {
            resultado = " No existe rango valido ";
        } else if (finicial == null) {
            resultado = " No existe folio inicial ";
        } else if (ffinal == null) {
            resultado = " No existe folio final ";
        } else {
            resultado = finicial.toString() + " - " + ffinal.toString();
        }
        return resultado;
    }

    @Override
    public List<Codigo> buscarCodigoInvPorRangoFolio(String rfc, Long folioInicia, Long folioFinal) throws DaoException {
        Object[] sqlParams = {rfc, folioInicia, folioFinal};
        return jdbcTemplate.query(SQL_CODINVALIDO_TABACALAREA, new CodigoMapper(), sqlParams);
    }

    private boolean validaCamposCodigoInvalido(Codigo codigo) {
        if (codigo != null && codigo.getIdCodigoFalso() != null && codigo.getIdRangoFolio() != null && codigo.getJustificacion() != null) {
            return true;
        }
        return false;
    }

    private static class CodigoMapper implements ParameterizedRowMapper<Codigo> {

        @Override
        public Codigo mapRow(ResultSet rs, int row) throws SQLException {
            Codigo codigo = new Codigo();
            codigo.setIdCodigoFalso(rs.getLong("IDCODINVALIDO"));
            codigo.setIdRangoFolio(rs.getLong("IDRANGOFOLIO"));
            codigo.setFolioInicial(rs.getString("FOLIOINICIAL"));
            codigo.setFolioFinal(rs.getString("FOLIOFINAL"));
            codigo.setFecRegistro(rs.getDate("FECREGISTRO"));
            codigo.setFecCaptura(rs.getDate("FECCAPTURA"));
            codigo.setJustificacion(rs.getString("JUSTIFICACION"));
            return codigo;
        }
    }

}
