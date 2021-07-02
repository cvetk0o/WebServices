package rs.xml.ws.agentsoapconsumer.security;

import java.util.Date;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import rs.xml.ws.agentsoapconsumer.models.JwtToken;
import rs.xml.ws.agentsoapconsumer.repositories.JwtTokenRepository;
import rs.xml.ws.agentsoapconsumer.security.config.PropertiesConfiguration;

@Component
public class JwtTokenProvider
{

	private static final Logger logger = LoggerFactory.getLogger( JwtTokenProvider.class );

	@Autowired
	private PropertiesConfiguration configuration;

	@Autowired
	private JwtTokenRepository tokenRepository;

	public String generateToken( Authentication authentication )
	{

		UserPrincipal userPrincipal = ( UserPrincipal ) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date( now.getTime() + configuration.getJwtExpirationInMs() );

		String token = Jwts.builder().setSubject( Long.toString( userPrincipal.getId() ) ).setIssuedAt( new Date() ).setExpiration( expiryDate )
				.signWith( SignatureAlgorithm.HS512, configuration.getJwtSecret() ).compact();

		tokenRepository.save( new JwtToken( token ) );

		logger.info( ">>>> Finished generating token" );

		return token;

	}


	public void suspendToken( String token )
	{

		Optional< JwtToken > token2delete = tokenRepository.findByToken( token.substring( 7, token.length() ) );

		if ( token2delete.isPresent() )
		{
			tokenRepository.delete( token2delete.get() );
		}

		return;

	}


	public Long getUserIdFromJWT( String token )
	{
		Claims claims = Jwts.parser().setSigningKey( configuration.getJwtSecret() ).parseClaimsJws( token ).getBody();

		return Long.parseLong( claims.getSubject() );

	}


	public Date getExpirationFromJWT( String token )
	{
		Claims claims = Jwts.parser().setSigningKey( configuration.getJwtSecret() ).parseClaimsJws( token ).getBody();

		return claims.getExpiration();

	}


	public boolean validateToken( String authToken )
	{

		Optional< JwtToken > optionalToken = tokenRepository.findById( authToken );

		if ( !optionalToken.isPresent() )
		{
			return false;
		}

		try
		{
			Jwts.parser().setSigningKey( configuration.getJwtSecret() ).parseClaimsJws( authToken );
			return true;
		}
		catch ( SignatureException ex )
		{
			logger.error( "Invalid JWT signature" );
		}
		catch ( MalformedJwtException ex )
		{
			logger.error( "Invalid JWT token" );
		}
		catch ( ExpiredJwtException ex )
		{
			logger.error( "Expired JWT token" );
		}
		catch ( UnsupportedJwtException ex )
		{
			logger.error( "Unsupported JWT token" );
		}
		catch ( IllegalArgumentException ex )
		{
			logger.error( "JWT claims string is empty." );
		}
		return false;

	}

}
