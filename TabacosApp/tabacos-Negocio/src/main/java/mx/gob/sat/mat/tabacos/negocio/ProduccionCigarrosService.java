package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReporteDesperdiciosDTO;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReporteProduccion;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoRetro;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProduccionServiceException;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public interface ProduccionCigarrosService {

    List<Planta> selectedPlantas(Long idTabacalera) throws ProduccionServiceException;

    ProduccionCigarros guardaProduccion(ProduccionCigarros produccion) throws ProduccionServiceException;

    List<TipoRetro> selectedTipoRetro() throws ProduccionServiceException;

    ProduccionCigarros guardaDestruccion(ProduccionCigarros produccion) throws ProduccionServiceException;

    List<ReporteDesperdiciosDTO> consultaReporteDesperdicios(ReporteDesperdiciosDTO reporteDesperdicios) throws ProduccionServiceException;

    List<ReporteProduccion> consultaReporteProduccion(ReporteProduccion reporteProduccion) throws ProduccionServiceException;

}
