package mx.gob.sat.mat.tabacos.persistence;

public interface Fields {
    
    String RFC = "RFC";
    String ID_ESTATUS = "IDESTATUS";
    String ID_TABACALERA = "IDTABACALERA";
    String ID_COMPANIA = "IDN";
    String STATUS = "STATUS";
    String NOMBRE = "NOMBRE";
    String RAZON_SOCIAL = "RAZONSOCIAL";
    String RESOLUCION = "RESOLUCION";
    String MOVIMIENTO = "MOVIMIENTO";
    String DOMICILIO = "DOMICILIO";
    String FECINICIO = "FECINICIO";
    String ESTATUS = "ESTATUS";
    String RUTAARCHIVO = "RUTAARCHIVO";
    
    String VALUE = "VALUE";
    String LABEL = "LABEL";
        
    //MArcas Resolucion
    String IDTABACALERA = ID_TABACALERA;
    String CLAVE_MARCA = "CLAVE";
    String MARCA_FECINICIO = FECINICIO;
    String MARCA_FECFIN = "FECFIN";
    String MARCA_IDESTATUS = ID_ESTATUS;
    String MARCA_ESTATUS = ESTATUS;
    String MARCA_SPAUTORIZA = "SPAUTORIZA";
    String MARCA_DESCSPAUTORIZA = "DESCSPAUTORIZA";
    String MARCA_RUTAARCHIVO = RUTAARCHIVO;
    
    //TABLA TABACALERA
    String FIELD_LIKE_IDTABACALERA = ID_TABACALERA;
    String FIELD_LIKE_RFC = RFC;
    String FIELD_LIKE_RAZONSOCIAL = RAZON_SOCIAL;
    String FIELD_LIKE_IDESTATUS = ID_ESTATUS;
    String FIELD_LIKE_ESTATUS = ESTATUS;
    String FIELD_LIKE_CORREOE = "CORREOE";
    String FIELD_LIKE_DOMICILIO = DOMICILIO;
    String FIELD_TABACALERA_RAZON_SOCIAL_ALIAS = "RAZONSOCIALTAB";

    //TABLA TBCT_PROVEEDOR
    String FIELD_PROVEEDOR_ID = "IDPROVEEDOR";
    String FIELD_PROVEEDOR_RFC = RFC;
    String FIELD_PROVEEDOR_RAZON_SOCIAL = RAZON_SOCIAL;
    String FIELD_PROVEEDOR_DOMICILIO = DOMICILIO;
    String FIELD_PROVEEDOR_FECHA_REGISTRO = "FECREGISTRO";
    String FIELD_PROVEEDOR_FECHA_CAPTURA = "FECCAPTURA";
    String FIELD_PROVEEDOR_ID_ESTATUS = ID_ESTATUS;
    String FIELD_PROVEEDOR_NOMESTATUS = "NOMESTATUS";
    String FIELD_PROVEEDOR_RAZON_SOCIAL_ALIAS = "RAZONSOCIALPROV";
    
    //TABLA TBCT_MARCA
    String FIELD_MARCAS_ID = "IDMARCA";
    String FIELD_MARCAS_NOMBRE_MARCA = "NOMMARCA";
    String FIELD_MARCAS_TABACALERA_ID = ID_TABACALERA;

    //TABLA RFCC_PAIS (Corresponde a otro JNDI)
    String FIELD_PAIS_ID = "IDPAIS";
    String FIELD_PAIS_DESC_CORTA = "DESCORTA";
    String FIELD_PAIS_DESC_LARGA = "DESLARGA";
    String FIELD_PAIS_CLAVEPAIS="CLAVEPAIS";
    
    

    //TABLA TBCC_ESTATUS
    String FIELD_ESTATUS_ID = ID_ESTATUS;
    String FIELD_ESTATUS_NOMBRE = "NOMESTATUS";

    //TABLA TBCT_PLANTA
    String FIELD_PLANTA_ID = "IDPLANTA";
    String FIELD_PLANTA_NOMBRE = "NOMPLANTA";

