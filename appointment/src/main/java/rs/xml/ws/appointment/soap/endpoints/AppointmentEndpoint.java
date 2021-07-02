package rs.xml.ws.appointment.soap.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import rs.xml.ws.appointment.soap.AllAppointmentsRequest;
import rs.xml.ws.appointment.soap.AllAppointmentsResponse;
import rs.xml.ws.appointment.soap.CreateAppointmentRequest;
import rs.xml.ws.appointment.soap.CreateAppointmentResponse;

@Endpoint
public class AppointmentEndpoint
{

	private static final String NAMESPACE_URI = "www.rent-a-car.ws.xml.rs/appointment";

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	// @Autowired
	// private IFilterAndSearchFeignProxy proxyFilter;

	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "allAppointmentsRequest" )
	@ResponsePayload
	public AllAppointmentsResponse synchAppointments( @RequestPayload AllAppointmentsRequest request )
	{

		logger.info( ">>>> Retriving all appointments where company === {}", request.getCompany() );

		return new AllAppointmentsResponse();

	}


	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "createAppointmentRequest" )
	@ResponsePayload
	public CreateAppointmentResponse createAppointment( @RequestPayload CreateAppointmentRequest request )
	{

		logger.info( ">>>> Checking if user with id {}exists", request.getAppointment().getUserId() );

		// ResponseEntity< ApiResponse > zuulResponse = proxyZuul.existsById(
		// request.getAppointment().getUserId() );
		// System.err.println( zuulResponse.getBody().getMessage() );
		//
		// ResponseEntity< ApiResponse > filterResponse = proxyFilter.checkCarsExists(
		// null, 1L );
		// System.err.println( filterResponse.getBody().getMessage() );

		logger.info( ">>>> Checking if cars exist" );

		return new CreateAppointmentResponse();

	}

}
