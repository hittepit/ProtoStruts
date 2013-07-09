package be.fabrice.service.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.fabrice.service.PersonService;
import be.fabrice.vo.Person;

/**
 * Mock du service qui va se charger lui-même de la persistance. Uniquement à des fins de démos,
 * le rôle est cependant correct.
 * C'est un singleton standard.
 * @author fabrice.claes
 *
 */
public class PersonServiceMock implements PersonService {
	private static PersonServiceMock instance = new PersonServiceMock();
	
	public static PersonService getInstance(){
		return instance;
	}
	
	private Map<Long, Person> persons;
	private Long index = 0L;
	
	private PersonServiceMock(){
		persons = new HashMap<Long, Person>();
		//Initialisation de quelques données
		Person p = Person.builder().firstname("toto").lastname("Leheros").build();
		save(p);
		p = Person.builder().firstname("Charles").lastname("X").build();
		save(p);
	}

	public Person find(Long id) {
		return persons.get(id);
	}

	public List<Person> findAll() {
		return new ArrayList<Person>(persons.values());
	}

	public void save(Person p) {
		if(p.getId()==null){
			p.setId(index++);
		}
		persons.put(p.getId(), p);
	}

}
