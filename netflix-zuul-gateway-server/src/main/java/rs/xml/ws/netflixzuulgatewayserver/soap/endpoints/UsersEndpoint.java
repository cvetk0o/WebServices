package rs.xml.ws.netflixzuulgatewayserver.soap.endpoints;

import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import rs.xml.ws.netflixzuulgatewayserver.exception.AppException;
import rs.xml.ws.netflixzuulgatewayserver.models.Role;
import rs.xml.ws.netflixzuulgatewayserver.models.RoleName;
import rs.xml.ws.netflixzuulgatewayserver.models.User;
import rs.xml.ws.netflixzuulgatewayserver.repositories.RoleRepository;
import rs.xml.ws.netflixzuulgatewayserver.repositories.UserRepository;
import rs.xml.ws.netflixzuulgatewayserver.soap.GetAllUsersRequest;
import rs.xml.ws.netflixzuulgatewayserver.soap.GetAllUsersResponse;
import rs.xml.ws.netflixzuulgatewayserver.soap.UserXML;

@Endpoint
public class UsersEndpoint
{

	private static final String NAMESPACE_URI = "www.rent-a-car.ws.xml.rs/auth";

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "getAllUsersRequest" )
	@ResponsePayload
	public GetAllUsersResponse synchUsers( @RequestPayload GetAllUsersRequest request )
	{
		GetAllUsersResponse response = new GetAllUsersResponse();

		logger.info( ">>>> Retriving users" );

		Role userRole = roleRepository.findByName( RoleName.ROLE_USER ).orElseThrow( () -> new AppException( "User Role not set." ) );

		Set< User > users = userRepository.findByRolesName( userRole.getName() );

		for ( User user : users )
		{
			UserXML u = new UserXML();

			u.setEmail( user.getEmail() );
			u.setFirstName( user.getFirstName() );
			u.setId( user.getId() );
			u.setLastName( user.getLastName() );
			u.setUsername( user.getUsername() );

			response.getUsers().add( u );
		}

		logger.info( ">>>> Returning users" );

		return response;

	}

}
