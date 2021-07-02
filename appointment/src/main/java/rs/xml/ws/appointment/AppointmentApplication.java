package rs.xml.ws.appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


import brave.sampler.Sampler;

@EnableFeignClients( "rs.xml.ws.appointment" )
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan( basePackageClasses =
{ AppointmentApplication.class, Jsr310JpaConverters.class } )
public class AppointmentApplication
{

	// TODO make scheduled task that check if time has passed and appointment will
	// go from sattus PENDING to status CANCELED if not paid

	public static void main( String[] args )
	{
		SpringApplication.run( AppointmentApplication.class, args );

	}


	@Bean
	public Sampler defaultSampler()
	{
		return Sampler.ALWAYS_SAMPLE;

	}

}
