package rs.xml.ws.message.services;

import java.util.Set;


import org.springframework.http.ResponseEntity;


import rs.xml.ws.message.models.Message;
import rs.xml.ws.message.models.dtos.ApiResponse;
import rs.xml.ws.message.models.dtos.MailDTO;
import rs.xml.ws.message.models.dtos.MessageDTO;

public interface IMessageService
{

	ResponseEntity< Set< Message > > getAllUsersMessages( Long id );

	ResponseEntity< Set< Message > > getMessagesBySender( Long id );

	ResponseEntity< Set< Message > > getMessagesByReceiver( Long id );

	ResponseEntity< Set< Message > > getConversation( Long id1, Long id2, String subject );

	ResponseEntity< ApiResponse > createMessage( MessageDTO message );

	ResponseEntity< ApiResponse > sendMail( MailDTO mail );

}
