package rs.xml.ws.filterandsearch.models.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDTO
{

	private Long id;

	@Email
	private String email;

	@NotBlank
	private String name;

	@NotBlank
	private String adress;

	@NotBlank
	private Long bussinessCode;

}
