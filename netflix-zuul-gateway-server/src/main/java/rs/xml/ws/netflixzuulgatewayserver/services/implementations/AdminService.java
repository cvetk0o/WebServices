package rs.xml.ws.netflixzuulgatewayserver.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import rs.xml.ws.netflixzuulgatewayserver.models.Role;
import rs.xml.ws.netflixzuulgatewayserver.models.User;
import rs.xml.ws.netflixzuulgatewayserver.payload.ApiResponse;
import rs.xml.ws.netflixzuulgatewayserver.repositories.RoleRepository;
import rs.xml.ws.netflixzuulgatewayserver.repositories.UserRepository;
import rs.xml.ws.netflixzuulgatewayserver.services.IAdminService;

@Service
public class AdminService implements IAdminService
{

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public ResponseEntity< List< User > > getAllUsers()
	{

		logger.info( ">>>> Retriving users" );

		return new ResponseEntity< List< User > >( userRepository.findAll(), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > updateUsersRoles( Long user, Set< Long > roles )
	{

		Optional< User > userOptional = userRepository.findById( user );

		if ( !userOptional.isPresent() )
		{
			logger.error( ">>>> Cant find user" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find user" ), HttpStatus.BAD_REQUEST );
		}

		Set< Role > rolesToAdd = roleRepository.findByIdIn( roles );

		User user2change = userOptional.get();

		user2change.getRoles().clear();

		user2change.getRoles().addAll( rolesToAdd );

		userRepository.save( user2change );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > activateUser( Long user, Boolean action )
	{

		Optional< User > userOptional = userRepository.findById( user );

		if ( !userOptional.isPresent() )
		{
			logger.error( ">>>> Cant find user" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "cant find user" ), HttpStatus.BAD_REQUEST );
		}

		User user2change = userOptional.get();

		user2change.setActive( action );

		userRepository.save( user2change );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

	}

}
