package rs.xml.ws.message.services.implementations;

import java.time.LocalDateTime;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import rs.xml.ws.message.feign.IFeignProxy;
import rs.xml.ws.message.models.Message;
import rs.xml.ws.message.models.dtos.ApiResponse;
import rs.xml.ws.message.models.dtos.MailDTO;
import rs.xml.ws.message.models.dtos.MessageDTO;
import rs.xml.ws.message.repositories.MessageRepository;
import rs.xml.ws.message.services.IMessageService;

@Service
public class MessageService implements IMessageService
{

	@Autowired
	private MessageRepository repo;

	@Autowired
	private IFeignProxy proxy;

	@Autowired
	private EmailService mailService;

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Override
	public ResponseEntity< Set< Message > > getMessagesBySender( Long id )
	{
		logger.info( ">>>> Retriving messages by sender" );

		return new ResponseEntity< Set< Message > >( repo.findBySender( id ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Message > > getMessagesByReceiver( Long id )
	{
		logger.info( ">>>> Retriving messages by receiver" );

		return new ResponseEntity< Set< Message > >( repo.findByReceiver( id ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Message > > getConversation( Long id1, Long id2, String subject )
	{

		logger.info( ">>>> Retriving messages" );

		Set< Message > messages = repo.findByReceiverAndSenderAndSubject( id1, id2, subject );

		messages.addAll( repo.findByReceiverAndSenderAndSubject( id2, id1, subject ) );

		logger.info( ">>>> Messages retrived" );

		return new ResponseEntity< Set< Message > >( messages, HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > createMessage( MessageDTO message )
	{
		logger.info( ">>>> Checking user" );
		ResponseEntity< ApiResponse > zuulResponse;

		// sender is ok, check for company and for receiver
		if ( message.getReceiver() != -1l )
		{
			zuulResponse = proxy.existsByIdUser( message.getReceiver() );

		}
		else
		{
			zuulResponse = proxy.getCompanyEmail( message.getCompany() );
		}

		if ( !zuulResponse.getBody().getSuccess() )
		{
			logger.error( ">>>> Message cant be sent" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant send message, reciever or company is not valid" ), HttpStatus.BAD_REQUEST );
		}

		logger.info( ">>>> Creating new message" );
		Message newMessage = new Message();

		newMessage.setMessage( message.getMessage() );
		newMessage.setReceiver( message.getReceiver() );
		newMessage.setSender( message.getSender() );
		newMessage.setCreatedAt( LocalDateTime.now() );
		newMessage.setSubject( message.getSubject() );
		newMessage.setCompany( message.getCompany() );

		logger.info( ">>>> Saving new message" );
		Message save = repo.save( newMessage );
		logger.info( ">>>> Saved new message" );
		return new ResponseEntity< ApiResponse >( new ApiResponse( true, save.getId().toString() ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Message > > getAllUsersMessages( Long id )
	{
		Set< Message > messages = repo.findByReceiver( id );

		messages.addAll( repo.findBySender( id ) );
		return new ResponseEntity< Set< Message > >( messages, HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > sendMail( MailDTO mail )
	{

		Boolean sendMessage = mailService.sendMessage( mail.getEmail(), mail.getMessage(), mail.getSubject() );

		return new ResponseEntity< ApiResponse >( new ApiResponse( sendMessage, "" ), HttpStatus.OK );

	}

}
