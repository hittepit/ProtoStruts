package be.fabrice.actions.json;

import java.util.List;

import be.fabrice.service.PersonService;
import be.fabrice.service.mock.PersonServiceMock;
import be.fabrice.vo.Person;

public class PersonListAction {
	private List<Person> persons;
	private PersonService personService = PersonServiceMock.getInstance();
	
	public String execute(){
		persons = personService.findAll();
		return "success";
	}
	
	public List<Person> getPersons() {
		return persons;
	}
}
