package rs.xml.ws.appointment.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


import org.springframework.http.ResponseEntity;


import rs.xml.ws.appointment.models.Appointment;
import rs.xml.ws.appointment.models.dtos.ApiResponse;
import rs.xml.ws.appointment.models.dtos.AppointmentDTO;
import rs.xml.ws.appointment.models.dtos.SearchDTO;

public interface IService
{

	ResponseEntity< ApiResponse > makeAppointment( Long user, AppointmentDTO appointment );

	ResponseEntity< ApiResponse > cancelAppointment( Long user, Long appointment );

	ResponseEntity< ApiResponse > payAppointment( Long user, Long appointment );

	ResponseEntity< ApiResponse > existsById( Long user, Long appointment );

	ResponseEntity< Set< Appointment > > getAllMyAppointments( Long user );

	ResponseEntity< Set< Appointment > > getAllAppointmentsForCar( Long car );

	ResponseEntity< Set< Appointment > > getAllAppointmentsForCompanz( Long company );

	ResponseEntity< List< Long > > checkIfFree( SearchDTO search );

	ResponseEntity< ApiResponse > checkIfCarIsInUse( Long car );

	ResponseEntity< Set< Long > > getCustomers( Set< Long > cars );

	ResponseEntity< ApiResponse > makeCarUnavailable( Long car, Long owner, LocalDateTime start, LocalDateTime end );

}
