package telran.java40.person.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@Embeddable
public class Address implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3619586816147396740L;

	String city;
	String street;
	int building;
}
