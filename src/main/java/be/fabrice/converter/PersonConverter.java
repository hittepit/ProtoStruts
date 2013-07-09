package be.fabrice.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import be.fabrice.service.PersonService;
import be.fabrice.service.mock.PersonServiceMock;
import be.fabrice.vo.Person;

public class PersonConverter extends StrutsTypeConverter {
	private PersonService personService = PersonServiceMock.getInstance();

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		Long id = Long.valueOf(values[0]); //Normalement, consolider...
		return personService.find(id);
	}

	@Override
	public String convertToString(Map context, Object o) {
		return ((Person)o).getId().toString();
	}
	
}
