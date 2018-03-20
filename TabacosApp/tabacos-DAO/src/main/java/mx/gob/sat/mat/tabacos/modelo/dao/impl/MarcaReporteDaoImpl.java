/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.MarcaReporteDao;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.MarcaReporteMapper;
import mx.gob.sat.mat.tabacos.modelo.dao.sql.reportes.CRMarcaSQL;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesMarcas;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ReporteDaoException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author emmanuel
 */
@Repository
@Qualifier("marcaReporteDao")
public class MarcaReporteDaoImpl implements MarcaReporteDao {

    private static final Logger LOGGER = Logger.getLogger(MarcaReporteDaoImpl.class);
    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    @Override
    public ReportesMarcas getAltaMarcas(ReportesFiltroBase filtro) throws ReporteDaoException {
        ReportesMarcas reporteMarcas = new ReportesMarcas();
        StringBuilder sql = new StringBuilder(CRMarcaSQL.SQL_SELECT_MARCA_PROD);
        List<Object> params = new ArrayList<Object>();

        llenaParametrosAlta(filtro, sql, params);
        sql.append(CRMarcaSQL.ORDER_BY_TMAR_FECINICIO);

        LOGGER.info("sql Altas Marcas --> " + sql);
        List<Marcas> marcas = jdbcTemplate.query(sql.toString(), params.toArray(), new MarcaReporteMapper());

        setParametros(reporteMarcas, filtro, marcas);
        return reporteMarcas;
    }

    @Override
    public ReportesMarcas getModificacionMarcas(ReportesFiltroBase filtro) throws ReporteDaoException {
        ReportesMarcas reporteMarcas = new ReportesMarcas();
        StringBuilder sql = new StringBuilder(CRMarcaSQL.SELECT_MODIFICACION_MARCA);
        List<Object> params = new ArrayList<Object>();

        llenaParametrosModificacion(filtro, sql, params);
        sql.append(CRMarcaSQL.ORDER_BY_TMAR_FECMOVIMIENTO);

        LOGGER.info("sql Modificacion Marcas --> " + sql);
        List<Marcas> marcas = jdbcTemplate.query(sql.toString(), params.toArray(), new MarcaReporteMapper());

        setParametros(reporteMarcas, filtro, marcas);
        return reporteMarcas;
    }

    @Override
    public ReportesMarcas getBajaMarcas(ReportesFiltroBase filtro) throws ReporteDaoException {
        ReportesMarcas reporteMarcas = new ReportesMarcas();
        StringBuilder sql = new StringBuilder(CRMarcaSQL.SQL_SELECT_MARCA_BAJA);
        List<Object> params = new ArrayList<Object>();

        llenaParametrosBaja(filtro, sql, params);
        sql.append(CRMarcaSQL.ORDER_BY_TBAJ_FECREGISTRO);

        LOGGER.info("sql Bajas Marcas --> " + sql);
        List<Marcas> marcas = jdbcTemplate.query(sql.toString(), params.toArray(), new MarcaReporteMapper());

        setParametros(reporteMarcas, filtro, marcas);
        return reporteMarcas;
    }

    private void setParametros(ReportesMarcas reporteMarcas, ReportesFiltroBase filtro, List<Marcas> marcas) {
        reporteMarcas.setRfc(filtro.getRfc());
        if (filtro.getRfc() != null || filtro.getFechaInicio() == null || filtro.getFechaFin() == null) {
            reporteMarcas.setFechaInicio("");
            reporteMarcas.setFechaFin("");
        } else {
            reporteMarcas.setFechaInicio(new SimpleDateFormat("dd/MM/yyyy").format(filtro.getFechaInicio()));
            reporteMarcas.setFechaFin(new SimpleDateFormat("dd/MM/yyyy").format(filtro.getFechaFin()));
        }
        reporteMarcas.setMarcas(marcas);
    }

    private void llenaParametrosAlta(ReportesFiltroBase filtro, StringBuilder sql, List<Object> params) {

        if (fechasSinRFC(filtro)) {
            sql.append(CRMarcaSQL.AND_BETWEEN_TMAR_FECINICIO);
            params.add(filtro.getFechaInicio());
            params.add(filtro.getFechaFin());
        } else if (fechasConMarca(filtro)) {
            sql.append(CRMarcaSQL.AND_TMAR_IDMARCA);
            sql.append(CRMarcaSQL.AND_BETWEEN_TMAR_FECINICIO);

            params.add(filtro.getMarca());
            params.add(filtro.getFechaInicio());
            params.add(filtro.getFechaFin());

        } else if (rfcYMarca(filtro)) {
            sql.append(CRMarcaSQL.AND_TTAB_RFC);
            sql.append(CRMarcaSQL.AND_TMAR_IDMARCA);

            params.add(filtro.getRfc());
            params.add(filtro.getMarca());
        } else if (marcaSinRfc(filtro)) {
            sql.append(CRMarcaSQL.AND_TMAR_IDMARCA);
            params.add(filtro.getMarca());
        }/**
         * Si tiene rfc.
         */
        else if (filtro.getRfc() != null) {
            sql.append(CRMarcaSQL.AND_TTAB_RFC);
            params.add(filtro.getRfc());
        }
    }

