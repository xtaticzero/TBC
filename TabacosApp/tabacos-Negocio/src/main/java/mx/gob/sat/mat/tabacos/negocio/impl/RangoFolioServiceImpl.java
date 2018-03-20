/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.sat.mat.tabacos.constants.enums.EstadoEnum;
import mx.gob.sat.mat.tabacos.modelo.dao.RangoFolioDao;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.modelo.exceptions.RangoFolioDaoException;
import mx.gob.sat.mat.tabacos.negocio.RangoFolioService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangoFolioServiceExcepcion;
import static mx.gob.sat.mat.tabacos.negocio.impl.TratamientoRangoFolioServiceImpl.LOGGER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author
 */
@Service
@Qualifier("rangoFolioService")
public class RangoFolioServiceImpl implements RangoFolioService {

    @Autowired
    @Qualifier("rangoFolioDaoImpl")
    private RangoFolioDao rangoFolioDao;

    /**
     *
     * @param folioInicial
     * @param folioFinal
     * @param idResolucion
     * @return
     * @throws RangoFolioServiceExcepcion
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    @Override
    public List<RangoFolio> validaRango(long folioInicial, long folioFinal, Long idResolucion) throws RangoFolioServiceExcepcion {
        List<RangoFolio> rango = null;

        try {
            rango = rangoFolioDao.validaRango(folioInicial, folioFinal, idResolucion);
        } catch (RangoFolioDaoException ex) {
            Logger.getLogger(RangoFolioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RangoFolioServiceExcepcion(ex);
        }
        return rango;
    }

    /**
     *
     * @param rfcTabacalera
     * @param folioInicial
     * @param folioFinal
     * @return
     * @throws RangoFolioServiceExcepcion
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    @Override
    public RangoFolio validaRangoFolioPorRfc(String rfcTabacalera, long folioInicial, long folioFinal) throws RangoFolioServiceExcepcion {
        try {
            return rangoFolioDao.validaRangoFolioPorRfc(rfcTabacalera, folioInicial, folioFinal);
        } catch (RangoFolioDaoException ex) {
            Logger.getLogger(RangoFolioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RangoFolioServiceExcepcion(ex);
        }
    }

    /**
     *
     * @param rangoFolio
     * @throws RangoFolioServiceExcepcion
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    @Override
    public void guardar(RangoFolio rangoFolio) throws RangoFolioServiceExcepcion {

        try {
            rangoFolioDao.guardar(rangoFolio);
        } catch (RangoFolioDaoException ex) {
            Logger.getLogger(RangoFolioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RangoFolioServiceExcepcion(ex);
        }
    }

    public RangoFolioDao getRangoFolioDao() {
        return rangoFolioDao;
    }

    public void setRangoFolioDao(RangoFolioDao rangoFolioDao) {
        this.rangoFolioDao = rangoFolioDao;
    }

    /*2*/
    /**
     *
     * @param rangoFoliosCanceladosList
     * @param folioInicial
     * @param folioFinal
     * @return
     * @throws RangoFolioServiceExcepcion
     */
    @Override
    public List<RangoFolio> obtenerRangoFoliosListActivosCancelados(final List<RangoFolio> rangoFoliosCanceladosList,
            final long folioInicial,
            final long folioFinal) throws RangoFolioServiceExcepcion {

        List<RangoFolio> rangoFoliosActivosCanceladosList = new ArrayList<RangoFolio>();
        List<RangoFolio> rangoFoliosActivosList = null;

        if (rangoFoliosCanceladosList == null || rangoFoliosCanceladosList.isEmpty()) {
            // Si no hay rangos cancelados solo se crea uno desde el folio inicial al folio final.
            RangoFolio rangoFolios = new RangoFolio();
            rangoFolios.setFolioInicial(folioInicial);
            rangoFolios.setFolioFinal(folioFinal);
            rangoFolios.setEstado(EstadoEnum.ACTIVO.getId());
            rangoFoliosActivosCanceladosList.add(rangoFolios);
        } else {
            Collections.sort(rangoFoliosCanceladosList);

            if (validarRangoFoliosCanceladosEnSerie(rangoFoliosCanceladosList, folioInicial, folioFinal)) {
                // Si ningun rango de folios se intersecta se procede a crear los rangos activos
                rangoFoliosActivosList
                        = obtenerRangosActivosDeRangosCancelados(rangoFoliosCanceladosList, folioInicial, folioFinal);

            } else {
                throw new RangoFolioServiceExcepcion("Error el rango de folios cancelados,"
                        + "no se ajusta el rango de folios de la serie o los rangos se intersectan");
            }
            rangoFoliosActivosCanceladosList.addAll(rangoFoliosActivosList);
            rangoFoliosActivosCanceladosList.addAll(rangoFoliosCanceladosList);
        }

        LOGGER.info("TratamientoRangoFolioServiceImpl.Salida de obtenerRangoFoliosListActivosCancelados");

        return rangoFoliosActivosCanceladosList;
    }

    /*
     * Valida que la lista de rangoFoliosCancelados no se encuentre fuera de los limites de los folios y
     * que no se intersecten.
     */

