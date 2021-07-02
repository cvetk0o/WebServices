package rs.xml.ws.message.services.implementations;

import javax.mail.internet.MimeMessage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{

	private Logger logger = LoggerFactory.getLogger( EmailService.class );

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	public Boolean sendMessage( String to, String text, String subject )
	{

		Boolean returnValue = true;
		try
		{
			MimeMessage message = javaMailSender.createMimeMessage();

			message.setSubject( subject );
			MimeMessageHelper helper = new MimeMessageHelper( message, true );

			helper.setFrom( env.getProperty( "spring.mail.username" ) );

			helper.setTo( to );
			helper.setText( text, true );
			javaMailSender.send( message );
			logger.info( "Sent mail to :[ " + to + " ]" );
		}
		catch ( Exception e )
		{
			System.err.println( e.getMessage() );
			logger.error( "Error sending mail to :[ " + to + " ]" );
			returnValue = false;
		}
		return returnValue;

	}

}