package rs.xml.ws.netflixzuulgatewayserver.services.implementations;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;


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


import rs.xml.ws.netflixzuulgatewayserver.exception.AppException;
import rs.xml.ws.netflixzuulgatewayserver.models.JwtToken;
import rs.xml.ws.netflixzuulgatewayserver.models.Role;
import rs.xml.ws.netflixzuulgatewayserver.models.RoleName;
import rs.xml.ws.netflixzuulgatewayserver.models.User;
import rs.xml.ws.netflixzuulgatewayserver.payload.ApiResponse;
import rs.xml.ws.netflixzuulgatewayserver.payload.LoginRequest;
import rs.xml.ws.netflixzuulgatewayserver.payload.SignUpRequest;
import rs.xml.ws.netflixzuulgatewayserver.repositories.RoleRepository;
import rs.xml.ws.netflixzuulgatewayserver.repositories.UserRepository;
import rs.xml.ws.netflixzuulgatewayserver.security.JwtTokenProvider;
import rs.xml.ws.netflixzuulgatewayserver.security.UserPrincipal;
import rs.xml.ws.netflixzuulgatewayserver.services.IAuthService;

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

	@Override
	public ResponseEntity< ? > authenticateUser( @Valid LoginRequest loginRequest )
	{
		Authentication authentication =
				authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( loginRequest.getUsernameOrEmail(), loginRequest.getPassword() ) );

		SecurityContextHolder.getContext().setAuthentication( authentication );

		String jwt = tokenProvider.generateToken( authentication );
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

		Role userRole = roleRepository.findByName( RoleName.ROLE_USER ).orElseThrow( () -> new AppException( "User Role not set." ) );

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


	@Override
	public ResponseEntity< ApiResponse > changePassword( UserPrincipal up, String newPassword, String oldPassword )
	{

		String username = up.getUsername();

		User user = userRepository.findByUsername( username ).get();

		if ( !passwordEncoder.matches( oldPassword, user.getPassword() ) )
		{
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Passwords do not match!" ), HttpStatus.BAD_REQUEST );
		}

		user.setPassword( passwordEncoder.encode( newPassword ) );

		userRepository.save( user );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Password updated!" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< User > whoImI( UserPrincipal up )
	{
		String username = up.getUsername();

		return new ResponseEntity< User >( userRepository.findByUsername( username ).get(), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > checkUsernameAvailibility( String username )
	{
		Boolean existsByUsername = userRepository.existsByUsername( username );
		return new ResponseEntity< ApiResponse >( new ApiResponse( existsByUsername, "" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > checkEmailAvailibility( String email )
	{
		Boolean existsByEmail = userRepository.existsByEmail( email );

		return new ResponseEntity< ApiResponse >( new ApiResponse( existsByEmail, "" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > checkIdAvailibility( Long id )
	{
		boolean existsById = userRepository.existsById( id );
		return new ResponseEntity< ApiResponse >( new ApiResponse( existsById, "" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > getEmail( Long id )
	{

		Optional< User > userOptional = userRepository.findById( id );
		if ( !userOptional.isPresent() )
		{

			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "" ), HttpStatus.OK );
		}

		User user = userOptional.get();
		return new ResponseEntity< ApiResponse >( new ApiResponse( true, user.getEmail().toString() ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< List< User > > getUsers( Set< Long > ids )
	{

		return new ResponseEntity< List< User > >( userRepository.findByIdIn( ids ), HttpStatus.OK );

	}

}
