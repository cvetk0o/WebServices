package rs.xml.ws.agentsoapconsumer.models.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import org.hibernate.annotations.NaturalId;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.xml.ws.agentsoapconsumer.soap.UserXML;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client
{

	public Client( UserXML userXML )
	{
		this.id = userXML.getId();
		this.email = userXML.getEmail();
		this.username = userXML.getUsername();
		this.firstName = userXML.getFirstName();
		this.lastName = userXML.getLastName();

	}

	@Id
	private Long id;

	@NaturalId
	@NotBlank
	@Size( max = 100 )
	@Email
	private String email;

	public String username;

	public String firstName;

	public String lastName;

	// TODO appointments

	// TODO ratings

	// TODO comments

}
