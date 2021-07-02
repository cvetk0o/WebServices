package rs.xml.ws.netflixzuulgatewayserver.models;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import org.hibernate.annotations.NaturalId;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table( name = "users", uniqueConstraints =
{ @UniqueConstraint( columnNames =
		{ "username" } ), @UniqueConstraint( columnNames =
		{ "username" } ) } )
public class User
{

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	public long id;

	@NaturalId
	@NotBlank
	@Size( max = 100 )
	@Email
	private String email;

	@NotBlank( message = "Username can't be blank" )
	@Size( min = 2, message = "Your username must have at least 2 characters" )
	@Size( max = 100, message = "Your username can't have more than 100 characters" )
	public String username;

	@NotBlank( message = "First Name can't be blank" )
	@Size( min = 2, message = "Your First Name must have at least 2 characters" )
	@Size( max = 100, message = "Your First Name can't have more than 100 characters" )
	public String firstName;

	@NotBlank( message = "Last Name can't be blank" )
	@Size( min = 2, message = "Your Last Name must have at least 2 characters" )
	@Size( max = 100, message = "Your Last name can't have more than 100 characters" )
	public String lastName;

	@NotBlank
	@Size( max = 100, message = "Password can't be longer than 100 characters" )
	private String password;

	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable( name = "user_roles", joinColumns = @JoinColumn( name = "user_id" ), inverseJoinColumns = @JoinColumn( name = "role_id" ) )
	private Set< Role > roles = new HashSet< Role >();

	private boolean active;

	public User( @NotBlank @Size( max = 100 ) @Email String email,
			@NotBlank( message = "Username can't be blank" ) @Size( min = 2, message = "Your username must have at least 2 characters" ) @Size( max = 100,
					message = "Your username can't have more than 100 characters" ) String username,
			@NotBlank( message = "First Name can't be blank" ) @Size( min = 2, message = "Your First Name must have at least 2 characters" ) @Size( max = 100,
					message = "Your First Name can't have more than 100 characters" ) String firstName,
			@NotBlank( message = "Last Name can't be blank" ) @Size( min = 2, message = "Your Last Name must have at least 2 characters" ) @Size( max = 100,
					message = "Your Last name can't have more than 100 characters" ) String lastName )
	{
		super();
		this.email = email;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = false;

	}

}
