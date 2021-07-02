package rs.xml.ws.netflixzuulgatewayserver.repositories;

import java.util.Optional;
import java.util.Set;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.netflixzuulgatewayserver.models.Role;
import rs.xml.ws.netflixzuulgatewayserver.models.RoleName;

@Repository
public interface RoleRepository extends JpaRepository< Role, Long >
{

	Optional< Role > findByName( RoleName name );

	Set< Role > findByIdIn( Set< Long > ids );

}
