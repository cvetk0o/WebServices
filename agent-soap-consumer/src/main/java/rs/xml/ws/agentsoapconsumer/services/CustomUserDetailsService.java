package rs.xml.ws.agentsoapconsumer.services;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import rs.xml.ws.agentsoapconsumer.models.User;
import rs.xml.ws.agentsoapconsumer.repositories.UserRepository;
import rs.xml.ws.agentsoapconsumer.security.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService
{

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername( String usernameOrEmail ) throws UsernameNotFoundException
	{

		User user = userRepository.findByUsernameOrEmail( usernameOrEmail, usernameOrEmail )
				.orElseThrow( () -> new UsernameNotFoundException( ">>>> Cant find username or email [" + usernameOrEmail + "]" ) );

		return UserPrincipal.create( user );

	}


	@Transactional
	public UserDetails loadUserById( Long id )
	{
		User user = userRepository.findById( id ).orElseThrow( () -> new UsernameNotFoundException( "User not found with id : " + id ) );

		return UserPrincipal.create( user );

	}

}
