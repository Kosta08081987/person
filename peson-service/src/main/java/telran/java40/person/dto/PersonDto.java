package telran.java40.person.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PersonDto {

	int id;
	String name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate birthDate;
	AddressDto address;
}
