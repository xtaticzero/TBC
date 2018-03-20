/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusCargador;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusResolucion;
import mx.gob.sat.mat.tabacos.modelo.dao.RangoFolioDao;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.exceptions.RangoFolioDaoException;
import mx.gob.sat.mat.tabacos.negocio.ValidadorRangosService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
@Service
@Qualifier("validadorRangosService")
public class ValidadorRangosServiceImpl implements ValidadorRangosService {

    @Autowired
    private RangoFolioDao rangoFolioDaoImpl;

    private static final String ERROR_CONSULTA_RANGOS = "ERROR AL CONSULTAR LOS RANGOS";

    @Override
    public List<RangoFolio> generarRangosProduccion(String rfcTabacalera, String rfcProveedor, List<RangoFolio> lstACargar) throws RangosException {
        validarInterseccion(lstACargar);

        List<RangoFolio> lstResolucion = getListaResolucion(rfcTabacalera, rfcProveedor);
        validarExistencia(lstACargar, lstResolucion);
        List<RangoFolio> lstRangos = crearLista(lstACargar, lstResolucion);

        List<RangoFolio> lstProduccion = getListaProduccion(rfcTabacalera, rfcProveedor);
        lstRangos = crearListaComp(lstRangos, lstProduccion);
        lstRangos = eliminarDuplicados(lstRangos);

        if (lstRangos.isEmpty()) {
            throw new RangosException(RANGO_YA_CARGADO);
        }
        return lstRangos;
    }

    private List<RangoFolio> getListaResolucion(String rfcTabacalera, String rfcProveedor) throws RangosException {
        try {
            boolean isEmpty = ((rfcProveedor != null && rfcProveedor.isEmpty()) || (rfcProveedor == null));

            return (!isEmpty) ? rangoFolioDaoImpl.getRangosResolucion(rfcTabacalera, rfcProveedor, EstatusResolucion.GENERADO, EstatusCargador.LISTO)
                    : rangoFolioDaoImpl.getRangosResolucion(rfcTabacalera, EstatusResolucion.GENERADO, EstatusCargador.LISTO);
        } catch (RangoFolioDaoException e) {
            throw new RangosException(ERROR_CONSULTA_RANGOS, e);
        }
    }

    private List<RangoFolio> getListaProduccion(String rfcTabacalera, String rfcProveedor) throws RangosException {
        try {
            boolean isEmpty = ((rfcProveedor != null && rfcProveedor.isEmpty()) || (rfcProveedor == null));
            return (!isEmpty) ? rangoFolioDaoImpl.getRangos(rfcTabacalera, rfcProveedor) : rangoFolioDaoImpl.getRangos(rfcTabacalera);
        } catch (RangoFolioDaoException e) {
            throw new RangosException(ERROR_CONSULTA_RANGOS, e);
        }
    }

    @Override
    public List<RangoFolio> generarRangosProduccion(String rfcTabacalera, List<RangoFolio> lstACargar) throws RangosException {
        return generarRangosProduccion(rfcTabacalera, null, lstACargar);
    }

    @Override
    public List<RangoFolio> generarRangosInvalidos(String rfcTabacalera, String rfcProveedor, List<RangoFolio> lstACargar) throws RangosException {
        validarInterseccion(lstACargar);

        List<RangoFolio> lstProduccion = getListaProd(rfcTabacalera, rfcProveedor);
        validarExistencia(lstACargar, lstProduccion);
        List<RangoFolio> lstRangos = crearLista(lstACargar, lstProduccion);

        List<RangoFolio> lstInvalidos = getListaInvalido(rfcTabacalera, rfcProveedor);
        lstRangos = crearListaComp(lstRangos, lstInvalidos);
        lstRangos = eliminarDuplicados(lstRangos);

        if (lstRangos.isEmpty()) {
            throw new RangosException(RANGO_YA_CARGADO);
        }
        return lstRangos;
    }

    private List<RangoFolio> getListaProd(String rfcTabacalera, String rfcProveedor) throws RangosException {
        try {
            boolean isEmpty = ((rfcProveedor != null && rfcProveedor.isEmpty()) || (rfcProveedor == null));

            return (!isEmpty) ? rangoFolioDaoImpl.getRangosProduccion(rfcTabacalera, rfcProveedor) : rangoFolioDaoImpl.getRangosProduccion(rfcTabacalera);
        } catch (RangoFolioDaoException e) {
            throw new RangosException(ERROR_CONSULTA_RANGOS, e);
        }
    }

