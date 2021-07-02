package rs.xml.ws.netflixzuulgatewayserver.controllers;

import java.util.List;
import java.util.Set;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.netflixzuulgatewayserver.models.User;
import rs.xml.ws.netflixzuulgatewayserver.payload.ApiResponse;
import rs.xml.ws.netflixzuulgatewayserver.payload.LoginRequest;
import rs.xml.ws.netflixzuulgatewayserver.payload.SignUpRequest;
import rs.xml.ws.netflixzuulgatewayserver.security.CurrentUser;
import rs.xml.ws.netflixzuulgatewayserver.security.UserPrincipal;
import rs.xml.ws.netflixzuulgatewayserver.services.IAuthService;

@RestController
@RequestMapping( "/api/auth" )
public class AuthController
{

	@Autowired
	private IAuthService service;

	@PostMapping( "/signin" )
	public ResponseEntity< ? > authenticateUser( @Valid @RequestBody LoginRequest loginRequest )
	{

		return service.authenticateUser( loginRequest );

	}


	@PostMapping( "/signup" )
	public ResponseEntity< ApiResponse > registerUser( @Valid @RequestBody SignUpRequest signUpRequest )
	{
		return service.registerUser( signUpRequest );

	}


	@PostMapping( "/signout" )
	public ResponseEntity< ApiResponse > logout( @RequestHeader( value = "Authorization" ) String token )
	{
		return service.logout( token );

	}


	@PatchMapping( "/me" )
	public ResponseEntity< ApiResponse > updatePassword( @CurrentUser UserPrincipal up, @RequestBody LoginRequest data )
	{
		return service.changePassword( up, data.getPassword(), data.getUsernameOrEmail() );

	}


	@GetMapping( "/me" )
	public ResponseEntity< User > getMe( @CurrentUser UserPrincipal up )
	{
		return service.whoImI( up );

	}


	@GetMapping( "/user/checkUsernameAvailability/{username}" )
	public ResponseEntity< ApiResponse > checkUsernameAvailibility( @PathVariable String username )
	{
		return service.checkUsernameAvailibility( username );

	}


	@GetMapping( "/user/checkEmailAvailability/{email}" )
	public ResponseEntity< ApiResponse > checkEmailAvailibility( @PathVariable String email )
	{
		return service.checkEmailAvailibility( email );

	}


	@GetMapping( "/exists/{id}" )
	public ResponseEntity< ApiResponse > existsByIdUser( @PathVariable Long id )
	{
		return service.checkIdAvailibility( id );

	}


	@GetMapping( "/mail/{id}" )
	public ResponseEntity< ApiResponse > getMail( @PathVariable Long id )
	{
		return service.getEmail( id );

	}


	@GetMapping( "/users" )
	public ResponseEntity< List< User > > getUsers( @RequestBody Set< Long > ids )
	{
		return service.getUsers( ids );

	}

}
