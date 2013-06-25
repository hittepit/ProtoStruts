package be.fabrice.actions.wizard;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.validators.ValidatorSupport;

class CodeValidator extends ValidatorSupport{
	public void validate(Object object) throws ValidationException {
		// TODO Auto-generated method stub
		
	}
}

@Validations(
		requiredStrings={
				@RequiredStringValidator(fieldName="personne.nom",trim=true,message="Le nom doit être rempli"),
				@RequiredStringValidator(fieldName="personne.prenom", trim=true,message="Le prénom doit être rempli")
		},
		requiredFields={
				@RequiredFieldValidator(fieldName="personne.sexe",message="Choisissez un sexe")
		},
		customValidators={
				@CustomValidator(fieldName="personne.code",message="le code",type="CodeValidator")
		}
		)
public class WizardPersonnAction {
	private Personne personne;
	
	public String input(){
		personne = (Personne) ActionContext.getContext().getSession().get("personne");
		return "input";
	}
	
	public String save(){
		return "saved";
	}
	
	public String saveAndNext(){
		return "next";
	}
	
	public Personne getPersonne() {
		return personne;
	}
	
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
}
