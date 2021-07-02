package rs.xml.ws.netflixzuulgatewayserver.repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.netflixzuulgatewayserver.models.JwtToken;

@Repository
public interface JwtTokenRepository extends JpaRepository< JwtToken, String >
{

	Optional< JwtToken > findByToken( String token );

}
