package rs.xml.ws.agentsoapconsumer.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


import rs.xml.ws.agentsoapconsumer.payload.AppointmentDTO;
import rs.xml.ws.agentsoapconsumer.soap.AllAppointmentsRequest;
import rs.xml.ws.agentsoapconsumer.soap.AllAppointmentsResponse;
import rs.xml.ws.agentsoapconsumer.soap.CreateAppointmentRequest;
import rs.xml.ws.agentsoapconsumer.soap.CreateAppointmentResponse;

public class AppointmentClient extends WebServiceGatewaySupport
{

	public AllAppointmentsResponse synchAppointments( Long company )
	{

		AllAppointmentsRequest request = new AllAppointmentsRequest();

		request.setCompany( company );

		AllAppointmentsResponse response = ( AllAppointmentsResponse ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.APPOINTMENT, request );

		return response;

	}


	public CreateAppointmentResponse createAppointment( AppointmentDTO appointment )
	{
		// TODO
		CreateAppointmentRequest request = new CreateAppointmentRequest();

		CreateAppointmentResponse response = ( CreateAppointmentResponse ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.APPOINTMENT, request );

		return response;

	}

}
