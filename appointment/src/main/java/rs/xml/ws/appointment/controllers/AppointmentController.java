package rs.xml.ws.appointment.controllers;

import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.appointment.models.Appointment;
import rs.xml.ws.appointment.models.dtos.ApiResponse;
import rs.xml.ws.appointment.models.dtos.AppointmentDTO;
import rs.xml.ws.appointment.models.dtos.SearchDTO;
import rs.xml.ws.appointment.services.IService;

@RestController
public class AppointmentController
{

	@Autowired
	private IService service;

	@GetMapping( "/appointment" )
	public ResponseEntity< Set< Appointment > > getMyAppointments( @RequestHeader( required = true, value = "id" ) String id )
	{
		return service.getAllMyAppointments( Long.parseLong( id ) );

	}


	@PutMapping( "/appointment/{appointment}" )
	public ResponseEntity< ApiResponse > payAppointment( @RequestHeader( required = true, value = "id" ) String id, @PathVariable Long appointment )
	{
		return service.payAppointment( Long.parseLong( id ), appointment );

	}


	@DeleteMapping( "/appointment/{appointment}" )
	public ResponseEntity< ApiResponse > cancelAppointment( @RequestHeader( required = true, value = "id" ) String id, @PathVariable Long appointment )
	{
		return service.cancelAppointment( Long.parseLong( id ), appointment );

	}


	@PostMapping( "/appointment" )
	public ResponseEntity< ApiResponse > createAppointment( @RequestHeader( required = true, value = "id" ) String id, @RequestBody AppointmentDTO ap )
	{
		return service.makeAppointment( Long.parseLong( id ), ap );

	}


	@GetMapping( "/exists/{user}/{car}" )
	public ResponseEntity< ApiResponse > existsById( @PathVariable Long user, @PathVariable Long car )
	{
		return service.existsById( user, car );

	}


	@PostMapping( "/check" )
	public ResponseEntity< List< Long > > checkIfFree( @RequestBody SearchDTO search )
	{
		return service.checkIfFree( search );

	}


	@GetMapping( "/used/{id}" )
	public ResponseEntity< ApiResponse > checkIfCarIsInUse( @PathVariable Long id )
	{
		return service.checkIfCarIsInUse( id );

	}


	@PostMapping( "/customers" )
	public ResponseEntity< Set< Long > > getCustomers( @RequestBody Set< Long > cars )
	{
		return service.getCustomers( cars );

	}


	@PostMapping( "/schedule/car" )
	public ResponseEntity< ApiResponse > scheduleCar( @RequestHeader( required = true, value = "id" ) String id, @RequestBody AppointmentDTO appointment )
	{
		return service.makeCarUnavailable( appointment.getCar(), Long.parseLong( id ), appointment.getStart(), appointment.getEnd() );

	}

}