    //TABLA TBCT_BAJA
    String FIELD_BAJA_ID = "IDBAJA";
    String FIELD_BAJA_TABACALERA_ID = ID_TABACALERA;
    String FIELD_BAJA_PROVEEDOR_ID = "IDPROVEEDOR";
    String FIELD_BAJA_MARCA_ID = "IDMARCA";
    String FIELD_BAJA_DESC_MOTIVO_BAJA = "DESCMOTIVOBAJA";
    String FIELD_BAJA_FECHA_REGISTRO = "FECREGISTRO";
    String FIELD_BAJA_FECHA_BAJA = "FECBAJA";
    
    //TABLA TBCC_TIPORETRO
    String FIELD_TIPO_RETRO_ID = "IDTIPORETRO";
    String FIELD_TIPO_RETRO_DESCTIPORETRO = "DESCTIPORETRO";
    
    String FIELD_COMPANIA_ID = ID_COMPANIA;
    String FIELD_COMPANIA_PROVEEDOR_ID = "PROV_IDN";
    String FIELD_COMPANIA_STATUS = STATUS;
    String FIELD_COMPANIA_NOMBRE = NOMBRE;
    String FIELD_COMPANIA_RFC = RFC;
    String FIELD_COMPANIA_BOID = "BOID";
    String FIELD_COMPANIA_TIPO = "TIPO";
    String FIELD_COMPANIA_AUTORIZACION = "AUTORIZACION";
    String FIELD_COMPANIA_FECHA = "FECHA";
    String FIELD_COMPANIA_EMAIL = "CORREO";
    String FIELD_COMPANIA_TELEFONO = "TELEFONO";
    String FIELD_COMPANIA_TELEFONO2 = "TELEFONO2";
    String FIELD_COMPANIA_DOM = DOMICILIO;
    
    String FIELD_PRODUCTO_ID = ID_COMPANIA;
    String FIELD_PRODUCTO_COMPANIA_ID = "COMP_IDN";
    String FIELD_PRODUCTO_STATUS = STATUS;
    String FIELD_PRODUCTO_MARCA = "MARCA";
    String FIELD_PRODUCTO_CODIGO_MARCA = "CMARCA";
    String FIELD_PRODUCTO_SUBMARCA = "SUBMARCA";
    String FIELD_PRODUCTO_CODIGO_SUBMARCA = "CSUBMARCA";
    String FIELD_PRODUCTO_CANTIDAD = "CANTIDAD";
    String FIELD_PRODUCTO_CODIGO_CANTIDAD = "CCANTIDAD";
    String FIELD_PRODUCTO_DESDE = "DESDE";
    String FIELD_PRODUCTO_HASTA = "HASTA";
    String FIELD_PRODUCTO_TIPO = "TIPO";
    String FIELD_PRODUCTO_FECHA = "FECHA";
    String FIELD_PRODUCTO_ORIGEN_ID = "ORIG_IDN";
    String FIELD_PRODUCTO_DESTINO_ID = "DEST_IDN";

    String FIELD_CENTRO_ID = ID_COMPANIA;
    String FIELD_CENTRO_COMPANIA_ID = "COMP_IDN";
    String FIELD_CENTRO_STATUS = STATUS;
    String FIELD_CENTRO_NOMBRE = NOMBRE;
    String FIELD_CENTRO_DIRECCION = "DIRECCION";

    String FIELD_MAQUINA_ID = ID_COMPANIA;
    String FIELD_MAQUINA_CENTRO_ID = "CENTRO_IDN";
    String FIELD_MAQUINA_STATUS = STATUS;
    String FIELD_MAQUINA_NUMERO = "NUMERO";

    String FIELD_ORIGEN_ID = ID_COMPANIA;
    String FIELD_ORIGEN_STATUS = STATUS;
    String FIELD_ORIGEN_NOMBRE = NOMBRE;

    String FIELD_DESTINO_ID = ID_COMPANIA;
    String FIELD_DESTINO_STATUS = STATUS;
    String FIELD_DESTINO_NOMBRE = NOMBRE;

