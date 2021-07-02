package rs.xml.ws.agentsoapconsumer;

import java.util.TimeZone;


import javax.annotation.PostConstruct;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan( basePackageClasses =
{ AgentSoapConsumerApplication.class, Jsr310JpaConverters.class } )
@ComponentScan( "rs.xml.ws.agentsoapconsumer" )
@SpringBootApplication
public class AgentSoapConsumerApplication
{

	@PostConstruct
	void init()
	{
		TimeZone.setDefault( TimeZone.getTimeZone( "GMT+1" ) );

	}


	public static void main( String[] args )
	{
		SpringApplication.run( AgentSoapConsumerApplication.class, args );

	}

}
