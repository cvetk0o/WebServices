package rs.xml.ws.agentsoapconsumer.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest
{

	@NotBlank
	@Size( min = 2, max = 100 )
	private String firstName;

	@NotBlank
	@Size( min = 2, max = 100 )
	private String lastName;

	@NotBlank
	@Size( min = 2, max = 100 )
	private String username;

	@NotBlank
	@Size( max = 100 )
	@Email
	private String email;

	@NotBlank
	@Size( max = 100 )
	private String password;

}
