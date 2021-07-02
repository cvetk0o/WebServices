package rs.xml.ws.agentsoapconsumer.services;

import javax.validation.Valid;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


import rs.xml.ws.agentsoapconsumer.payload.ApiResponse;
import rs.xml.ws.agentsoapconsumer.payload.LoginRequest;
import rs.xml.ws.agentsoapconsumer.payload.SignUpRequest;

public interface IAuthService
{

	ResponseEntity< ? > authenticateUser( @Valid @RequestBody LoginRequest loginRequest );

	ResponseEntity< ApiResponse > registerUser( @Valid @RequestBody SignUpRequest signUpRequest );

	ResponseEntity< ApiResponse > logout( @RequestHeader( value = "Authorization" ) String token );

}
