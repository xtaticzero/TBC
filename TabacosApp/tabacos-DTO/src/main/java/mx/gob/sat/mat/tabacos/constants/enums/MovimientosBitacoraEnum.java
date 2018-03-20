/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.constants.enums;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public enum MovimientosBitacoraEnum {

    AUTORIZA_SOLICITUDES(GrupoEnum.TABACOS_PE.getGrupo(), "Autorizar solicitudes", 810),
    AUTORIZAR_MARCAS(GrupoEnum.TABACOS_PE.getGrupo(), "Autorizar marcas", 811),
    REGISTRO_MARCA(GrupoEnum.TABACOS_PE.getGrupo(), "Registro de Marca", 812),
    MODIFICACION_MARCA(GrupoEnum.TABACOS_PE.getGrupo(), "Modificación de Marca", 813),
    BAJA_MARCA(GrupoEnum.TABACOS_PE.getGrupo(), "Baja de Marca", 814),
    REGISTRO_PROVEEDOR(GrupoEnum.TABACOS_PE.getGrupo(), "Registro de Proveedor", 815),
    MODIFICACION_PROVEEDOR(GrupoEnum.TABACOS_PE.getGrupo(), "Modificación de Proveedor", 816),
    BAJA_PROVEEDOR(GrupoEnum.TABACOS_PE.getGrupo(), "Baja de Proveedor", 817),
    DESPERDICIOS_DESTRUCCION_PE(GrupoEnum.TABACOS_PE.getGrupo(), "Desperdicios Y Destrucción", 818),
    CODIGOS_FALSOS_NO_VALIDOS_PE(GrupoEnum.TABACOS_PE.getGrupo(), "Códigos Falsos O No Validos", 819),
    REGISTRO_PRODUCCION(GrupoEnum.TABACOS_PE.getGrupo(), "Registro De Producción", 820),
    SOLICITAR_CODIGOS(GrupoEnum.TABACOS_PTSC.getGrupo(), "Solicitar Codigos", 821),
    DESCARGA_CODIGOS(GrupoEnum.TABACOS_PTSC.getGrupo(), "Descarga de Códigos", 822),
    PRODUCCION_CIGARRILLOS(GrupoEnum.TABACOS_PTSC.getGrupo(), "Producción de Cigarrillos", 823),
    DESPERDICIOS_DESTRUCCION(GrupoEnum.TABACOS_PTSC.getGrupo(), "Desperdicios Y Destrucción", 824),
    CODIGOS_FALSOS_NO_VALIDOS(GrupoEnum.TABACOS_PTSC.getGrupo(), "Códigos Falsos O No Validos", 825),
    SOLICITAR_ALTA_MARCAS(GrupoEnum.TABACOS_PTSC.getGrupo(), "Solicitar Alta De Marcas", 826),
    SOLICITAR_CODIGOS_PAC(GrupoEnum.TABACOS_PAC.getGrupo(), "Solicitar Codigos", 827),
    DESCARGA_CODIGOS_PAC(GrupoEnum.TABACOS_PAC.getGrupo(), "Descarga de Códigos", 828),
    PRODUCCION_CIGARRILLOS_PAC(GrupoEnum.TABACOS_PAC.getGrupo(), "Producción de Cigarrillos", 829),
    DESTRUCCION(GrupoEnum.TABACOS_PAC.getGrupo(), "Destrucción", 830),
    CODIGOS_FALSOS_NO_VALIDOS_PAC(GrupoEnum.TABACOS_PAC.getGrupo(), "Códigos Falsos O No Validos", 831);

    private int idMovimiento;
    private String modulo;
    private String grupo;
    private String movimiento;

    private MovimientosBitacoraEnum(String grupo, String movimiento, int idMovimiento) {
        this.idMovimiento = idMovimiento;
        this.modulo = "Tabacos";
        this.grupo = grupo;
        this.movimiento = movimiento;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

}