    String FIELD_PRODUCCION_CANTIDAD_PRODUCCION = "CANTPRODUCIDA";
    String FIELD_PRODUCCION_PAIS_ORIGEN = "IDPAISORIGEN";
    String FIELD_PRODUCCION_DESCRIPCION_MAQUINA = "DESCMAQUINAPROD";
    String FIELD_PRODUCCION_LOTE = "NUMLOTE";
    String FIELD_PRODUCCION_FECHA_PRODUCCION = "FECPRODUCCION";
    String FIELD_PRODUCCION_CANTIDAD_CIGARROS = "CANTCIGARROS";
    String FIELD_PRODUCCION_ID_PRODUCCION = "IDPRODCIGARRO";
    String FIELD_PRODUCCION_DESCRIPCION_PAIS = "DESCPAISORIGEN";
    String FIELD_PRODUCCION_LINEA_PRODUCCION = "LINEAPRODUCCION";
    
    String FIELD_PRODUCCION_ID = ID_COMPANIA;
    String FIELD_PRODUCCION_STATUS_AUTORIZACION = "SAUTORIZACION";
    String FIELD_PRODUCCION_STATUS_PRODUCCION = "SPRODUCCION";
    String FIELD_PRODUCCION_TIPO = "TIPO";
    String FIELD_PRODUCCION_SOLICITADOS = "SOLICITADOS";
    String FIELD_PRODUCCION_FECHA_SOLICITUD = "FSOLICITADOS";
    String FIELD_PRODUCCION_FECHA_AUTORIZACION = "FAUTORIZACION";
    String FIELD_PRODUCCION_FECHA_IMPORTACION = "FIMPORTACION";
    String FIELD_PRODUCCION_FECHA_DISTRIBUCION = "FDISTRIBUCION";
    String FIELD_PRODUCCION_COMPANIA_ID = "COMP_IDN";
    String FIELD_PRODUCCION_PROVEEDOR_ID = "PROV_IDN";
    String FIELD_PRODUCCION_PRODUCTO_ID = "PROD_IDN";
    String FIELD_PRODUCCION_CENTRO_ID = "CENTRO_IDN";
    String FIELD_PRODUCCION_ORIGEN_ID = "ORIG_IDN";
    String FIELD_PRODUCCION_DESTINO_ID = "DEST_IDN";
    String FIELD_PRODUCCION_TABLA = "TABLA";

    //CASO DE USO DE AUTORIZAR SOLICITUD    
    String FIELD_SOLAUT_FOLIO = "IDSOLICITUD";
    String FIELD_SOLAUT_RFC = RFC;
    String FIELD_SOLAUT_RAZONSOCIAL = RAZON_SOCIAL;
    String FIELD_SOLAUT_CANTCOD = "CANTSOLICITADA";
    String FIELD_SOLAUT_FECHA = "FECSOLICITUD";
    String FIELD_SOLAUT_PROC = ID_ESTATUS;
    String FIELD_SOLAUT_RESOL = RESOLUCION;
    
    //TABLA TBCT_RESOLUCION
    String FIELD_IDRESOLUCION = "IDRESOLUCION";
    String FIELD_IDSOLICITUD = "IDSOLICITUD";
    String FIELD_IDESTRESOLUCION = "IDESTRESOLUCION";
    String FIELD_FECRESOLUCION = "FECRESOLUCION";
    String FIELD_NUMSERVIDORPUBLICO = "NUMSERVIDORPUBLICO";
    String FIELD_FOLIOINICIAL = "FOLIOINICIAL";
    String FIELD_FOLIOFINAL = "FOLIOFINAL";
    String FIELD_FECHAINICIO = "FECHAINICIO";
    String FIELD_FECHAFIN = "FECHAFIN";
    String FIELD_IDESTCARGADOR = "IDESTCARGADOR";

    //TABLA TBCC_TIPOCONTRIB
    String FIELD_IDTIPOCONTRIB = "IDTIPOCONTRIB";
    String FIELD_DESCTIPOCONTRIB = "DESCTIPOCONTRIB";
    String FIELD_FECINICIO = FECINICIO;
    String FIELD_FECFINAL = "FECFINAL";

