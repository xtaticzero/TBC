/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesMarcas;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ReporteDaoException;

/**
 *
 * @author emmanuel
 */
public interface MarcaReporteDao {

    ReportesMarcas getAltaMarcas(ReportesFiltroBase filtro) throws ReporteDaoException;

    ReportesMarcas getModificacionMarcas(ReportesFiltroBase filtro) throws ReporteDaoException;

    ReportesMarcas getBajaMarcas(ReportesFiltroBase filtro) throws ReporteDaoException;

}
