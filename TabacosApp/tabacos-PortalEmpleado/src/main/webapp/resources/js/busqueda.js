/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    //código a ejecutar cuando el DOM está listo para recibir instrucciones.
    //document.getElementById('resultadosB').style.display='none';
    //--menuOptions.jq.hide();
    //--menuOptions.jq.hidden();
});

function resetDatos() {
    //alert("Hello World!");
    contribuyente.jq.val('');
    proveedor.jq.val('');
    maquinaproduccion.jq.val('');
    fecha.jq.val('');
    fechaFin.jq.val('');
    marcas.jq.val('');
    codigo.jq.val('');
    loteproduccion.jq.val('');
    plantaproduccion.jq.val('');
}



function validarCampos () {
    //validar que por lo menos se tenga un campo con informacion para realizar la busquda
    /*alert("Regreso cont: "+$.trim(contribuyente.jq.val()).length +" Prov "+ $.trim(proveedor.jq.val()).length);
    alert("Regreso maq: "+$.trim(maquinaproduccion.jq.val()).length + " Fecha: "+$.trim(fecha.jq.val()).length);
    alert("Regreso marcas: "+$.trim(marcas.jq.val()).length +" Cod "+ $.trim(codigo.jq.val()).length);
    alert("Regreso marcas: "+$.trim(loteproduccion.jq.val()).length +" Cod "+ $.trim(codigo.jq.val()).length);*/
    
    //alert("<*> "+fecha.getDate()); // la manera correcta de obtener la fecha
    
    if( !$.trim(contribuyente.jq.val()).length && 
        !$.trim(proveedor.jq.val()).length && 
        !$.trim(maquinaproduccion.jq.val()).length && 
        !$.trim(fecha.getDate()).length && 
        !$.trim(fechaFin.getDate()).length &&
        !$.trim(marcas.jq.val()).length && 
        !$.trim(codigo.jq.val()).length && 
        !$.trim(loteproduccion.jq.val()).length && 
        !$.trim(plantaproduccion.jq.val()).length 
        ) { 
               alert("Se requiere llenar  por lo menos un campo"); 
        }
    
}
function justNumbers(e){
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum === 0)||(keynum === 8) || (keynum === 9)){
        return true;
    }

    return /\d/.test(String.fromCharCode(keynum));
}

function validaAlfaNumericos(e){
        tecla = (document.all) ? e.keyCode : e.which;
        patron =/[A-Za-z]/; //letras
        te = String.fromCharCode(tecla);
        noimpr = (tecla >= 0 && tecla <= 32); //caracteres no imprimibles
        enie = (tecla == 164 || tecla == 165) // ñ
        return patron.test(te) || justNumbers(e) || noimpr || enie;
    }
    
function validaAlfaNumericosConAcentos(e){
    tecla = (document.all) ? e.keyCode : e.which;
    minAcentos = (tecla >= 160 && tecla <= 163) || tecla == 130; //minusculas con acentos
    mayAcentos = tecla == 181 || tecla == 144 || tecla == 214 || tecla == 224 || tecla == 233; //mayusculas con acentos
    comaPunto = tecla == 40 || tecla == 41 || (tecla >= 44 && tecla <= 46) || tecla == 58 || tecla == 59;
    espacio = tecla == 160 || tecla == 127;

    return validaAlfaNumericos(e) || mayAcentos || minAcentos ||comaPunto || espacio;
}

