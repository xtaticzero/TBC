package mx.gob.sat.mat.tabacos.vista.validador;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("primeDateRangeValidator")
public class PrimeDateRangeValidator implements Validator{
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o){
        if (o == null) {
            return;
        }
                 
        //Leave the null handling of startDate to required="true"
        Object startDateValue = uic.getAttributes().get("finicial");
        
        if (startDateValue==null) {
            return;
        }
         
        Date startDate = (Date)startDateValue;
        Date endDate = (Date)o;
        if (endDate.before(startDate)) {             
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error!", "La fecha Final no puede ser anterior a la fecha Inicial.");
            FacesContext.getCurrentInstance().addMessage(null, message);                    
            throw new ValidatorException(message);
        }
    }
}