    /*3*/
    private boolean validarRangoFoliosCanceladosEnSerie(final List<RangoFolio> rangoFoliosCanceladosList,
            final long folioInicial,
            final long folioFinal) throws RangoFolioServiceExcepcion {

        List<RangoFolio> rangoFoliosCanceladosAux = new ArrayList<RangoFolio>();
        rangoFoliosCanceladosAux.addAll(rangoFoliosCanceladosList);
        boolean resultado = Boolean.TRUE;
        int contador = 1;
        for (RangoFolio rangoFoliosCancelado : rangoFoliosCanceladosList) {
            // Se verifica que el rango este dentro de los limites de la serie
            if (rangoFoliosCancelado.getFolioInicial() >= folioInicial
                    && rangoFoliosCancelado.getFolioFinal() <= folioFinal) {

                // Se valida que el rango de folios cancelado no se intersecte con otro rango
                for (int i = contador; i < rangoFoliosCanceladosAux.size(); i++) {
                    RangoFolio rangoFoliosCanceladoAux = rangoFoliosCanceladosAux.get(i);
                    if (RangoFolio.intersectan(rangoFoliosCancelado, rangoFoliosCanceladoAux)) {
                        // Los rangos no pueden intersectarse
                        resultado = Boolean.FALSE;
                    }
                }
            } else {
                // Los rangos no pueden estar fuera del los limites marcados
                resultado = Boolean.FALSE;
            }
            contador++;
        }
        return resultado;
    }

    /*
     * Recibe un lista de rango de folios cancelados y regresa una lista con los rangos de folios activos y cancelados
     * dentro de los limites recibidos (foliInicial y folioFinal).
     */

    /*4*/
    private List<RangoFolio> obtenerRangosActivosDeRangosCancelados(final List<RangoFolio> rangoFoliosCanceladosList,
            final long folioInicial,
            final long folioFinal) throws RangoFolioServiceExcepcion {
        long folioActual = folioInicial;
        List<RangoFolio> rangoFoliosActivosList = new ArrayList<RangoFolio>();

        Collections.sort(rangoFoliosCanceladosList);

        for (RangoFolio actualRangoFoliosCancelado : rangoFoliosCanceladosList) {

            if (folioActual == actualRangoFoliosCancelado.getFolioInicial()) {
                folioActual = actualRangoFoliosCancelado.getFolioFinal() + 1;

            } else if (folioActual < actualRangoFoliosCancelado.getFolioInicial()) {
                RangoFolio rangoActivo = new RangoFolio();
                rangoActivo.setEstado(EstadoEnum.ACTIVO.getId());
                rangoActivo.setFolioInicial(folioActual);
                rangoActivo.setFolioFinal(actualRangoFoliosCancelado.getFolioInicial() - 1);

                rangoFoliosActivosList.add(rangoActivo);

                folioActual = actualRangoFoliosCancelado.getFolioFinal() + 1;
            } else {
                throw new RangoFolioServiceExcepcion("Error fatal al crear los rangos de la serie");
            }
        }

        //Al terminar de recorrerla lista de folios cancelados puede que haya un rango de folios activo
        if (folioActual <= folioFinal) {
            RangoFolio rangoActivo = new RangoFolio();
            rangoActivo.setEstado(EstadoEnum.ACTIVO.getId());
            rangoActivo.setFolioInicial(folioActual);
            rangoActivo.setFolioFinal(folioFinal);
            rangoFoliosActivosList.add(rangoActivo);
        } else if (folioActual > (folioFinal + 1)) {
            throw new RangoFolioServiceExcepcion("Error fatal al final de la serie, al crear los rangos");
        }

        return rangoFoliosActivosList;
    }

    @Override
    public Map<String, Object> validaRangoSerie(String rfcTabacalera, List<RangoFolio> rangos) throws RangoFolioServiceExcepcion {
        Map<String, Object> retorno = new HashMap<String, Object>();
        List<Codigo> codigosInvalidos = new ArrayList<Codigo>();

        Codigo codigoInv;
        RangoFolio folio;
        boolean valido = true;
        if (rangos != null) {
            for (RangoFolio rango : rangos) {
                folio = validaRangoFolioPorRfc(rfcTabacalera, rango.getFolioInicial(), rango.getFolioFinal());
                if (folio == null) {
                    valido = false;
                    break;
                } else {
                    codigoInv = new Codigo();
                    codigoInv.setFolioInicial("" + rango.getFolioInicial());
                    codigoInv.setFolioFinal("" + rango.getFolioFinal());
                    codigoInv.setIdRangoFolio(folio.getIdRangoFolios());
                    codigosInvalidos.add(codigoInv);
                }
            }

        }
        retorno.put("valido", valido);
        retorno.put("codigosInvalidos", codigosInvalidos);

        return retorno;

    }
    
    /**
     *
     * @param rangoFoliosListAux
     * @param produccion
     * @return rangoFoliosList
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.RangoFolioServiceExcepcion
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<RangoFolio> guardarRangosFolios(List<RangoFolio> rangoFoliosListAux, ProduccionCigarros produccion) throws RangoFolioServiceExcepcion {
        List<RangoFolio> rangoFoliosList = new ArrayList<RangoFolio>();
        RangoFolio rangoF = new RangoFolio();
        try {
            Long idProduccion = produccion.getIdProdCigarro();

            for (RangoFolio rangosFolio : rangoFoliosListAux) {
                rangosFolio.setIdProdCigarro(idProduccion);
                rangoFolioDao.guardarRangosFolios(rangosFolio, produccion);
                rangoFoliosList.add(rangoF);
            }
        } catch (RangoFolioDaoException e) {
            LOGGER.error("ERROR - Al insertar los Rangos Folios " + e.getMessage(), e);
            throw new RangoFolioServiceExcepcion(e);
        }
        return rangoFoliosList;
    }

}
