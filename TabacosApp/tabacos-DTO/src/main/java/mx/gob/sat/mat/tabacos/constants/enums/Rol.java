package mx.gob.sat.mat.tabacos.constants.enums;

public enum Rol {
    TABACALERA(0,"TABACALERA"),
    REPRESENTANTE_LEGAL(1,"REPRESENTANTE LEGAL"),
    REPRESENTANTE_OPERATIVO(2,"REPRESENTANTE OPERATIVO"),
    PROVEEDOR(3,"PROVEEDOR");
    
    private Integer key;
    private String descripcion;

    private Rol(Integer key, String descripcion) {
            this.key = key;
            this.descripcion = descripcion;
    }

    public static Rol parse(Integer key) {
            Rol rol = null;
    
        for (Rol item : Rol.values()) {
            if (item.getKey().equals(key)) {
                rol = item;
                break;
            }
        }
        return rol;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getKey() {
        return key;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