    String FIELD_TABACALERA_IDTABACALERA = ID_TABACALERA;
    String FIELD_TABACALERA_RFC = RFC;
    String FIELD_TABACALERA_RAZONSOCIAL = RAZON_SOCIAL;
    String FIELD_TABACALERA_DOMICILIO = DOMICILIO;
    String FIELD_TABACALERA_IDTIPOUSUARIO = "IDTIPOUSUARIO";
    String FIELD_TABACALERA_FECREGISTRO = "FECREGISTRO";
    String FIELD_TABACALERA_FECCAPTURA = "FECCAPTURA";
    String FIELD_TABACALERA_IDESTATUS = ID_ESTATUS;
    String FIELD_TABACALERA_CORREOELECTRONICO = "CORREOELECTRONICO";
    String FIELD_TABACALERA_TELEFONO = "TELEFONO";

    String FIELD_REPLEGAL_IDREPLEGAL = "IDREPLEGAL";
    String FIELD_REPLEGAL_IDTABACALERA = ID_TABACALERA;
    String FIELD_REPLEGAL_IDPROVEEDOR = "IDPROVEEDOR";
    String FIELD_REPLEGAL_NOMBRE = NOMBRE;
    String FIELD_REPLEGAL_APELLIDOPATERNO = "APELLIDOPATERNO";
    String FIELD_REPLEGAL_APELLIDOMATERNO = "APELLIDOMATERNO";
    String FIELD_REPLEGAL_RAZONSOCIAL = RAZON_SOCIAL;
    String FIELD_REPLEGAL_CORREOELECTRONICO = "CORREOELECTRONICO";
    String FIELD_REPLEGAL_TELEFONO = "TELEFONO";
    String FIELD_REPLEGAL_FECINICIO = FECINICIO;
    String FIELD_REPLEGAL_FECFIN = "FECFIN";
    String FIELD_REPLEGAL_IDESTATUS = ID_ESTATUS;
    String FIELD_REPLEGAL_RFC = RFC;
    String FIELD_REPLEGAL_IDTIPOREPLEGAL = "IDTIPOREPLEGAL";
    
    String FIELD_SOL_RES_IDSOLICITUD ="IDSOLICITUD";
    String FIELD_SOL_RES_RFC =RFC;
    String FIELD_SOL_RES_PAS ="PAS";
    String FIELD_SOL_RES_CANTSOLICITADA ="CANTSOLICITADA";
    String FIELD_SOL_RES_CANTAUTORIZADA ="CANTAUTORIZADA";
    String FIELD_SOL_RES_FECSOLICITUD ="FECSOLICITUD";
    String FIELD_SOL_RES_ORIGEN ="ORIGEN";
    String FIELD_SOL_RES_RESOLUCION =RESOLUCION;
    String FIELD_SOL_RES_FECRESOLUCION ="FECRESOLUCION";
    
    String FIELD_SOL_AUTORIZADA_FOLIO ="FOLIO";
    String FIELD_SOL_AUTORIZADA_RFC =RFC;
    String FIELD_SOL_AUTORIZADA_CONTRIBUYENTE ="CONTRIBUYENTE";
    String FIELD_SOL_AUTORIZADA_FECHARES ="FECHARES";
    String FIELD_SOL_AUTORIZADA_CANTAUTORIZADA ="CANTAUTORIZADA";
    String FIELD_SOL_AUTORIZADA_FOLIOINICIAL ="FOLIOINICIAL";
    String FIELD_SOL_AUTORIZADA_FOLIOFINAL ="FOLIOFINAL";
    String FIELD_SOL_AUTORIZADA_RUTAARCHIVO =RUTAARCHIVO;
    String FIELD_SOL_AUTORIZADA_NUMLINEAS ="NUMLINEAS";
    String FIELD_SOL_AUTORIZADA_IDARCHIVO ="IDARCHIVO";
    
    
    
}