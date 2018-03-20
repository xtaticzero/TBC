var FIND_CERTIFICATE = "Buscar Certificado";
var FIND_PRIVATE_KEY = "Buscar Clave Privada";


// javascript messages
var GENERIC_INVALID_MSG = "Certificado, clave privada o contrase\u00f1a de clave privada inv\u00e1lidos, int\u00e9ntelo nuevamente.";
var INVALID_CERTIFICATE_MSG = "Certificado no emitido por el SAT";
var INVALID_PRIVATE_KEY_MSG = "Clave privada inv\u00e1lida";
var INVALID_PRIVATE_KEY_PASSWORD_MSG = "Clave privada o contrase\u00f1a de clave privada inv\u00e1lida";
var JAVA_PROBLEM_MSG = "Su navegador no tiene Java instalado. Esta aplicaci\u00f3n no funcionar\u00e1.";

var NPOBJECT_ERROR = "Los datos introducidos no corresponden, favor de verificar su contrase\u00f1a de clave privada, clave privada y/o certificado nuevamente";

//applet messages
var INVALID_CERT_TYPE = "Certificado no emitido por el SAT: Debe usar un certificado de FIEL";
var INVALID_FILE = "Certificado no emitido por el SAT: Archivo inv\u00e1lido";
var INVALID_CERT = "Certificado no emitido por el SAT:";
var INVALID_USERID = "El certificado no contiene un RFC";
var CORRESPOND_ERROR = "El certificado no corresponde con la clave privada.";

//****************************************************************************************************************
function onload() {
    document.getElementByd("btnSubmit").setAttribute("disabled", "disabled");
}

function validarFormulario() {
//document.getElementById("solicitudes:privateKey").value

    if ($( "input[id$='privateKey']" ).val() == "") {
        alert("Debe seleccionar el archivo de Clave Privada");
        return false;
    }
    if ($( "input[id$='certificate']" ).val()== "") {
        alert("Debe seleccionar el Certificado ");
        return false;
    }
    if ($( "input[id$='privateKeyPassword']" ).val() == "") {
        alert("Debe ingresar la contraseña del Archivo de Clave Privada");
        return false;
    }
    if ($( "input[id$='txtRFC']" ).val() == "") {
        alert("Debe ingresar el RFC");
        return false;
    }
    return true;
}


//****************************************************************************************************************


function browseForCertificate() {
    try {

        var filename = document.FIEL.showFileDialog(FIND_CERTIFICATE, "*.cer");

        //document.getElementById("solicitudes:certificate").value = filename;
        $( "input[id$='certificate']" ).val(filename);
        
//        try {
//            getRFCFromCert(filename);
//        }
//        catch (err) {
//            alert("No se pudo obtener RFC de certificado.");
//        }

    } catch (err) {
        document.getElementById("solicitudes:certificate").value = '';

        var desc = err.toString();
        if (navigator.appName.indexOf('Microsoft') > -1) {
            desc = err.description;
        }
        // if a key is specified, it returns this message from the SgiCripto libs (nice)
        if (desc.indexOf('5 >= 2') > -1) {
            desc = INVALID_CERTIFICATE_MSG;
        }
        // if the applet hasn't loaded yet, or won't load
        if (desc.indexOf('TypeError') > -1) {
            desc = JAVA_PROBLEM_MSG;
        }
        if (desc.indexOf("java.lang.Exception: ") == 0) {
            desc = desc.substring(21);
        }
        if (desc.indexOf("java.io.FileNotFoundException: ") == 0) {
            desc = desc.substring(31);
        }
        if (desc.indexOf("java.security.PrivilegedActionException: java.lang.Exception: ")==0){
            desc = desc.substring(31);
        }
        //$('#error').html(INVALID_FILE); //
        alert(desc);
    }
}
function browseForPrivateKey() {
    try {

        var filename = document.FIEL.showFileDialog(FIND_PRIVATE_KEY, "*.key");

        //document.getElementById("solicitudes:privateKey").value = filename;
        $( "input[id$='privateKey']" ).val(filename);

    } catch (err) {
        document.getElementById("privateKey").value = '';

        var desc = err.toString();
        if (navigator.appName.indexOf('Microsoft') > -1) {
            desc = INVALID_PRIVATE_KEY_MSG;
        }
        if (desc.indexOf('TypeError') > -1) {
            desc = JAVA_PROBLEM_MSG;
        }
        if (desc.indexOf("java.lang.Exception: ") == 0) {
            desc = desc.substring(21);
        }
        if (desc.indexOf("java.io.FileNotFoundException: ") == 0) {
            desc = desc.substring(31);
        }
        
//        $('#error').html(INVALID_FILE);
        alert(INVALID_FILE);
        //addMessage(desc);
    }
}
//****************************************************************************************************************

