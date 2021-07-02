package rs.xml.ws.netflixeurekanamingserver;

import java.io.IOException;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class NetflixEurekaNamingServerApplication
{

	public static void main( String[] args ) throws IOException
	{
		SpringApplication.run( NetflixEurekaNamingServerApplication.class, args );

	}

}
