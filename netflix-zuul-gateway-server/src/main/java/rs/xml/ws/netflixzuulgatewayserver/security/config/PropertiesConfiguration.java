package rs.xml.ws.netflixzuulgatewayserver.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties( "netflix-zuul-gateway-server" )
public class PropertiesConfiguration
{

	private String jwtSecret;

	private Long jwtExpirationInMs;

}
