package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.mat.tabacos.constants.enums.Rol;

public class ValidarAccesoRespuesta implements Serializable {

    @SuppressWarnings("compatibility:-2185253260904668901")
    private static final long serialVersionUID = -1781622766618519685L;

    private Rol rol;
    private List<Proveedor> proveedores;
    private List<Tabacalera> tabacaleras;

    public static ValidarAccesoRespuesta crearRespuestaRepresentanteLegalOperativo(Tabacalera tabacalera) {
        ValidarAccesoRespuesta validarAccesoRespuesta = new ValidarAccesoRespuesta();
        validarAccesoRespuesta.setRol(Rol.REPRESENTANTE_OPERATIVO);
        List<Tabacalera> tabacaleras = new ArrayList<Tabacalera>();
        tabacaleras.add(tabacalera);
        validarAccesoRespuesta.setTabacaleras(tabacaleras);
        return validarAccesoRespuesta;
    }

    public static ValidarAccesoRespuesta crearRespuestaTabacalera(Tabacalera tabacalera, List<Proveedor> proveedores) {
        ValidarAccesoRespuesta validarAccesoRespuesta = new ValidarAccesoRespuesta();
        validarAccesoRespuesta.setRol(Rol.TABACALERA);
        List<Tabacalera> tabacaleras = new ArrayList<Tabacalera>();
        tabacaleras.add(tabacalera);
        validarAccesoRespuesta.setTabacaleras(tabacaleras);
        validarAccesoRespuesta.setProveedores(proveedores);
        return validarAccesoRespuesta;
    }

    public static ValidarAccesoRespuesta crearRespuestaProveedor(Proveedor proveedor, List<Tabacalera> tabacaleras) {
        ValidarAccesoRespuesta validarAccesoRespuesta = new ValidarAccesoRespuesta();
        validarAccesoRespuesta.setRol(Rol.PROVEEDOR);
        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        proveedores.add(proveedor);
        validarAccesoRespuesta.setTabacaleras(tabacaleras);
        validarAccesoRespuesta.setProveedores(proveedores);
        return validarAccesoRespuesta;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setTabacaleras(List<Tabacalera> tabacaleras) {
        this.tabacaleras = tabacaleras;
    }

    public List<Tabacalera> getTabacaleras() {
        return tabacaleras;
    }
}
