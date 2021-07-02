package rs.xml.ws.netflixzuulgatewayserver.services;

import java.util.List;
import java.util.Set;


import javax.validation.Valid;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


import rs.xml.ws.netflixzuulgatewayserver.models.User;
import rs.xml.ws.netflixzuulgatewayserver.payload.ApiResponse;
import rs.xml.ws.netflixzuulgatewayserver.payload.LoginRequest;
import rs.xml.ws.netflixzuulgatewayserver.payload.SignUpRequest;
import rs.xml.ws.netflixzuulgatewayserver.security.UserPrincipal;

public interface IAuthService
{

	ResponseEntity< ? > authenticateUser( @Valid @RequestBody LoginRequest loginRequest );

	ResponseEntity< ApiResponse > registerUser( @Valid @RequestBody SignUpRequest signUpRequest );

	ResponseEntity< ApiResponse > logout( @RequestHeader( value = "Authorization" ) String token );

	ResponseEntity< ApiResponse > changePassword( UserPrincipal up, String newPassword, String oldPassword );

	ResponseEntity< User > whoImI( UserPrincipal up );

	ResponseEntity< ApiResponse > checkUsernameAvailibility( String username );

	ResponseEntity< ApiResponse > checkEmailAvailibility( String email );

	ResponseEntity< ApiResponse > checkIdAvailibility( Long id );

	ResponseEntity< ApiResponse > getEmail( Long id );

	ResponseEntity< List< User > > getUsers( Set< Long > ids );

}
