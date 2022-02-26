package telran.java40.person.service;

import java.util.List;

import telran.java40.person.dto.AddressDto;
import telran.java40.person.dto.PersonDto;

public interface PersonService {

	boolean addPerson(PersonDto personDto);
	
	PersonDto findPerson(Integer id);
	
	Iterable<PersonDto> findPersonsByCity(String city);
	
	Iterable<PersonDto> findByAges(Integer from, Integer to);
	
	PersonDto updateName(Integer id, String newName);
	
	List<PersonDto> findByName(String name);
	
	Integer cityPopulation(String city);
	
	PersonDto updateAddress(Integer id, AddressDto address);
	
	PersonDto deletePerson(Integer id);
}
