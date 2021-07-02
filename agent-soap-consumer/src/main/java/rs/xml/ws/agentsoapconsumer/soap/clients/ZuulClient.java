package rs.xml.ws.agentsoapconsumer.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


import rs.xml.ws.agentsoapconsumer.soap.GetAllUsersRequest;
import rs.xml.ws.agentsoapconsumer.soap.GetAllUsersResponse;

public class ZuulClient extends WebServiceGatewaySupport
{

	public GetAllUsersResponse synchUsers()
	{
		GetAllUsersRequest request = new GetAllUsersRequest();

		GetAllUsersResponse response = ( GetAllUsersResponse ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.ZUUL, request );

		return response;

	}

}
