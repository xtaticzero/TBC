 function abre(nombre)
        {
           // alert(nombre);
            var mapForm = document.createElement("form");
            mapForm.method = "post";
            mapForm.action = "managedBeanRedirectServlet";
            mapForm.target = "principal";
                  
            var mapNomBean = document.createElement("input");
                mapNomBean.type = "hidden";
                mapNomBean.name = "nombreBean";
                mapNomBean.value = nombreBean(nombre);
                mapForm.appendChild(mapNomBean);
            var mapNomJSF = document.createElement("input");
                mapNomJSF.type = "hidden";
                mapNomJSF.name = "nombreJSF";
                mapNomJSF.value = nombreJSF(nombre);
                mapForm.appendChild(mapNomJSF);
            document.body.appendChild(mapForm);
            mapForm.submit();
            mapForm.removeChild(mapNomBean);
            mapForm.removeChild(mapNomJSF);
       }       
       
  function nombreBean(nombre){
            var nB;
            var opcion;
            opcion = translate(nombre);
           // alert(opcion);
            switch(opcion)
            {
             case 1: nB ='ConsultaPublicoMB'; 
                     break;
             case 2: nB ='registroMercancia'; 
                     break;
             case 3: nB ='seguimientoMB'; 
                     break;
             case 4: nB ='registroDonatariaAutorizadaManagedBean'; 
                     break;
             case 5: nB = 'seguimientoDonataria';
                    break;
             case 6: nB = 'SeguimientoSATMB';
                    break;
             case 7: nB = 'contraloriaSocial';
                    break;
             case 8: nB = 'resultadoDonataria';
                    break;
             case 9: nB = 'resultadoContribuyente';
                    break;
             default:break;             
            }
            
        return nB;
  }
  
  function nombreJSF(nombre){
        var nB;
            var opcion;
            opcion = translate(nombre);
            //alert(opcion);
            switch(opcion)
            {
             case 1: nB ='ConsultaPublico'; 
                     break;
             case 2: nB ='RegistroAviso'; 
                     break;
             case 3: nB ='SeguimientoContribuyente'; 
                     break;
             case 4: nB ='registroDonatariaAutorizada/registroDonatariaAutorizada'; 
                     break;
             case 5: nB = 'SeguimientoDonataria';
                    break;
             case 6: nB = 'SeguimientoSAT';
                    break;
             case 7: nB = 'ContraloriaSocial';
                    break;
             case 8: nB = 'ResultadoDonataria';
                    break;
              case 9: nB ='ResultadoContribuyente';
                    break;       
             default:break;             
            }
            
        return nB;
  }
  
  function translate(nombre){
      
      if(nombre == 'consultaPublica')
      return 1;
      else if(nombre == 'regAviso')
      return 2;
      else if(nombre == 'segContribuyente')
      return 3;
      else if(nombre == 'regDonataria')
      return 4;
      else if(nombre == 'segDonataria')
      return 5;
      else if(nombre == 'seguimientoSAT')
      return 6;
      else if(nombre == 'contraloriaSocial')
      return 7;
      else if(nombre == 'resDonataria')
      return 8;
      else if(nombre == 'resContribuyente')
      return 9;
  }
  
  function redondeoMiles(element){
      
      var valor = element;
      
      if(element < 1000){
          
      }
      
      
  }