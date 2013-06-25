package be.fabrice.actions;

import java.util.ArrayList;
import java.util.List;

import be.fabrice.vo.ComplexeStructure;
import be.fabrice.vo.Person;

public class TestJsonAction {
	private String info;
	private ComplexeStructure structure;
	
	public String execute(){
		info = "Go";
		List<Person> persons = new ArrayList<Person>();
		Person p = new Person();
		p.setFirstname("Fabrice");
		p.setLastname("Claes");
		persons.add(p);
		p = new Person();
		p.setFirstname("Catherine");
		p.setLastname("Duchateau");
		persons.add(p);
		structure = new ComplexeStructure();
		structure.setPersons(persons);
		return "success";
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public ComplexeStructure getStructure() {
		return structure;
	}

	public void setStructure(ComplexeStructure structure) {
		this.structure = structure;
	}
	
	
}
