package telran.java40.person.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5883074161089097217L;

	public PersonNotFoundException(Integer id) {
		super("Person " + id +" not found");
	}
	
	

}