    private void llenaParametrosModificacion(ReportesFiltroBase filtro, StringBuilder sql, List<Object> params) {
        if (fechasSinRFC(filtro)) {
            sql.append(CRMarcaSQL.BETWEEN_FECHA_MOVIMIENTO);
            params.add(filtro.getFechaInicio());
            params.add(filtro.getFechaFin());
        } else if (fechasConMarca(filtro)) {
            sql.append(CRMarcaSQL.AND_TMAR_IDMARCA);
            sql.append(CRMarcaSQL.BETWEEN_FECHA_MOVIMIENTO);

            params.add(filtro.getMarca());
            params.add(filtro.getFechaInicio());
            params.add(filtro.getFechaFin());

        } else if (rfcYMarca(filtro)) {
            sql.append(CRMarcaSQL.AND_TTAB_RFC);
            sql.append(CRMarcaSQL.AND_TMAR_IDMARCA);

            params.add(filtro.getRfc());
            params.add(filtro.getMarca());
        } else if (marcaSinRfc(filtro)) {
            sql.append(CRMarcaSQL.AND_TMAR_IDMARCA);
            params.add(filtro.getMarca());
        }/**
         * Si tiene rfc.
         */
        else if (filtro.getRfc() != null) {
            sql.append(CRMarcaSQL.AND_TTAB_RFC);
            params.add(filtro.getRfc());
        }
    }

    private void llenaParametrosBaja(ReportesFiltroBase filtro, StringBuilder sql, List<Object> params) {
        if (fechasSinRFC(filtro)) {
            sql.append(CRMarcaSQL.BETWEEN_FECHA_REGISTRO);
            params.add(filtro.getFechaInicio());
            params.add(filtro.getFechaFin());
        } else if (fechasConMarca(filtro)) {
            sql.append(CRMarcaSQL.AND_TMAR_IDMARCA);
            sql.append(CRMarcaSQL.BETWEEN_FECHA_REGISTRO);

            params.add(filtro.getMarca());
            params.add(filtro.getFechaInicio());
            params.add(filtro.getFechaFin());

        } else if (rfcYMarca(filtro)) {
            sql.append(CRMarcaSQL.AND_TTAB_RFC);
            sql.append(CRMarcaSQL.AND_TMAR_IDMARCA);

            params.add(filtro.getRfc());
            params.add(filtro.getMarca());
        } else if (marcaSinRfc(filtro)) {
            sql.append(CRMarcaSQL.AND_TMAR_IDMARCA);
            params.add(filtro.getMarca());
        }/**
         * Si tiene rfc.
         */
        else if (filtro.getRfc() != null) {
            sql.append(CRMarcaSQL.AND_TTAB_RFC);
            params.add(filtro.getRfc());
        }
    }

    /**
     * Si tiene fecha inicio y fin, pero no rfc.
     */
    private boolean fechasSinRFC(ReportesFiltroBase filtro) {
        boolean flgFechas = (filtro.getFechaInicio() != null && filtro.getFechaFin() != null);
        boolean flgRFCFiltro = (filtro.getRfc() == null||filtro.getRfc().length()==0);
        boolean flgFiltroMarca = (filtro.getMarca() == null);
        
        return flgFechas  && flgRFCFiltro && flgFiltroMarca;
    }

    /**
     * Si tiene marca, fecha inicio y fin.
     */
    private boolean fechasConMarca(ReportesFiltroBase filtro) {
        return (filtro.getFechaInicio() != null && filtro.getFechaFin() != null)
                && filtro.getMarca() != null && filtro.getRfc() == null;
    }

    /**
     * Si tiene rfc y marca.
     */
    private boolean rfcYMarca(ReportesFiltroBase filtro) {
        return filtro.getRfc() != null && filtro.getMarca() != null;
    }

    /**
     * Si tiene marca, pero no rfc.
     */
    private boolean marcaSinRfc(ReportesFiltroBase filtro) {
        return filtro.getMarca() != null && filtro.getRfc() == null;
    }
}