    private List<RangoFolio> getListaInvalido(String rfcTabacalera, String rfcProveedor) throws RangosException {
        try {
            boolean isEmpty = ((rfcProveedor != null && rfcProveedor.isEmpty()) || (rfcProveedor == null));

            return (!isEmpty) ? rangoFolioDaoImpl.getRangosInvalidos(rfcTabacalera, rfcProveedor) : rangoFolioDaoImpl.getRangosInvalidos(rfcTabacalera);
        } catch (RangoFolioDaoException e) {
            throw new RangosException(ERROR_CONSULTA_RANGOS, e);
        }
    }

    @Override
    public List<RangoFolio> generarRangosInvalidos(String rfcTabacalera, List<RangoFolio> lstACargar) throws RangosException {
        return generarRangosInvalidos(rfcTabacalera, null, lstACargar);
    }

    private void validarInterseccion(List<RangoFolio> lstACargar) throws RangosException {
        if (lstACargar != null && !lstACargar.isEmpty()) {
            List<RangoFolio> lstRangos = new ArrayList<RangoFolio>(lstACargar);
            RangoFolio rcs;

            while (!lstRangos.isEmpty()) {
                rcs = lstRangos.remove(0);
                if (rcs.getFolioInicial() > rcs.getFolioFinal()) {
                    throw new RangosException(INCORRECTO_INFERIOR_SUPERIOR);
                }

                for (RangoFolio r : lstRangos) {
                    if (RangoFolio.intersectan(rcs, r)) {
                        throw new RangosException(INTERSECCION);
                    }
                }
            }
        } else {
            throw new RangosException("LA LISTA A CARGAR ESTA VACIA");
        }
    }

    private void validarExistencia(List<RangoFolio> lstACargar, List<RangoFolio> lstComp) throws RangosException {
        if (lstComp != null && !lstComp.isEmpty()) {
            List<RangoFolio> lstRangos = new ArrayList<RangoFolio>(lstACargar);
            RangoFolio rcs;

            while (!lstRangos.isEmpty()) {
                rcs = lstRangos.remove(0);
                validarExistenciaSub(rcs, lstComp);
            }
        } else {
            throw new RangosException(NO_PERTENECE);
        }
    }

    private void validarExistenciaSub(RangoFolio rcs, List<RangoFolio> lstComp) throws RangosException {
        boolean infExiste = false;
        boolean supExiste = false;
        for (RangoFolio r : lstComp) {
            if (existeEnRango(rcs.getFolioInicial(), r)) {
                infExiste = true;
            }
            if (existeEnRango(rcs.getFolioFinal(), r)) {
                supExiste = true;
            }
        }
        if (!infExiste || !supExiste) {
            throw new RangosException(NO_PERTENECE);
        }
    }

    private List<RangoFolio> crearListaComp(List<RangoFolio> lstACargar, List<RangoFolio> lstComp) throws RangosException {
        List<RangoFolio> rangos = new ArrayList<RangoFolio>();
        if (lstComp != null && !lstComp.isEmpty()) {
            List<RangoFolio> ran1;
            List<RangoFolio> lstRangos = new ArrayList<RangoFolio>(lstACargar);
            RangoFolio rcs;
            while (!lstRangos.isEmpty()) {
                rcs = lstRangos.remove(0);
                ran1 = recursivo(rcs, lstComp);
                rangos.addAll(ran1);
            }
        } else {
            rangos.addAll(lstACargar);
        }
        return rangos;
    }

