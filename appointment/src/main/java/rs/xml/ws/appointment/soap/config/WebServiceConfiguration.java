package rs.xml.ws.appointment.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfiguration
{

	@Bean
	public ServletRegistrationBean< MessageDispatcherServlet > messageDispatcherServlet( ApplicationContext applicationContext )
	{
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext( applicationContext );
		servlet.setTransformWsdlLocations( true );
		return new ServletRegistrationBean< MessageDispatcherServlet >( servlet, "/ws/*" );

	}


	@Bean( name = "appointment" )
	public DefaultWsdl11Definition defaultWsdl11Definition( XsdSchema schema )
	{
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName( "AppointmentPort" );
		wsdl11Definition.setLocationUri( "/ws" );
		wsdl11Definition.setTargetNamespace( "www.rent-a-car.ws.xml.rs/appointment" );
		wsdl11Definition.setSchema( schema );
		return wsdl11Definition;

	}


	@Bean
	public XsdSchema schema()
	{
		return new SimpleXsdSchema( new ClassPathResource( "appointment.xsd" ) );

	}

}
