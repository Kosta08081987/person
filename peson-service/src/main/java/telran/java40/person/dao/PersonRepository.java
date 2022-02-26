package telran.java40.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java40.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
	
	List<Person> findByAddressCity(String city);
	
	Stream<Person> findByBirthDateBetween(LocalDate from, LocalDate to);
	
	Stream<Person> findByName(String name);
	
	Integer countByAddressCity(String name);
}
