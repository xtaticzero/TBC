package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.Baja;
import mx.gob.sat.mat.tabacos.modelo.dto.Estatus;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException;

/**
 * Interface que define el comportamiento para el servicio del ABC de proveedores para el sistema de TABACOS
 * @author Salvador Pocteco Saldaña
 */
public interface ProveedorService {

    Proveedor insertProveedor(Proveedor proveedor, RepresentanteLegal representante, Long idTabacalera, RelacionTabProv relacionProveedor)throws ProveedorServiceException;
    
    Proveedor updateProveedor(Proveedor proveedor, RepresentanteLegal representante, Long idTabacalera, RelacionTabProv relacionProveedor) throws ProveedorServiceException;
    
    Proveedor deleteProveedor(Proveedor proveedor, Long idTabacalera, Baja baja, RelacionTabProv relacionProveedor) throws  ProveedorServiceException;

    List<Proveedor> consultaDatosProveedor(Proveedor proveedor)throws ProveedorServiceException;
    
    List<RepresentanteLegal> consultaDatosRepresentante(RepresentanteLegal representante, Proveedor proveedor) throws ProveedorServiceException;
    
    List<Marcas> selectedMarcas() throws ProveedorServiceException;

    List<Estatus> selectedEstatus(Proveedor proveedor)throws ProveedorServiceException;
    
    List<Proveedor> buscaRfc(Proveedor proveedor) throws ProveedorServiceException;

    List<Baja> consultaDatosProveedorBaja(Proveedor proveedor) throws ProveedorServiceException;

    Proveedor updateProveedorRelacional(RelacionTabProv relacionProveedor, Proveedor proveedor, Long idTabacalera, RepresentanteLegal representante, Baja baja, Long idTabacaleraTem) throws ProveedorServiceException;
    
    List<Proveedor> recuperaProveedores(ReportesFiltroBase filtro) throws ProveedorServiceException;
    
    List<Proveedor> buscaProvedoresLikeRfc(String rfc) throws ProveedorServiceException;
    
    Proveedor getProveedorByRfc(String rfc) throws ProveedorServiceException;
    
    List<Proveedor> getProveedoresActivos() throws ProveedorServiceException;
    
    List<Proveedor> getProveedoresTodos() throws ProveedorServiceException;
    
    List<Proveedor> consultaProveedores() throws ProveedorServiceException;
    
    List<Proveedor> consultaProveedoresPorRfc(String rfc) throws ProveedorServiceException;
    
}
