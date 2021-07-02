package rs.xml.ws.netflixzuulgatewayserver.controllers;

import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.netflixzuulgatewayserver.models.User;
import rs.xml.ws.netflixzuulgatewayserver.payload.ApiResponse;
import rs.xml.ws.netflixzuulgatewayserver.services.IAdminService;

@RestController
@RequestMapping( "/admin" )
public class AdminController
{

	@Autowired
	private IAdminService service;

	@GetMapping( "/users" )
	public ResponseEntity< List< User > > getAllUsers()
	{
		return service.getAllUsers();

	}


	@PutMapping( "/user/role/{id}" )
	public ResponseEntity< ApiResponse > updateRoles( @PathVariable Long id, @RequestBody Set< Long > ids )
	{
		return service.updateUsersRoles( id, ids );

	}


	@PutMapping( "/activate/{id}/{action}" )
	public ResponseEntity< ApiResponse > activateUser( @PathVariable Long id, @PathVariable Boolean action )
	{
		return service.activateUser( id, action );

	}

}
