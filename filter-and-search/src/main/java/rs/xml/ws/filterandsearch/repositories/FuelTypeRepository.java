package rs.xml.ws.filterandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.filterandsearch.models.FuelType;

@Repository
public interface FuelTypeRepository extends JpaRepository< FuelType, Long >
{

	Boolean existsByName( String name );

}
