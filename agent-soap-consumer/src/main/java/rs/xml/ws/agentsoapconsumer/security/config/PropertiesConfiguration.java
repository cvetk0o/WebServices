package rs.xml.ws.agentsoapconsumer.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties( "agent-soap-consumer" )
public class PropertiesConfiguration
{

	private String jwtSecret;

	private Long jwtExpirationInMs;

}
