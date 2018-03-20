package mx.gob.sat.mat.tabacos.vista.validador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import mx.gob.sat.mat.tabacos.vista.util.Expresiones;




@FacesValidator("rfcValidator")
public class RfcValidador implements Validator {
    
    private static final String INFO = "INFO";

    /**
     * Class constructor.
     */
    public RfcValidador() {
    }


    public void validaLongitud(String sRFC) {

        if (sRFC.length() != Expresiones.RFCM_LONGITUD && sRFC.length() !=Expresiones.RFC_LONGITUD) {
                FacesMessage msg = new FacesMessage(INFO, "El RFC debe tener 13 ó 12 posiciones según sea el caso");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
        }


    }


    public void validaRFCFisica(String sRFC) {

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(Expresiones.RFC_PATTERN_1BLOQUE);
        matcher = pattern.matcher(sRFC);
        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(INFO, "Las primeras 4 posiciones deben ser letras");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        } else {
            pattern = Pattern.compile(Expresiones.RFC_PATTERN_2BLOQUE);
            matcher = pattern.matcher(sRFC);
            if (!matcher.matches()) {
                FacesMessage msg = new FacesMessage(INFO, "Año[yy]-Mes[mm]-Dia[dd] deben ser válidos y numéricos");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            } else {
                pattern = Pattern.compile(Expresiones.RFC_PATTERN_3BLOQUE);
                matcher = pattern.matcher(sRFC);
                if (!matcher.matches()) {
                    FacesMessage msg = new FacesMessage(INFO, "El RFC no debe contener caracteres especiales");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);

                } else {
                    pattern = Pattern.compile(Expresiones.RFC_PATTERN_4BLOQUE);
                    matcher = pattern.matcher(sRFC);
                    if (!matcher.matches()) {
                        FacesMessage msg = new FacesMessage(INFO, "El último digito debe ser numérico o la letra A");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(msg);

                    }
                }

            }

        }

    }


    public void validaRFCMoral(String sRFC) throws ValidatorException {

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(Expresiones.RFCM_PATTERN_1BLOQUE);
        matcher = pattern.matcher(sRFC);
        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(INFO, "Las primeras 3 posiciones deben ser letras");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        } else {
            pattern = Pattern.compile(Expresiones.RFCM_PATTERN_2BLOQUE);
            matcher = pattern.matcher(sRFC);
            if (!matcher.matches()) {
                FacesMessage msg = new FacesMessage(INFO, "Año-Mes-Dia deben ser válidos y numéricos");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            } else {
                pattern = Pattern.compile(Expresiones.RFCM_PATTERN_3BLOQUE);
                matcher = pattern.matcher(sRFC);
                if (!matcher.matches()) {
                    FacesMessage msg = new FacesMessage(INFO, "El RFC no debe contener caracteres especiales");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);

                } else {
                    pattern = Pattern.compile(Expresiones.RFCM_PATTERN_4BLOQUE);
                    matcher = pattern.matcher(sRFC);
                    if (!matcher.matches()) {
                        FacesMessage msg = new FacesMessage(INFO, "El último digito debe ser numérico o la letra A");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(msg);

                    }
                }

            }

        }

    }

    /**
     * Procesa por medio de filtros (expresiones regulares) la entrada de datos que se obtiene
     * de la vista, valida que el texto ingresado tenga formato de RFC, en caso contrario
     * genera un mensaje de error a nivel de pantalla.
     *
     * @param  facesContext el contexto de JSF
     * @param  uiComponent el objeto de componentes de JSF
     * @param  value objeto con propiedad de la vista
     * @return void
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {

        String sRFC = (String)value;
        sRFC = sRFC.toUpperCase();

        validaLongitud(sRFC);
        if (sRFC.length() == Expresiones.RFC_LONGITUD) {
            validaRFCFisica(sRFC);
        } else if (sRFC.length() == Expresiones.RFCM_LONGITUD) {
            validaRFCMoral(sRFC);
        }
    }

}
