package rs.xml.ws.appointment.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.appointment.models.Appointment;
import rs.xml.ws.appointment.models.AppointmentStatus;

@Repository
public interface AppointmentRepository extends JpaRepository< Appointment, Long >
{

	Set< Appointment > findByUserIdAndItemsCarIdAndStatus( Long user, Long car, AppointmentStatus status );

	Optional< Appointment > findByIdAndUserId( Long user, Long appointment );

	Set< Appointment > findByUserIdAndItemsCarId( Long user, Long car );

	Set< Appointment > findByItemsCarId( Long car );

	Set< Appointment > findByItemsCarIdIn( Set< Long > cars );

	Set< Appointment > findByItemsCarIdAndStartGreaterThanAndStartLessThan( Long car, LocalDateTime val1, LocalDateTime val2 );

	Set< Appointment > findByItemsCarIdAndStartLessThanAndEndGreaterThan( Long car, LocalDateTime val1, LocalDateTime val2 );

	Set< Appointment > findByItemsCarIdAndEndGreaterThanAndEndLessThan( Long car, LocalDateTime val1, LocalDateTime val2 );

	Set< Appointment > findByItemsCarIdInAndStartGreaterThanAndStartLessThanAndCompanyAndUserId( List< Long > cars, LocalDateTime val1, LocalDateTime val2,
			Long user, Long company );

	Set< Appointment > findByItemsCarIdInAndStartLessThanAndEndGreaterThanAndCompanyAndUserId( List< Long > cars, LocalDateTime val1, LocalDateTime val2,
			Long user, Long company );

	Set< Appointment > findByItemsCarIdInAndEndGreaterThanAndEndLessThanAndCompanyAndUserId( List< Long > cars, LocalDateTime val1, LocalDateTime val2,
			Long user, Long company );

	Set< Appointment > findByItemsCarIdInAndStartGreaterThanAndStartLessThanAndStatus( List< Long > cars, LocalDateTime val1, LocalDateTime val2,
			AppointmentStatus status );

	Set< Appointment > findByItemsCarIdInAndStartLessThanAndEndGreaterThanAndStatus( List< Long > cars, LocalDateTime val1, LocalDateTime val2,
			AppointmentStatus status );

	Set< Appointment > findByItemsCarIdInAndEndGreaterThanAndEndLessThanAndStatus( List< Long > cars, LocalDateTime val1, LocalDateTime val2,
			AppointmentStatus status );

	Set< Appointment > findByUserId( Long id );

	Set< Appointment > findByCompany( Long id );

}
