package rs.xml.ws.message.controllers;

import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.message.models.Message;
import rs.xml.ws.message.models.dtos.ApiResponse;
import rs.xml.ws.message.models.dtos.MailDTO;
import rs.xml.ws.message.models.dtos.MessageDTO;
import rs.xml.ws.message.services.IMessageService;

@RestController
public class MessageController
{

	@Autowired
	private IMessageService service;

	@GetMapping( "/message" )
	public ResponseEntity< Set< Message > > getMessage( @RequestHeader( required = true, value = "id" ) String id )
	{

		return service.getAllUsersMessages( Long.parseLong( id ) );

	}


	@GetMapping( "/message/sender" )
	public ResponseEntity< Set< Message > > getBySender( @RequestHeader( required = true, value = "id" ) String id )
	{
		return service.getMessagesBySender( Long.parseLong( id ) );

	}


	@GetMapping( "/message/reciever" )
	public ResponseEntity< Set< Message > > getByReciever( @RequestHeader( required = true, value = "id" ) String id )
	{
		return service.getMessagesByReceiver( Long.parseLong( id ) );

	}


	@GetMapping( "/message/from/{from}/to/{to}/subject/{subject}" )
	public ResponseEntity< Set< Message > > getChat( @PathVariable Long from, @PathVariable Long to, @PathVariable String subject )
	{
		return service.getConversation( to, from, subject );

	}


	@PostMapping( "/message" )
	public ResponseEntity< ApiResponse > createMessage( @RequestBody MessageDTO message )
	{
		return service.createMessage( message );

	}


	@PostMapping( "/mail" )
	public ResponseEntity< ApiResponse > sendMail( @RequestBody MailDTO mail )
	{
		return service.sendMail( mail );

	}

}