    private List<RangoFolio> recursivo(RangoFolio rango, List<RangoFolio> lstComp) throws RangosException {
        List<RangoFolio> rangos = new ArrayList<RangoFolio>();
        List<RangoFolio> rngs1 = new ArrayList<RangoFolio>();
        List<RangoFolio> rngs2 = new ArrayList<RangoFolio>();
        RangoFolio rf;
        RangoFolio rf1;
        RangoFolio rf2;

        boolean bolEntro = false;
        for (int i = 0; i < lstComp.size(); i++) {
            rf = lstComp.get(i);
            Long inferior;
            Long superior;
            if (existeEnRango(rf, rango)) {
                inferior = rango.getFolioInicial();
                superior = rf.getFolioInicial() - 1;
                if (superior >= inferior) {
                    rf1 = new RangoFolio(rango.getIdRangoFolios(), inferior, superior, rango.getIdProdCigarro(), rango.getIdResolucion(), rango.getIdSolicitud());
                    rngs1 = recursivo(rf1, lstComp);
                }

                inferior = rf.getFolioFinal() + 1;
                superior = rango.getFolioFinal();
                if (inferior <= superior) {
                    rf2 = new RangoFolio(rango.getIdRangoFolios(), inferior, superior, rango.getIdProdCigarro(), rango.getIdResolucion(), rango.getIdSolicitud());
                    rngs2 = recursivo(rf2, lstComp);
                }
                bolEntro = true;
            } else if (existeEnRango(rf.getFolioInicial(), rango) && !existeEnRango(rf.getFolioFinal(), rango)) {

                inferior = rango.getFolioInicial();
                superior = rf.getFolioInicial() - 1;
                if (superior >= inferior) {
                    rf1 = new RangoFolio(rango.getIdRangoFolios(), inferior, superior, rango.getIdProdCigarro(), rango.getIdResolucion(), rango.getIdSolicitud());
                    rngs1 = recursivo(rf1, lstComp);
                }
                bolEntro = true;
            } else if (!existeEnRango(rf.getFolioInicial(), rango) && existeEnRango(rf.getFolioFinal(), rango)) {

                inferior = rf.getFolioFinal() + 1;
                superior = rango.getFolioFinal();
                if (inferior <= superior) {
                    rf2 = new RangoFolio(rango.getIdRangoFolios(), inferior, superior, rango.getIdProdCigarro(), rango.getIdResolucion(), rango.getIdSolicitud());
                    rngs2 = recursivo(rf2, lstComp);
                }
                bolEntro = true;
            } else {
                if (i == (lstComp.size() - 1) && !bolEntro) {
                    rangos.add(rango);
                }
            }

            if (i == (lstComp.size() - 1)) {
                rangos.addAll(rngs1);
                rangos.addAll(rngs2);
            }
        }

        return rangos;
    }

    private List<RangoFolio> crearLista(List<RangoFolio> lstACargar, List<RangoFolio> lstComp) throws RangosException {
        List<RangoFolio> rangos = new ArrayList<RangoFolio>();
        if (lstComp != null && !lstComp.isEmpty()) {
            List<RangoFolio> lstRangos = new ArrayList<RangoFolio>(lstACargar);
            RangoFolio rcs;
            RangoFolio rf;

            while (!lstRangos.isEmpty()) {
                rcs = lstRangos.remove(0);

                boolean continuar = false;
                for (RangoFolio r : lstComp) {

                    if (existeEnRango(rcs.getFolioInicial(), r)) {
                        if (existeEnRango(rcs.getFolioFinal(), r)) {
                            rf = new RangoFolio(r.getIdRangoFolios(), rcs.getFolioInicial(), rcs.getFolioFinal(), r.getIdProdCigarro(), r.getIdResolucion(), r.getIdSolicitud());
                            rangos.add(rf);
                            break;
                        } else {
                            rf = new RangoFolio(r.getIdRangoFolios(), rcs.getFolioInicial(), r.getFolioFinal(), r.getIdProdCigarro(), r.getIdResolucion(), r.getIdSolicitud());
                            rangos.add(rf);
                            continuar = true;
                            continue;
                        }
                    }

                    if (continuar) {
                        if (rcs.getFolioFinal() < r.getFolioInicial()) {
                            break;
                        }

                        Long valSup = rcs.getFolioFinal();
                        if (!(existeEnRango(rcs.getFolioFinal(), r))) {
                            valSup = r.getFolioFinal();
                        }
                        rf = new RangoFolio(r.getIdRangoFolios(), r.getFolioInicial(), valSup, r.getIdProdCigarro(), r.getIdResolucion(), r.getIdSolicitud());
                        rangos.add(rf);
                    }
                }
            }
        } else {
            rangos.addAll(lstACargar);
        }

        return rangos;
    }

    private List<RangoFolio> eliminarDuplicados(List<RangoFolio> lstACargar) {
        List<RangoFolio> rangos = new ArrayList<RangoFolio>();
        List<RangoFolio> lstRangos = new ArrayList<RangoFolio>(lstACargar);
        RangoFolio rcs;

        while (!lstRangos.isEmpty()) {
            rcs = lstRangos.remove(0);
            loop:
            {
                for (RangoFolio r : lstRangos) {
                    if (esIgual(rcs, r)) {
                        break loop;
                    }
                }
                rangos.add(rcs);
            }
        }
        return rangos;
    }

    private boolean existeEnRango(Long a, RangoFolio r) {
        return r.getFolioInicial() <= a && a <= r.getFolioFinal();
    }

    private boolean existeEnRango(RangoFolio a, RangoFolio b) {
        return (existeEnRango(a.getFolioInicial(), b) && existeEnRango(a.getFolioFinal(), b));
    }

    private boolean esIgual(RangoFolio a, RangoFolio b) {
        return (a.getFolioInicial().equals(b.getFolioInicial()) && a.getFolioFinal().equals(b.getFolioFinal()));
    }
}
