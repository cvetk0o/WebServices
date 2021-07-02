package rs.xml.ws.filterandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.filterandsearch.models.CarClass;

@Repository
public interface CarClassRepository extends JpaRepository< CarClass, Long >
{

	Boolean existsByName( String name );

}
