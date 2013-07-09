package be.fabrice.service;

import java.util.List;

import be.fabrice.vo.Person;

public interface PersonService {
	Person find(Long id);
	List<Person> findAll();
	void save(Person p);
}
