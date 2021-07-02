package rs.xml.ws.appointment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.appointment.models.AppointmentItem;

@Repository
public interface AppointmentItemRepository extends JpaRepository< AppointmentItem, Long >
{

}
