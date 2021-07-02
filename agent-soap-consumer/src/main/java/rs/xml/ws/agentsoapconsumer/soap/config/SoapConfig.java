package rs.xml.ws.agentsoapconsumer.soap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


import rs.xml.ws.agentsoapconsumer.soap.clients.AppointmentClient;
import rs.xml.ws.agentsoapconsumer.soap.clients.FilterAndSearchClient;
import rs.xml.ws.agentsoapconsumer.soap.clients.MessageClient;
import rs.xml.ws.agentsoapconsumer.soap.clients.NameSpaceURI;
import rs.xml.ws.agentsoapconsumer.soap.clients.RatingAndCommentClient;
import rs.xml.ws.agentsoapconsumer.soap.clients.ZuulClient;

@Configuration
public class SoapConfig
{

	@Bean
	public Jaxb2Marshaller marshaller()
	{
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath( "rs.xml.ws.agentsoapconsumer.soap" );
		return marshaller;

	}


	@Bean
	public RatingAndCommentClient ratingAndCommentClient( Jaxb2Marshaller marshaller )
	{

		RatingAndCommentClient client = new RatingAndCommentClient();

		client.setDefaultUri( NameSpaceURI.RATING_AND_COMMENT );
		client.setMarshaller( marshaller );
		client.setUnmarshaller( marshaller );

		return client;

	}


	@Bean
	public ZuulClient zuulClient( Jaxb2Marshaller marshaller )
	{
		ZuulClient client = new ZuulClient();

		client.setDefaultUri( NameSpaceURI.ZUUL );
		client.setMarshaller( marshaller );
		client.setUnmarshaller( marshaller );

		return client;

	}


	@Bean
	public MessageClient messageClient( Jaxb2Marshaller marshaller )
	{
		MessageClient client = new MessageClient();

		client.setDefaultUri( NameSpaceURI.MESSAGE );
		client.setMarshaller( marshaller );
		client.setUnmarshaller( marshaller );

		return client;

	}


	@Bean
	public FilterAndSearchClient filterAndSearchClient( Jaxb2Marshaller marshaller )
	{
		FilterAndSearchClient client = new FilterAndSearchClient();

		client.setDefaultUri( NameSpaceURI.FILTER_AND_SEARCH );
		client.setMarshaller( marshaller );
		client.setUnmarshaller( marshaller );

		return client;

	}


	@Bean
	public AppointmentClient appointmentClient( Jaxb2Marshaller marshaller )
	{
		AppointmentClient client = new AppointmentClient();

		client.setDefaultUri( NameSpaceURI.APPOINTMENT );
		client.setMarshaller( marshaller );
		client.setUnmarshaller( marshaller );

		return client;

	}

}
