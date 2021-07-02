package rs.xml.ws.netflixzuulgatewayserver.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.netflixzuulgatewayserver.models.RoleName;
import rs.xml.ws.netflixzuulgatewayserver.models.User;

@Repository
public interface UserRepository extends JpaRepository< User, Long >
{

	Optional< User > findByEmail( String email );

	Optional< User > findByUsername( String username );

	Optional< User > findByUsernameOrEmail( String username, String email );

	List< User > findByIdIn( Set< Long > userIds );

	Boolean existsByUsername( String username );

	Boolean existsByEmail( String email );

	Set< User > findByRolesName( RoleName role );

}
