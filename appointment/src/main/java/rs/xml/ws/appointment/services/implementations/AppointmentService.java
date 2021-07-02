package rs.xml.ws.appointment.services.implementations;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import rs.xml.ws.appointment.feign.IFeignProxy;
import rs.xml.ws.appointment.models.Appointment;
import rs.xml.ws.appointment.models.AppointmentItem;
import rs.xml.ws.appointment.models.AppointmentStatus;
import rs.xml.ws.appointment.models.dtos.ApiResponse;
import rs.xml.ws.appointment.models.dtos.AppointmentDTO;
import rs.xml.ws.appointment.models.dtos.Car;
import rs.xml.ws.appointment.models.dtos.SearchDTO;
import rs.xml.ws.appointment.repositories.AppointmentItemRepository;
import rs.xml.ws.appointment.repositories.AppointmentRepository;
import rs.xml.ws.appointment.services.IService;

@Service
public class AppointmentService implements IService
{

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private AppointmentItemRepository appointmentItemRepository;

	@Autowired
	private IFeignProxy proxy;

	@Override
	public ResponseEntity< ApiResponse > makeAppointment( Long user, AppointmentDTO appointment )
	{

		logger.info( ">>>> Checking if id of car is valid" );

		Map< Long, Long > carsMap = proxy.groupCars( appointment.getCars() ).getBody();

		if ( carsMap.size() != appointment.getCars().size() )
		{
			logger.error( ">>>> Didnt find all cars" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Didnt find all cars" ), HttpStatus.BAD_REQUEST );
		}

		logger.info( ">>>> Checking validity of time" );

		if ( appointment.getEnd().isBefore( appointment.getStart() ) )
		{
			logger.error( ">>>> Start and end time are not valid" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Start and end time are not valid" ), HttpStatus.BAD_REQUEST );
		}

		Set< Appointment > problem1 = appointmentRepository.findByItemsCarIdInAndStartGreaterThanAndStartLessThanAndStatus( appointment.getCars(),
				appointment.getStart(), appointment.getEnd(), AppointmentStatus.PAID );
		Set< Appointment > problem2 = appointmentRepository.findByItemsCarIdInAndStartLessThanAndEndGreaterThanAndStatus( appointment.getCars(),
				appointment.getStart(), appointment.getEnd(), AppointmentStatus.PAID );
		Set< Appointment > problem3 = appointmentRepository.findByItemsCarIdInAndEndGreaterThanAndEndLessThanAndStatus( appointment.getCars(),
				appointment.getStart(), appointment.getEnd(), AppointmentStatus.PAID );

		if ( problem1.size() + problem2.size() + problem3.size() > 0 )
		{
			logger.error( ">>>> Not all cars are free" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Not all cars are free" ), HttpStatus.BAD_REQUEST );
		}

		// for ( Long id : appointment.getCars() )
		// {
		// if ( checkIfCarIsTaken( id, appointment.getStart(), appointment.getEnd() ) )
		// {
		// System.err.println(
		// "**************************************************************************************"
		// );
		// System.err.println(
		// "**************************************************************************************"
		// );
		// System.err.println(
		// "**************************************************************************************"
		// );
		// System.err.println(
		// "**************************************************************************************"
		// );
		// }
		// }

		List< Car > cars = proxy.getAllCars().getBody();

		if ( appointment.getBundle() )
		{
			Set< Long > companies = new HashSet< Long >();

			for ( Map.Entry< Long, Long > iterator : carsMap.entrySet() )
			{
				companies.add( iterator.getValue() );
			}

			for ( Long id : companies )
			{
				Appointment newAppointment = new Appointment();

				newAppointment.setCompany( id );
				newAppointment.setEnd( appointment.getEnd() );
				newAppointment.setStart( appointment.getEnd() );
				newAppointment.setStartingPointLocation( appointment.getLocation() );
				newAppointment.setStatus( AppointmentStatus.PENDING );
				newAppointment.setUserId( user );

				for ( Map.Entry< Long, Long > iterator : carsMap.entrySet() )
				{
					if ( id == iterator.getValue() )
					{
						// its from this ocmpan
						AppointmentItem newItem = new AppointmentItem();
						Set< Car > collect = cars.stream().filter( c -> c.getId() == iterator.getKey() ).collect( Collectors.toSet() );
						newItem.setCarId( iterator.getKey() );
						Iterator< Car > c = collect.iterator();
						Car ccc = ( Car ) c.next();

						newItem.setMileageEnd( -1.0 );
						newItem.setMileageStart( ccc.getMileage() );
						newItem.setOwner( ccc.getOwner() );
						appointmentItemRepository.save( newItem );
						newAppointment.getItems().add( newItem );

					}
				}

				logger.info( ">>>> Saving appointment" );
				appointmentRepository.save( newAppointment );

			}
		}
		else
		{
			for ( Long idCar : appointment.getCars() )
			{
				Appointment newAppointment = new Appointment();

				// set company id and car id
				newAppointment.setEnd( appointment.getEnd() );
				newAppointment.setStart( appointment.getEnd() );
				newAppointment.setStartingPointLocation( appointment.getLocation() );
				newAppointment.setStatus( AppointmentStatus.PENDING );
				newAppointment.setUserId( user );

				Set< Car > collect = cars.stream().filter( c -> c.getId() == idCar ).collect( Collectors.toSet() );

				Iterator< Car > iterator = collect.iterator();
				Car next = iterator.next();

				newAppointment.setCompany( next.getCompany() );

				AppointmentItem item = new AppointmentItem();

				item.setCarId( idCar );
				item.setMileageEnd( -1.0 );
				item.setMileageStart( next.getMileage() );
				item.setOwner( next.getOwner() );

				appointmentItemRepository.save( item );

				newAppointment.getItems().add( item );

				appointmentRepository.save( newAppointment );

			}
		}

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Created" ), HttpStatus.CREATED );

	}


	@SuppressWarnings( "unused" )
	private Boolean checkIfCarIsTaken( Long car, LocalDateTime start, LocalDateTime end )
	{
		Set< Appointment > problem1 = appointmentRepository.findByItemsCarIdAndStartGreaterThanAndStartLessThan( car, start, end );

		Set< Appointment > problem2 = appointmentRepository.findByItemsCarIdAndStartLessThanAndEndGreaterThan( car, start, end );

		Set< Appointment > problem3 = appointmentRepository.findByItemsCarIdAndEndGreaterThanAndEndLessThan( car, start, end );

		return ( problem1.size() + problem2.size() + problem3.size() ) != 0; // if != then true, thah means it is in use!!!!

	}


	@Override
	public ResponseEntity< ApiResponse > cancelAppointment( Long user, Long appointment )
	{

		logger.info( ">>>> Checking if id of appointment is valid" );

		Optional< Appointment > appointmentOptional = appointmentRepository.findById( appointment );

		if ( !appointmentOptional.isPresent() )
		{
			logger.error( ">>>> Cant find appointment" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find appointment" ), HttpStatus.BAD_REQUEST );
		}

		Appointment appointment2cancel = appointmentOptional.get();

		if ( appointment2cancel.getUserId() != user )
		{
			logger.error( ">>>> This is not appointment in your name" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "This is not yours appointment" ), HttpStatus.BAD_REQUEST );
		}

		appointment2cancel.setStatus( AppointmentStatus.CANCELED );
		appointmentRepository.save( appointment2cancel );

		logger.info( ">>>> Removing appointment" );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Canceles" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > payAppointment( Long user, Long appointment )
	{
		Optional< Appointment > appointmentOptional = appointmentRepository.findById( appointment );

		if ( !appointmentOptional.isPresent() )
		{
			logger.error( ">>>> Cant find appointment" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find appointment" ), HttpStatus.BAD_REQUEST );
		}

		Appointment appointment2pay = appointmentOptional.get();

		if ( appointment2pay.getUserId() != user )
		{
			logger.error( ">>>> This is not appointment in your name" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "This is not yours appointment" ), HttpStatus.BAD_REQUEST );
		}

		appointment2pay.setStatus( AppointmentStatus.PAID );

		appointmentRepository.save( appointment2pay );

		logger.info( ">>>> Saving appointment" );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Paid" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Appointment > > getAllMyAppointments( Long user )
	{

		logger.info( ">>>> Retriving appointments" );
		return new ResponseEntity< Set< Appointment > >( appointmentRepository.findByUserId( user ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Appointment > > getAllAppointmentsForCar( Long car )
	{
		logger.info( ">>>> Retriving appointments" );
		return new ResponseEntity< Set< Appointment > >( appointmentRepository.findByItemsCarId( car ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Appointment > > getAllAppointmentsForCompanz( Long company )
	{
		logger.info( ">>>> Retriving appointments" );
		return new ResponseEntity< Set< Appointment > >( appointmentRepository.findByCompany( company ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > existsById( Long id, Long car )
	{

		Set< Appointment > appointments = appointmentRepository.findByUserIdAndItemsCarId( id, car );

		if ( appointments.size() < 1 )
		{
			logger.error( ">>>> There is no such appointment with user {} and car {}", id, car );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "This user didn't use this car!" ), HttpStatus.OK );
		}

		Set< Appointment > collect = appointments.stream().filter( a -> a.getEnd().isBefore( LocalDateTime.now() ) ).collect( Collectors.toSet() );

		if ( collect.size() > 0 )
		{

			logger.info( ">>>> There is appointment with user {} and car {}", id, car );
			return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" ), HttpStatus.OK );
		}

		logger.error( ">>>> There is no such appointment with user {} and car {}", id, car );
		return new ResponseEntity< ApiResponse >( new ApiResponse( false, "User didn't finished appointment!" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< List< Long > > checkIfFree( SearchDTO search )
	{
		// TODO Auto-generated method stub
		return null;

	}


	@Override
	public ResponseEntity< ApiResponse > checkIfCarIsInUse( Long car )
	{
		Set< Appointment > appointments = appointmentRepository.findByItemsCarId( car );
		return new ResponseEntity< ApiResponse >( new ApiResponse( !appointments.isEmpty(), "" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Long > > getCustomers( Set< Long > cars )
	{

		Set< Appointment > myAppoitnments = appointmentRepository.findByItemsCarIdIn( cars );

		return new ResponseEntity< Set< Long > >( myAppoitnments.stream().map( a -> a.getUserId() ).collect( Collectors.toSet() ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > makeCarUnavailable( Long car, Long owner, LocalDateTime start, LocalDateTime end )
	{

		ResponseEntity< ApiResponse > filterResponse = proxy.checkCarsOwner( car, owner );

		if ( !filterResponse.getBody().getSuccess() )
		{
			logger.error( ">>>> This is not yours car" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, filterResponse.getBody().getMessage() ), HttpStatus.BAD_REQUEST );

		}

		Appointment newAppointment = new Appointment();

		newAppointment.setCompany( -1l );
		newAppointment.setEnd( end );
		newAppointment.setStart( start );

		newAppointment.setUserId( -1l );
		newAppointment.setStatus( AppointmentStatus.PAID );
		newAppointment.setStartingPointLocation( "MANUAL RENT" );

		AppointmentItem item = new AppointmentItem();

		item.setCarId( car );
		item.setMileageStart( Double.parseDouble( filterResponse.getBody().getMessage() ) );
		item.setMileageEnd( -1.0 );

		appointmentItemRepository.save( item );

		newAppointment.getItems().add( item );

		appointmentRepository.save( newAppointment );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "OK" ), HttpStatus.OK );

	}

}
