package rs.xml.ws.message;

import java.util.TimeZone;


import javax.annotation.PostConstruct;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


import brave.sampler.Sampler;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients( "rs.xml.ws.message" )
@EntityScan( basePackageClasses =
{ MessageApplication.class, Jsr310JpaConverters.class } )
public class MessageApplication
{

	@PostConstruct
	void init()
	{
		TimeZone.setDefault( TimeZone.getTimeZone( "GMT+1" ) );

	}


	public static void main( String[] args )
	{
		SpringApplication.run( MessageApplication.class, args );

	}


	@Bean
	Sampler defaultSampler()
	{
		return Sampler.ALWAYS_SAMPLE;

	}

}
