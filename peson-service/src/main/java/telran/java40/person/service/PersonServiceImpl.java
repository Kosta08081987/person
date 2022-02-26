package telran.java40.person.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.java40.person.dao.PersonRepository;
import telran.java40.person.dto.AddressDto;
import telran.java40.person.dto.PersonDto;
import telran.java40.person.exceptions.PersonNotFoundException;
import telran.java40.person.model.Address;
import telran.java40.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {

	PersonRepository personRepository;
	ModelMapper modelMapper;
	
	
	@Autowired
	public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper) {
		this.personRepository = personRepository;
		this.modelMapper = modelMapper;
	}



	@Override
	public boolean addPerson(PersonDto personDto) {
		if(personRepository.existsById(personDto.getId())) {
			return false;
		}
			personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}



	@Override
	public PersonDto findPerson(Integer id) {
		if(!personRepository.existsById(id)) {
			throw new PersonNotFoundException(id);
		}
		Person person = personRepository.findById(id).get();
		return modelMapper.map(person, PersonDto.class);
	}



	@Override
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findByAddressCity(city).stream()
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}


	@Override
	@Transactional
	public Iterable<PersonDto> findByAges(Integer from, Integer to) {
		LocalDate fromD = LocalDate.now().minusYears(to);
		LocalDate toD = LocalDate.now().minusYears(from);
		return personRepository.findByBirthDateBetween(fromD, toD).map(p -> modelMapper.map(p,PersonDto.class)).collect(Collectors.toList());
	}



	@Override
	@Transactional
	public PersonDto updateName(Integer id, String newName) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		person.setName(newName);
		return modelMapper.map(person, PersonDto.class);
	}



	@Override
	@Transactional
	public List<PersonDto> findByName(String name) {
		return personRepository.findByName(name).map(p -> modelMapper.map(p,PersonDto.class)).collect(Collectors.toList());
	}



	@Override
	
	public Integer cityPopulation(String city) {
		return personRepository.countByAddressCity(city);
	}



	@Override
	@Transactional
	public PersonDto updateAddress(Integer id, AddressDto address) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		person.setAddress(modelMapper.map(address,Address.class));
		return modelMapper.map(person, PersonDto.class);
	}



	@Override
	@Transactional
	public PersonDto deletePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

}
