package rs.xml.ws.agentsoapconsumer.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


import rs.xml.ws.agentsoapconsumer.payload.MessageDTO;
import rs.xml.ws.agentsoapconsumer.soap.AllMessagesRequest;
import rs.xml.ws.agentsoapconsumer.soap.CreateMessageRequest;
import rs.xml.ws.agentsoapconsumer.soap.CreateMessageResponse;
import rs.xml.ws.agentsoapconsumer.soap.MessageXML;
import rs.xml.ws.agentsoapconsumer.soap.MessagesResponse;

public class MessageClient extends WebServiceGatewaySupport
{

	public MessagesResponse synchMessages( Long company )
	{
		AllMessagesRequest request = new AllMessagesRequest();

		request.setCompany( company );

		MessagesResponse response = ( MessagesResponse ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.MESSAGE, request );

		return response;

	}


	public CreateMessageResponse createMessage( MessageDTO message, Long company )
	{
		CreateMessageRequest request = new CreateMessageRequest();

		MessageXML newMessage = new MessageXML();

		newMessage.setCompanyId( company );

		request.setMessage( newMessage );

		CreateMessageResponse response = ( CreateMessageResponse ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.MESSAGE, request );

		return response;

	}

}
