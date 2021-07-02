package rs.xml.ws.agentsoapconsumer.services.implementations;

import java.net.URI;
import java.util.Collections;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import rs.xml.ws.agentsoapconsumer.exceptions.AppException;
import rs.xml.ws.agentsoapconsumer.models.JwtToken;
import rs.xml.ws.agentsoapconsumer.models.Role;
import rs.xml.ws.agentsoapconsumer.models.RoleName;
import rs.xml.ws.agentsoapconsumer.models.User;
import rs.xml.ws.agentsoapconsumer.payload.ApiResponse;
import rs.xml.ws.agentsoapconsumer.payload.LoginRequest;
import rs.xml.ws.agentsoapconsumer.payload.SignUpRequest;
import rs.xml.ws.agentsoapconsumer.repositories.RoleRepository;
import rs.xml.ws.agentsoapconsumer.repositories.UserRepository;
import rs.xml.ws.agentsoapconsumer.security.JwtTokenProvider;
import rs.xml.ws.agentsoapconsumer.services.IAuthService;
import rs.xml.ws.agentsoapconsumer.services.ISynchService;

@Service
public class AuthService implements IAuthService
{

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	private ISynchService synchService;

	@Override
	public ResponseEntity< ? > authenticateUser( @Valid LoginRequest loginRequest )
	{
		Authentication authentication =
				authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( loginRequest.getUsernameOrEmail(), loginRequest.getPassword() ) );

		SecurityContextHolder.getContext().setAuthentication( authentication );

		String jwt = tokenProvider.generateToken( authentication );

		// TODO change this, move it to other place
		synchService.synchAll();

		return ResponseEntity.ok( new JwtToken( jwt ) );

	}


	@Override
	public ResponseEntity< ApiResponse > registerUser( @Valid SignUpRequest signUpRequest )
	{
		if ( userRepository.existsByUsername( signUpRequest.getUsername() ) )
		{
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Username is already taken!" ), HttpStatus.BAD_REQUEST );
		}

		if ( userRepository.existsByEmail( signUpRequest.getEmail() ) )
		{
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Email Address already in use!" ), HttpStatus.BAD_REQUEST );
		}

		// Creating user's account
		User user = new User( signUpRequest.getEmail(), signUpRequest.getUsername(), signUpRequest.getFirstName(), signUpRequest.getLastName() );

		user.setPassword( passwordEncoder.encode( signUpRequest.getPassword() ) );

		Role userRole = roleRepository.findByName( RoleName.ROLE_AGENT ).orElseThrow( () -> new AppException( "User Role not set." ) );

		user.setRoles( Collections.singleton( userRole ) );

		User result = userRepository.save( user );

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path( "/api/users/{username}" ).buildAndExpand( result.getUsername() ).toUri();

		return ResponseEntity.created( location ).body( new ApiResponse( true, "User registered successfully" ) );

	}


	@Override
	public ResponseEntity< ApiResponse > logout( String token )
	{
		tokenProvider.suspendToken( token );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Succesefully signed out!" ), HttpStatus.CREATED );

	}

}
