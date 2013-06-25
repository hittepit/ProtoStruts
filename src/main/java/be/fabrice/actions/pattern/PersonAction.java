package be.fabrice.actions.pattern;

import com.opensymphony.xwork2.conversion.annotations.Conversion;

import be.fabrice.vo.Person;

@Conversion
public class PersonAction {
	private String firstname;
	private String lastname;
	
	public String input(){
		throw new RuntimeException("test");
//		return "input";
	}
	
	public String save(){
		Person p = Person.builder().firstname(firstname).lastname(lastname).build();
		return "ok";
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
}
