package rs.xml.ws.netflixzuulgatewayserver.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Getter;
import lombok.Setter;
import rs.xml.ws.netflixzuulgatewayserver.models.User;

@Getter
@Setter
public class UserPrincipal implements UserDetails// UserDetailsService ce vracati instance ove klase, koristice ih za autentifik
													// i autoriz
{

	private static final long serialVersionUID = 2196229078859399515L;

	private Long id;

	private String first_name;

	private String last_name;

	private String username;

	@JsonIgnore
	private String email;

	@JsonIgnore
	private String password;

	private Collection< ? extends GrantedAuthority > authorities;

	public UserPrincipal( Long id, String firstName, String lastName, String username, String email, String password,
			Collection< ? extends GrantedAuthority > authorities )
	{
		this.id = id;
		this.first_name = firstName;
		this.last_name = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;

	}


	public static UserPrincipal create( User user )
	{
		List< GrantedAuthority > authorities =
				user.getRoles().stream().map( role -> new SimpleGrantedAuthority( role.getName().name() ) ).collect( Collectors.toList() );

		return new UserPrincipal( user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword(), authorities );

	}


	@Override
	public Collection< ? extends GrantedAuthority > getAuthorities()
	{
		return this.authorities;

	}


	@Override
	public String getPassword()
	{
		return this.password;

	}


	@Override
	public String getUsername()
	{
		return this.username;

	}


	@Override
	public boolean isAccountNonExpired()
	{
		return true;

	}


	@Override
	public boolean isAccountNonLocked()
	{
		return true;

	}


	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;

	}


	@Override
	public boolean isEnabled()
	{
		return true;

	}


	@Override
	public boolean equals( Object o )
	{
		if ( this == o )
			return true;
		if ( o == null || getClass() != o.getClass() )
			return false;
		UserPrincipal that = ( UserPrincipal ) o;
		return Objects.equals( id, that.id );

	}


	@Override
	public int hashCode()
	{

		return Objects.hash( id );

	}

}