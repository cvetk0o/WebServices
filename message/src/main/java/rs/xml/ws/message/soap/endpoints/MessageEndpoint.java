package rs.xml.ws.message.soap.endpoints;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import rs.xml.ws.message.models.Message;
import rs.xml.ws.message.repositories.MessageRepository;
import rs.xml.ws.message.soap.AllMessagesRequest;
import rs.xml.ws.message.soap.CreateMessageRequest;
import rs.xml.ws.message.soap.CreateMessageResponse;
import rs.xml.ws.message.soap.MessageXML;
import rs.xml.ws.message.soap.MessagesResponse;

@Endpoint
public class MessageEndpoint
{

	private static final String NAMESPACE_URI = "www.rent-a-car.ws.xml.rs/message";

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private MessageRepository messageRepository;

	// @Autowired
	// private IAppointmentFeignProxy proxyAppointment;

	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "allMessagesRequest" )
	@ResponsePayload
	public MessagesResponse synchMessages( @RequestPayload AllMessagesRequest request )
	{

		logger.info( ">>>> Retriving all messages for campany with id {}", request.getCompany() );

		Set< Message > messages = messageRepository.findByCompany( request.getCompany() );

		MessagesResponse response = new MessagesResponse();

		logger.info( ">>>> Creating xml messages" );
		for ( Message m : messages )
		{

			MessageXML messageXML = new MessageXML();

			messageXML.setId( m.getId() );
			messageXML.setCreatedAt( m.getCreatedAt().toString() );
			messageXML.setMessage( m.getMessage() );
			messageXML.setReceiver( m.getReceiver() );
			messageXML.setSender( m.getSender() );
			messageXML.setSubject( m.getSubject() );
			response.getMessages().add( messageXML );

			response.getMessages().add( messageXML );

		}
		logger.info( ">>>> Returning response with messages " );
		return response;

	}


	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "createMessageRequest" )
	@ResponsePayload
	public CreateMessageResponse createMessage( @RequestPayload CreateMessageRequest request )
	{

		CreateMessageResponse response = new CreateMessageResponse();
		logger.info( ">>>> Checking if user, agent and company id's are valid" );

		// ResponseEntity< ApiResponse > appointmentResponse =
		// proxyAppointment.existsById( request.getMessage().getAppointmentId() );
		//
		// ResponseEntity< ApiResponse > zuulResponse = proxyZuul.existsById(
		// request.getMessage().getReceiver() );

		// if ( !zuulResponse.getBody().getSuccess() )
		// {
		// logger.error( ">>>> Reciever is not valid" );
		// response.setSuccess( false );
		// response.setMessage( "User you are trying to message is not valid" );
		// return response;
		// }
		//
		// if ( !appointmentResponse.getBody().getSuccess() )
		// {
		// response.setSuccess( false );
		// response.setMessage( appointmentResponse.getBody().getMessage() );
		// return response;
		// }

		logger.info( ">>>> Checking if agent have reason to message user" );

		logger.info( ">>>> Creating new message" );
		Message newMessage = new Message();
		MessageXML message = request.getMessage();

		newMessage.setCreatedAt( LocalDateTime.parse( message.getCreatedAt(), DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" ) ) );
		newMessage.setMessage( message.getMessage() );
		newMessage.setReceiver( message.getReceiver() );
		newMessage.setSender( message.getSender() );
		newMessage.setSubject( message.getSubject() );
		newMessage.setCompany( message.getCompanyId() );

		Message save = messageRepository.save( newMessage );

		response.setSuccess( true );
		response.setMessage( save.getId().toString() );

		logger.info( ">>>> New message created" );
		return response;

	}

}