function enviarFormulario() {

    //var rfc = document.getElementById("solicitudes:txtRFC").value;
    //var userPwd = document.getElementById("solicitudes:privateKeyPassword").value;
    //var certFilePath = document.getElementById("solicitudes:certificate").value;
    //var keyFilePath = document.getElementById("solicitudes:privateKey").value;
    var rfc=$( "input[id$='txtRFC']" ).val();
    var userPwd=$( "input[id$='privateKeyPassword']" ).val();
    var certFilePath =$( "input[id$='certificate']" ).val();
    var keyFilePath =$( "input[id$='privateKey']" ).val();
    //var numeroSerie = document.getElementById("numeroSerie").value;
    //var cadenaOriginal = document.getElementById("cadenaOriginal").value;
    //var mode = document.getElementById("mode").value;Emmanuel

    var cadenaOriginal = $( "input[id$='cadenaOriginal']" ).val();
    var mode =  $( "input[id$='mode']" ).val();
    
    var selloDigital;

    if (validarFormulario()) {

        try {
            document.FIEL.firmarElectronicamente(rfc, userPwd, certFilePath, keyFilePath, null, cadenaOriginal, mode);
            cadenaOriginal = document.FIEL.getCadena();
            selloDigital = document.FIEL.getFirma();
            //numeroSerie = document.FIEL.getNumeroSerieCertificado();
            //document.getElementById("cadenaOriginal").value = cadenaOriginal;
            //document.getElementById("firmaDigital").value = selloDigital;
            
            $( "input[id$='firmaDigital']" ).val(selloDigital);
            $( "input[id$='cadenaOriginal']" ).val(cadenaOriginal);
            
            //document.getElementById("numeroSerie").value = numeroSerie;
            //document.FIELForm.submit();
            /*alert("Numero de serie del certificado: " + document.getElementById("numeroSerie").value + "\n\
            Cadena Original: " + cadenaOriginal + "\n\
            Firma digital:  " + selloDigital);*/
            //document.getElementById("solicitudes:btnFirma").click();
            setparamAttrs("cadenaOriginal", cadenaOriginal);
            setparamAttrs("firmaDigital", selloDigital);
            
            //firma.hide();
            //$( "input[id$='privateKeyPassword']" ).val("");
            //$("form")[0].;
            
        } catch (err) {
            //document.getElementById("solicitudes:privateKeyPassword").value = '';
            //$( "input[id$='privateKeyPassword']" ).val("");
            var desc = err.toString();

            if (navigator.appName.indexOf('Microsoft') > -1) {
                desc = err.description;
            }
            // if a key is specified, it returns this message from the SgiCripto libs (nice)
            if (desc.indexOf('5 >= 2') > -1) {
                desc = INVALID_CERTIFICATE_MSG;
            }
            // if the applet hasn't loaded yet, or won't load
            if (desc.indexOf('TypeError') > -1) {
                desc = JAVA_PROBLEM_MSG;
            }
            if (desc.indexOf('Bad sequence') > -1 || desc.indexOf('cannot be cast') > -1) {
                desc = INVALID_PRIVATE_KEY_MSG;
            }
            // invalid private key password
            if (desc.indexOf('pad block corrupted') > -1) {
                desc = INVALID_PRIVATE_KEY_PASSWORD_MSG;
            }
            // applet is not loaded yet
            if (desc.indexOf('TypeError') > -1) {
                desc = JAVA_PROBLEM_MSG;
            }
            // chrome bug - message when key and pwd do not correspond
            if (desc.toLowerCase().indexOf('npobject') > -1) {
                desc = NPOBJECT_ERROR;
            }
            if (desc.indexOf("java.lang.Exception: ") == 0) {
                desc = desc.substring(21);
            } else {
                desc = GENERIC_INVALID_MSG;
            }

            alert(desc);
        }
    }
}

function setparamAttrs(parmID, parmVal) {

 var gData =  document.createDocumentFragment();
 var newNode = document.createElement("param");

 newNode.setAttribute("id", parmID);
 newNode.setAttribute("value", parmVal);

 gData.appendChild(newNode);
 document.body.appendChild(gData);
}

function getRFCFromCert(certFilePath) {
    var resulRFC = "";
    try {
        if(certFilePath){
            resulRFC = document.FIEL.getRfcFormCertificate(certFilePath);
            //document.getElementById("txtRFC").value = resulRFC;
            $( "input[id$='txtRFC']" ).val(resulRFC);
        }
    } catch (err) {/**No se pudo leer el rfc del certificado.*/}
    return resulRFC;
}

