package rs.xml.ws.netflixzuulgatewayserver.services;

import java.util.List;
import java.util.Set;


import org.springframework.http.ResponseEntity;


import rs.xml.ws.netflixzuulgatewayserver.models.User;
import rs.xml.ws.netflixzuulgatewayserver.payload.ApiResponse;

public interface IAdminService
{

	ResponseEntity< List< User > > getAllUsers();

	ResponseEntity< ApiResponse > updateUsersRoles( Long id, Set< Long > roles );

	ResponseEntity< ApiResponse > activateUser( Long user, Boolean action );

}
