/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.EstadoEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.negocio.CodigosFalsoseInvalidosService;
import mx.gob.sat.mat.tabacos.negocio.TratamientoRangoFolioService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.BusinessException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CodigosFalsoseInvalidosException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author MMMF
 */
@Service
@Qualifier("tratamientoRangoFolioService")
public class TratamientoRangoFolioServiceImpl implements TratamientoRangoFolioService {

    protected static final Logger LOGGER = Logger.getLogger(TratamientoRangoFolioServiceImpl.class);

    @Autowired
    private CodigosFalsoseInvalidosService codigosFalsoseInvalidos;

    
    @Override
    public boolean validaExistenciaCodigosInvalidos(
            RangoFolio rango,
            String rfc) throws BusinessException {
        boolean result = false;
        List<Codigo> codigosInvalidos;
        String cadDatos = "";

        try {
            //recuperar las solicitudes pertenecientes a la tabacalera

            //buscar en la tabla codigo invalido para ver si existe alguno guardado asi
            codigosInvalidos = this.codigosFalsoseInvalidos.buscarCodigoInvPorRangoFolio(rfc, rango.getFolioInicial(), rango.getFolioFinal());
            cadDatos = cadDatos.concat("PASO A INVALIDOS .-.\n");
            if (codigosInvalidos != null && !codigosInvalidos.isEmpty()) {
                //existe en BD, no se requiere guardarlo nuevamente
                for (Codigo cod : codigosInvalidos) {
                    cadDatos = cadDatos.concat(
                            "CODIGO_INVALIDO: " + cod.getIdCodigoFalso()
                            + " num: " + cod.getNumeroCodigo()
                            + "IDRango: " + cod.getIdRangoFolio()
                            + " FI: " + cod.getFolioInicial()
                            + " FF: " + cod.getFolioFinal()
                            + " Justificacion: " + cod.getJustificacion() + "\n");
                }
                result = true;
            }
        } catch (CodigosFalsoseInvalidosException e) {
            throw new BusinessException("Error al consultar la tabla resolucionDao o rangoFolioDao , [validaRango] en DAO"
                    + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public long obtenerNumeroFoliosCancelados(final List<RangoFolio> rangoFoliosCancelados) {
        long numFolios = 0;
        if (rangoFoliosCancelados != null && !rangoFoliosCancelados.isEmpty()) {
            for (RangoFolio rangoFolios : rangoFoliosCancelados) {
                if (rangoFolios.getEstado() == EstadoEnum.CANCELADO.getId()) {
                    numFolios += (rangoFolios.getFolioFinal() - rangoFolios.getFolioInicial() + 1);
                }
            }
        }
        return numFolios;
    }
}
