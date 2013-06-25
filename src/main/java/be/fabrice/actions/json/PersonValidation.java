package be.fabrice.actions.json;

import org.apache.commons.lang.StringUtils;

import be.fabrice.vo.Person;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class PersonValidation extends FieldValidatorSupport {

	public void validate(Object object) throws ValidationException {
		Person person = (Person)getFieldValue("person",object);
		if(person != null){
			if(!StringUtils.isEmpty(person.getFirstname()) && 
					!StringUtils.isEmpty(person.getLastname()) && 
					!person.getFirstname().startsWith(person.getLastname().substring(0,1))){
				//Ajoute une erreur au niveau action
				addActionError(object);
				//Ajoute l'erreur sur le champ
				//Décommenter la ligne suivante en commentant la précédente pour changer le comportement
				//addFieldError("person.firstname",object);
			}
		}
	}

}
