package be.fabrice.actions.json;

import be.fabrice.vo.Person;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Validations(requiredStrings={
		@RequiredStringValidator(fieldName="person.lastname",message="Le nom doit être rempli"),
		@RequiredStringValidator(fieldName="person.firstname",message="Le prénom doit être rempli")},
		customValidators=@CustomValidator(fieldName="person",type="personValidation",message="Le prénom et le nom doivent commencer par la même lettre")
)
public class PersonneAction extends ActionSupport{
	private Person person;
	
	public String input(){
		return "input";
	}
	
	public String save(){
		return "saved";
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
}
