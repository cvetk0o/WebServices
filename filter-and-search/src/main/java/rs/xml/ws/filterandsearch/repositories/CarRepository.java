package rs.xml.ws.filterandsearch.repositories;

import java.util.List;
import java.util.Set;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.filterandsearch.models.Car;

@Repository
public interface CarRepository extends JpaRepository< Car, Long >
{

	Set< Car > findByIdIn( List< Long > ids );

	Set< Car > findByCompany( Long company );

	Set< Car > findByOwnerAndCompany( Long owner, Long company );

	boolean existsByBrandId( Long id );

	boolean existsByCarClassId( Long id );

	boolean existsByFuelTypeId( Long id );

	boolean existsByModelId( Long id );

	boolean existsByTransmissionId( Long id );

	Boolean existsByPlateNumber( String plate );

	Set< Car > findByOwner( Long id );

}
