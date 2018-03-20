
PrimeFaces.locales['es'] = {
    closeText: 'Cerrar',
    prevText: 'Anterior',
    nextText: 'Siguiente',
    currentText: 'Inicio',
    monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril',
        'Mayo', 'Junio', 'Julio', 'Agosto',
        'Septiembre', 'Octubre', 'Noviembre',
        'Diciembre'],
    monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May',
        'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
    dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles',
        'Jueves', 'Viernes', 'Sábado'],
    dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
    dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
    weekHeader: 'Semana',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'Sólo hora',
    timeText: 'Tiempo',
    hourText: 'Hora',
    minuteText: 'Minuto',
    secondText: 'Segundo',
    currentText : 'Fecha actual',
            ampm: false,
    month: 'Mes',
    week: 'Semana',
    day: 'Día',
    allDayText: 'Todo el día'
};

function start() {
    statusDialog.show();
}

function stop() {
    statusDialog.hide();
}

function superReload() {
    location.reload();
}

function highElment() {
    var elem1 = document.getElementById('solicitudes:spacerElement1');
    var elem2 = document.getElementById('solicitudes:spacerElement2');
    var elem3 = document.getElementById('solicitudes:spacerElement3');
    var elem4 = document.getElementById('solicitudes:spacerElement4');
    //var array = [document.getElementById('solicitudes:spacerElement1'),document.getElementById('solicitudes:spacerElement2'),document.getElementById('solicitudes:spacerElement3'),document.getElementById('solicitudes:spacerElement4')];


    var currentHeight = parseInt(elem1.style.height.replace('px', ''));
    currentHeight += 40;
    elem1.style.height = '' + currentHeight + 'px';
    elem2.style.height = '' + currentHeight + 'px';
    elem3.style.height = '' + currentHeight + 'px';
    elem4.style.height = '' + currentHeight + 'px';

    alert('form' + document.getElementById('solicitudes:divPagos').style.marginTop);
}

function borrarCositas() {

    $(".ui-state-error").remove();

}

function validateEnter() {
    if (event.keyCode == 13) {
        return false;
    }
}

function validNum() {
    if (event.keyCode < 48 || event.keyCode > 57) {
        event.returnValue = false;
    }
}

function justNumbers(e) {
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum === 0) || (keynum === 8) || (keynum === 9)) {
        return true;
    }
    
    return /\d/.test(String.fromCharCode(keynum));
}


function deshabilitarCamposCodigo() {
    var selectMenuDiv = tipoCodigo.getJQ();
    var selectMenu = $(selectMenuDiv).find('select');
    var selectValue = $('> option:selected', selectMenu).val();

    deshabilitaCodigoMarcas(selectValue);
    deshabilitarCargaArchivo(selectValue);
}


function deshabilitarCargaArchivo(selectValue) {
    var tamCodigo = $.trim(numeroCodigo.jq.val()).length;
    //alert("deshabilitando!! boton"+ tamCodigo +" >>" );

    //<label id="formCodigosF:labelcargaarchivo">Cargar Archivo</label>

    if (selectValue == 1) {
        //archivosCodigos.jq.attr('disabled', 'disabled'); // si sirve y marca error en el archivo
        archivosCodigos.jq.attr("style", "display:none"); // si sirve y no marca error - oculta boton de archivo
        var tempcarga = document.getElementById("formCodigosF:labelcargaarchivo");
        tempcarga.style.display = "none";
        var temArchivo = document.getElementById("formCodigosF:archivosCodigos_input");
        temArchivo.style.display = "none";
        //labelcargaarchivo.jq.attr( "style", "display:none" );//no sirve


        $("#cargaCodigosF").hide();
        //---$("#formCodigosF:labelcargaarchivo").hide(); // no sirve
        //archivosCodigos.jq.style.display = "none"; //no sirve
        //archivosCodigos.jq.attr('readonly', 'true'); //no sirve
    } else {
        archivosCodigos.jq.attr("style", "display:block"); // si sirve y no marca error - oculta boton de archivo
        var tempcarga = document.getElementById("formCodigosF:labelcargaarchivo");
        tempcarga.style.display = "block";
        var temArchivo = document.getElementById("formCodigosF:archivosCodigos_input");
        temArchivo.style.display = "block";

    }
}

function deshabilitaCodigoMarcas(selectValue) {

    //--alert("Aqui>> "+selectValue); 
    var labelmarca = document.getElementById("formCodigosF:labelmarca");
    var combomarca = document.getElementById("formCodigosF:cbxMarcasCod");
    var labelcodigo = document.getElementById("formCodigosF:labelnumerocodigo");
    var inputcodigo = document.getElementById("formCodigosF:numeroCodigo");

    if (selectValue == 2) {
        //ocultar etiqueta y combo de marca

        labelmarca.style.display = "none";
        combomarca.style.display = "none";
        labelcodigo.style.display = "none";
        inputcodigo.style.display = "none";


        /*cbxMarcasCod.jq.attr('disabled', 'disabled');
         var selectMarc = cbxMarcasCod.getJQ();
         var selectMV = $(selectMarc).find('select');
         selectMV.attr("disabled",'true');
         
         //cbxMarcasCod.jq.attr('readonly', 'true');
         numeroCodigo.jq.attr('readonly', 'true');
         
         /*cbxMarcasCod.jq.attr('disabled', 'disabled');
         numeroCodigo.jq.attr('disabled', 'disabled');*/
    } else {
        labelmarca.style.display = "block";
        combomarca.style.display = "block";
        labelcodigo.style.display = "block";
        inputcodigo.style.display = "block";
    }


}

function validaAlfaNumericos(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    patron = /[A-Za-z]/; //letras
    te = String.fromCharCode(tecla);
    noimpr = (tecla >= 0 && tecla <= 32); //caracteres no imprimibles
    enie = (tecla == 164 || tecla == 165) // ñ
    return patron.test(te) || justNumbers(e) || noimpr || enie;
}

function validaAlfaNumericosConAcentos(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    minAcentos = (tecla >= 160 && tecla <= 163) || tecla == 130; //minusculas con acentos
    mayAcentos = tecla == 181 || tecla == 144 || tecla == 214 || tecla == 224 || tecla == 233; //mayusculas con acentos
    comaPunto = tecla == 40 || tecla == 41 || (tecla >= 44 && tecla <= 46) || tecla == 58 || tecla == 59;
    espacio = tecla == 160 || tecla == 127 || tecla == 32;

    return validaAlfaNumericos(e) || mayAcentos || minAcentos || comaPunto || espacio;
}

$(function () {
    $(document).bind("contextmenu", function (e) {
        return false;
    });
});

$(document).ready(function(){
    $(document).on("cut copy paste","#solicitudAltaMarcaForm\\:clave",function(e) {
        e.preventDefault();
    });
    
    $(document).on("cut copy paste","#miForm\\:numeroCod",function(e) {
        e.preventDefault();
    });
 });