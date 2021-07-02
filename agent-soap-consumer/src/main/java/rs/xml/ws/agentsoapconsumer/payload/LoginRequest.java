package rs.xml.ws.agentsoapconsumer.payload;

import javax.validation.constraints.NotBlank;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest
{

	@NotBlank
	private String usernameOrEmail;

	@NotBlank
	private String password;

}
