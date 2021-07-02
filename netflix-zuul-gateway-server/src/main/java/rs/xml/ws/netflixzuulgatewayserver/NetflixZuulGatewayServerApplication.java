package rs.xml.ws.netflixzuulgatewayserver;

import java.util.TimeZone;


import javax.annotation.PostConstruct;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import brave.sampler.Sampler;

@EntityScan( basePackageClasses =
{ NetflixZuulGatewayServerApplication.class, Jsr310JpaConverters.class } )
@EnableZuulProxy
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan( "rs.xml.ws.netflixzuulgatewayserver" )
public class NetflixZuulGatewayServerApplication
{

	@PostConstruct
	void init()
	{
		TimeZone.setDefault( TimeZone.getTimeZone( "GMT+1" ) );

	}


	public static void main( String[] args )
	{
		SpringApplication.run( NetflixZuulGatewayServerApplication.class, args );

	}


	@Bean
	public Sampler defaultSampler()
	{
		return Sampler.ALWAYS_SAMPLE;

	}

}
