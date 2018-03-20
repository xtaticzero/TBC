/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import mx.gob.sat.mat.tabacos.modelo.dao.BusquedasDao;
import static mx.gob.sat.mat.tabacos.modelo.dao.BusquedasDao.SQL_SELECT_HIST_CONTRIBUYENTES;
import mx.gob.sat.mat.tabacos.modelo.dto.DatosBusqueda;
import mx.gob.sat.mat.tabacos.modelo.dto.Historico;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.exceptions.BusquedasDaoException;
import org.apache.log4j.Logger;

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
@Qualifier("genericoDaoImpl")
public class BusquedasDaoImpl implements BusquedasDao {
    protected static final Logger LOGGER = Logger.getLogger(BusquedasDaoImpl.class);

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    private static final String CADCONTRIBUYENTE = "contribuyente";
    private static final String CADPROVEEDOR = "proveedor";
    private static  final String CADCODIGOS = "codigo";
    private static final String CADFECHA = "fecha";
    private static final String CADMARCA = "marca";
    private static final String CADMAQUINA = "maquina";
    private static final String CADLOTE = "lote";
    private static final String CADPLANTA = "planta";
    private static final int INDICE_INDICADOR_TABLA_CODIGO = 2;
    private static final String NOMBRE_BASE_TB_CODIGO = "TBCT_CODIGO";
    
    private String condiciones;
    private List<Object> params;

    @Override
    public List<DatosBusqueda> busquedaPorFiltros(DatosBusqueda filtro) throws BusquedasDaoException {
        List<DatosBusqueda> resultado;
        condiciones = " WHERE ";
        params = new ArrayList<Object>();
        String sql = "";

        validaContribuyenteProveedor(filtro);

        if (validaCadena(filtro.getCodigo())) {
            //utilizar el select que incluye codigo
            String tablaCod = ""+filtro.getCodigo().charAt(INDICE_INDICADOR_TABLA_CODIGO);
            //reemplazar la tabla de codigo con
            sql += SQL_FILTRO_CON_CODIGO.replaceAll(NOMBRE_BASE_TB_CODIGO, NOMBRE_BASE_TB_CODIGO+tablaCod);
            condiciones += NOMBRE_BASE_TB_CODIGO+tablaCod+".CODIGO = ? AND ";
            params.add(filtro.getCodigo());
        } else {
            //utilizar query sin codigo
            sql += SQL_FILTRO_SIN_CODIGO;
        }
        
        if (validaCadena(filtro.getMarca())) {
            condiciones += " TBCT_MARCA.NOMMARCA = ? AND ";
            params.add(filtro.getMarca().trim());
        }
        
        if (validaCadena(filtro.getLoteproduccion())) {
            condiciones += " TBCT_PRODCIGARRO.NUMLOTE = ? AND ";
            params.add(filtro.getLoteproduccion().trim());
        }
        if ( sonFechasValidas(filtro.getFecha(), filtro.getFechaFin()) && !sonFechasIguales(filtro.getFecha(), filtro.getFechaFin())) {
            condiciones += " TBCT_RESOLUCION.FECRESOLUCION BETWEEN ? AND ? AND ";
            params.add(filtro.getFecha());
            params.add(filtro.getFechaFin());
        } else if (sonFechasIguales(filtro.getFecha(), filtro.getFechaFin()) ) {
            condiciones += " TO_CHAR(TBCT_RESOLUCION.FECRESOLUCION,'dd/mm/yyyy') = TO_CHAR(?, 'dd/mm/yyyy') AND ";
            params.add(filtro.getFecha());
        }

        if (validaCadena(filtro.getMaquinaproduccion())) {
            condiciones += " TBCT_MAQUINA.NOMMAQUINA = ? AND ";
            params.add(filtro.getMaquinaproduccion().trim());
        }

        if (validaCadena(filtro.getPlantaproduccion()) ) {
            condiciones += " TBCT_PLANTA.NOMPLANTA = ? AND ";
            params.add(filtro.getPlantaproduccion().trim());

        }

        //quitar la ultima ","
        condiciones = condiciones.substring(0, condiciones.lastIndexOf("AND"));
        sql += condiciones + " ORDER BY TBCT_RESOLUCION.FECRESOLUCION";
        LOGGER.info("Parametros para la busqueda: " + params.size());
        resultado = jdbcTemplate.query(sql, new DatosBusquedaRowMapper(), params.toArray());
        return resultado;
    }
    
    private void validaContribuyenteProveedor (DatosBusqueda filtro) {
        //Validar que campos trae tabacalera
        if (validaCadena(filtro.getRfcContribuyente())) {
            condiciones += " TBCP_TABACALERA.RFC = ? AND ";
            params.add(filtro.getRfcContribuyente().trim());
        }

        if (validaCadena(filtro.getRfcProveedor())) {
            condiciones += " TBCT_PROVEEDOR.RFC  = ? AND " ;
            params.add(filtro.getRfcProveedor().trim());
        }
    }
    

    private boolean validaCadena(String cadena) {
        if (cadena != null && !cadena.isEmpty() && cadena.trim().length() > 0) {
            return true;
        }
        return false;
    }
    
    private boolean sonFechasIguales (Date fecha1, Date fecha2) {
        if (fecha1 != null && fecha2 != null) {
            return fecha1.equals(fecha2);
        }
        return false;
    }
    
