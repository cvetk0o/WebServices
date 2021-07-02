package rs.xml.ws.agentsoapconsumer.repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.agentsoapconsumer.models.Role;
import rs.xml.ws.agentsoapconsumer.models.RoleName;

@Repository
public interface RoleRepository extends JpaRepository< Role, Long >
{

	Optional< Role > findByName( RoleName name );

}
