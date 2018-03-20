
PrimeFaces.locales['es'] = {
    closeText : 'Cerrar',
    prevText : 'Anterior',
    nextText : 'Siguiente',
    currentText : 'Inicio',
    monthNames : ['Enero', 'Febrero', 'Marzo', 'Abril',
                'Mayo', 'Junio', 'Julio', 'Agosto',
                'Septiembre', 'Octubre', 'Noviembre',
                'Diciembre'],
    monthNamesShort : ['Ene', 'Feb', 'Mar', 'Abr', 'May',
                'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
    dayNames : ['Domingo', 'Lunes', 'Martes', 'Miércoles',
                'Jueves', 'Viernes', 'Sábado'],
    dayNamesShort : ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
    dayNamesMin : ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
    weekHeader : 'Semana',
    firstDay : 1,
    isRTL : false,
    showMonthAfterYear : false,
    yearSuffix : '',
    timeOnlyTitle : 'Sólo hora',
    timeText : 'Tiempo',
    hourText : 'Hora',
    minuteText : 'Minuto',
    secondText : 'Segundo',
    currentText : 'Fecha actual',
    ampm : false,
    month : 'Mes',
    week : 'Semana',
    day : 'Día',
    allDayText : 'Todo el día'
};

function start() {  
    statusDialog.show();
}  
          
function stop() {  
    statusDialog.hide();
}

function showPagos(data){
    if(data.status === "success"){
        dlgPagosVarios.show();
    }
}

function clearInvalidFileMsg() {
    fileuplaod_wgt.uploadContent.find("tr.ui-state-error").remove();
}

function validateEnter(){
    if(event.keyCode==13){
        return false;
    }
}

function validaNumeros(e){
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum === 0)||(keynum === 8) || (keynum === 9)){
        return true;
    }

    return /\d/.test(String.fromCharCode(keynum));
}

function validaAcentos(e) {
    alert('Llege');
    //var tecla = window.event ? window.event.keyCode : e.which;
    var tecla = (document.all) ? e.keyCode : e.which;
    alert('la tecla es :: ' + tecla);
    var minAcentos = (tecla >= 160 && tecla <= 163) || tecla == 130; //minusculas con acentos
    var mayAcentos = tecla == 181 || tecla == 144 || tecla == 214 || tecla == 224 || tecla == 233; //mayusculas con acentos
//    if (minAcentos || mayAcentos) {
//        alert('Entre');
//        return false;
//    }
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
    espacio = tecla == 160 || tecla == 127 || tecla == 32;

    return validaAlfaNumericos(e) || mayAcentos || minAcentos ||comaPunto || espacio;
}

function justNumbers(e) {
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum === 0) || (keynum === 8) || (keynum === 9)) {
        return true;
    }
    return /\d/.test(String.fromCharCode(keynum));
}

$(function () {
    $(document).bind("contextmenu", function (e) {
        return false;
    });
});

$(document).ready(function(){
    $(document).on("cut copy paste","#formAMarca\\:txtClave",function(e) {
        e.preventDefault();
    });
    
    $(document).on("cut copy paste","#formCMarca\\:txtClave",function(e) {
        e.preventDefault();
    });
    
    $(document).on("cut copy paste","#formAutorizarSol\\:cantSolAut",function(e) {
        e.preventDefault();
    });
 });

//Deshabilita las fechas o rfc para Reportes de codigos falso o no validos
function ingresoRfcCodigos (){
    var txtRfc = document.getElementById("formCodigo:txtRFC4");
    var valorRfc = txtRfc.value;
    var tamRfc = $.trim(valorRfc).length;
    var tipoCodigo = $('> option:selected', $(codigoCB.getJQ()).find('select')).val(); 
    
    var fechaIni = document.getElementById("formCodigo:finicio4_input");
    var fechaFin = document.getElementById("formCodigo:ffinal4_input");
    var tamIni = $.trim(fechaIni.value).length;
    var tamFin = $.trim(fechaFin.value).length;
    
    //--alert("Valor fecha: "+valorRfc+ " tam: "+tamRfc + " tipoCodigo: "+tipoCodigo +" tmini: "+tamIni+" tamFin: "+tamFin);
    //(1) codigos invalidos, solo rfc O fechas
    //(2) codigo falso es necesario fechar Y rfc
    if (tamRfc > 1 && tipoCodigo == 1) {
        //se inicia el ingreso de RFC, hay que deshabilitar las fechas
        fechaIni.disabled = true;
        fechaFin.disabled = true;
        
        $('[id$="finicio4_input"]').siblings('button').attr('disabled', 'true');
        $('[id$="ffinal4_input"]').siblings('button').attr('disabled', 'true');
        
    } else if ((tamIni >1 || tamFin >1) && tipoCodigo == 1){
        txtRfc.disabled = true;
    } else if (tipoCodigo == 1 && tamRfc == 0 &&  tamIni == 0 && tamFin == 0) {
        txtRfc.disabled = false;
        fechaIni.disabled = false;
        fechaFin.disabled = false;
        $('[id$="finicio4_input"]').siblings().removeAttr('disabled');
        $('[id$="ffinal4_input"]').siblings().removeAttr('disabled');
    }
    else if (tipoCodigo == 2) {
        //habilitar rfc y fechas
        txtRfc.disabled = false;
        fechaIni.disabled = false;
        fechaFin.disabled = false;
        $('[id$="finicio4_input"]').siblings().removeAttr('disabled');
        $('[id$="ffinal4_input"]').siblings().removeAttr('disabled');
    }
}