    private boolean sonFechasValidas (Date fecha1, Date fecha2) {
        if (fecha1 != null && fecha2 != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<Planta> getPlantas() throws BusquedasDaoException {
        return jdbcTemplate.query(SQL_PLANTAS, new PlantaRowMapper());
    }

    @Override
    public List<Historico> listarHistoricosContribuyente() throws BusquedasDaoException {
        List<Historico> historicosContribuyentes;

        historicosContribuyentes = jdbcTemplate.query(SQL_SELECT_HIST_CONTRIBUYENTES, new RowMapper<Historico>() {
            @Override
            public Historico mapRow(ResultSet resultSet, int i) throws SQLException {
                Historico hist = new Historico();
                hist.setFolio(resultSet.getString("FOLIO"));
                hist.setRfc(resultSet.getString("RFC"));
                hist.setContribuyente(resultSet.getString("CONTRIBUYENTE"));
                hist.setCantidad(resultSet.getInt("CANTIDAD"));
                hist.setFecha(resultSet.getDate("FECHA"));
                hist.setPas(resultSet.getString("PAS"));
                return hist;
            }
        });
        if (historicosContribuyentes == null) {
            historicosContribuyentes = new ArrayList<Historico>();
        }

        LOGGER.info("Num de historicos: " + historicosContribuyentes.size());
        return historicosContribuyentes;
    }
    
    public List<Historico> listarHistoricosContribuyente(String rfc) throws BusquedasDaoException {
        List<Historico> historicosContribuyentes;
        Object[] sqlParams = { rfc };

        historicosContribuyentes = jdbcTemplate.query(SQL_SELECT_HIST_CONTRIBUYENTES_RFC, new RowMapper<Historico>() {
            public Historico mapRow(ResultSet resultSet, int i) throws SQLException {
                Historico hist = new Historico();
                hist.setFolio(resultSet.getString("FOLIO"));
                hist.setRfc(resultSet.getString("RFC"));
                hist.setContribuyente(resultSet.getString("CONTRIBUYENTE"));
                hist.setCantidad(resultSet.getInt("CANTIDAD"));
                hist.setFecha(resultSet.getDate("FECHA"));
                hist.setPas(resultSet.getString("PAS"));
                return hist;
            }
        }, sqlParams);
        
        if (historicosContribuyentes == null) {
            historicosContribuyentes = new ArrayList<Historico>();
        }

        LOGGER.info("Num de historicos: " + historicosContribuyentes.size());
        return historicosContribuyentes;
    }

    private static class PlantaRowMapper implements RowMapper {
        public Planta mapRow(ResultSet rs, int rowNum) throws SQLException {
            Planta planta = new Planta();
            planta.setFecBaja(rs.getDate("FECBAJA"));
            planta.setFecRegistro(rs.getDate("FECREGISTRO"));
            planta.setIdEstatus(rs.getLong("IDESTATUS"));
            planta.setIdPlanta(rs.getLong("IDPLANTA"));
            planta.setIdTabacalera(rs.getLong("IDTABACALERA"));
            planta.setNomPlanta(rs.getString("NOMPLANTA"));
            planta.setNombrePlanta(planta.getNomPlanta());
            return planta;
        }
    }

    private static class DatosBusquedaRowMapper implements RowMapper {

        public DatosBusqueda mapRow(ResultSet resultSet, int rowNum) throws SQLException {
             DatosBusqueda result = new DatosBusqueda();
                ResultSetMetaData rmData = resultSet.getMetaData();
                int nColumnas = rmData.getColumnCount();
                String nomColumna = null;
                
                for (int c = 1; c <= nColumnas; c++) {
                    nomColumna = rmData.getColumnName(c);
                    
                    if (nomColumna.compareToIgnoreCase(CADCONTRIBUYENTE) == 0) {
                        result.setRfcContribuyente(resultSet.getString(CADCONTRIBUYENTE));
                    } else if (nomColumna.compareToIgnoreCase(CADPROVEEDOR) == 0) {
                        result.setRfcProveedor(resultSet.getString(CADPROVEEDOR));
                    } else if (nomColumna.compareToIgnoreCase(CADMARCA) == 0) {
                        result.setMarca(resultSet.getString(CADMARCA));
                    } else if (nomColumna.compareToIgnoreCase(CADMAQUINA) == 0) {
                        result.setMaquinaproduccion(resultSet.getString(CADMAQUINA));
                    } else if (nomColumna.compareToIgnoreCase(CADLOTE) == 0) {
                        result.setLoteproduccion(resultSet.getString(CADLOTE));
                    } else if (nomColumna.compareToIgnoreCase(CADPLANTA) == 0) {
                        result.setPlantaproduccion(resultSet.getString(CADPLANTA));
                    } else if (nomColumna.compareToIgnoreCase(CADFECHA) == 0) {
                        result.setFecha(resultSet.getDate(CADFECHA));
                    } else if (nomColumna.compareToIgnoreCase(CADCODIGOS) == 0) {
                        result.setCodigo(resultSet.getString(CADCODIGOS));
                    }
                }

                LOGGER.info("Busqueda encontro : " + result);
                return result;
        }
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Historico> creaHistoricos() {
        List<Historico> historicos = new ArrayList<Historico>();
        final int nHistoricos = 20;
        
        for (int i = 0; i < nHistoricos; i++) {
            Historico h = new Historico();
            h.setContribuyente(" ABCD " + i);
            h.setNombre(" DMY" + i);
            h.setRfc("RFCDMY"+i);
            h.setCantidad(i);
            h.setFecha(new Date());
            h.setPas("PROV"+i);
            h.setFolio(""+i);
            historicos.add(h);
        }

        return historicos;
    }

}
