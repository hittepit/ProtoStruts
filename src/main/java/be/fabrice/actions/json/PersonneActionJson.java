package be.fabrice.actions.json;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import be.fabrice.vo.Person;

@Validations(requiredStrings={
		@RequiredStringValidator(fieldName="person.lastname",message="Le nom doit être rempli"),
		@RequiredStringValidator(fieldName="person.firstname",message="Le prénom doit être rempli")},
		customValidators=@CustomValidator(fieldName="person",type="personValidation",message="Le prénom et le nom doivent commencer par la même lettre")
)
public class PersonneActionJson extends ActionSupport{
	private Person person;
	
	private String status="KO";
	
	public String input(){
		return "success";
	}
	
	public String save(){
		status="OK";
		addActionMessage("Sauvegarde réussie");
		return "saved";
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public String getStatus() {
		return status;
	}
	
	@Override
	public void validate() {
//		if(!StringUtils.isEmpty(person.getFirstname()) && 
//				!StringUtils.isEmpty(person.getLastname()) && 
//				!person.getFirstname().startsWith(person.getLastname().substring(0,1))){
//			addActionError("Le prénom et le nom doivent commencer par la même lettre");
//		}
	}
	
	//Override nécessaire sinon pas repris dans la réponse JSON
	@Override
	public Collection<String> getActionErrors() {
		return super.getActionErrors();
	}
	
	//Override nécessaire sinon pas repris dans la réponse JSON
	@Override
	public Map<String, List<String>> getFieldErrors() {
		return super.getFieldErrors();
	}
	
	//Override nécessaire sinon pas repris dans la réponse JSON
	@Override
	public Collection<String> getActionMessages() {
		return super.getActionMessages();
	}
}
