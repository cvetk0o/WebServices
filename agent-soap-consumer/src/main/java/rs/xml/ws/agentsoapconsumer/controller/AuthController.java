package rs.xml.ws.agentsoapconsumer.controller;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.agentsoapconsumer.payload.ApiResponse;
import rs.xml.ws.agentsoapconsumer.payload.LoginRequest;
import rs.xml.ws.agentsoapconsumer.payload.SignUpRequest;
import rs.xml.ws.agentsoapconsumer.services.IAuthService;

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


	@PreAuthorize( "hasRole('ADMIN')" )
	@GetMapping( "/admin" )
	public String adminMethof()
	{
		return "Admin";

	}

}
