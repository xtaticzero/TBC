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
public enum GrupoEnum {
    //Modulos de aplicativo Bitacora    
    TABACOS_PE ("TabacosEmpleadoPE"),
    TABACOS_PTSC("TabacosContribuyentePTSC"),
    TABACOS_PAC("TabacosContribuyentePAC");
    
    private String grupo;
    
    private GrupoEnum(String grupo) {
        this.grupo = grupo;
    }

    public String getGrupo() {
        return grupo;
    }
    
}
