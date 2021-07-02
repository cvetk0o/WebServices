package rs.xml.ws.filterandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.filterandsearch.models.Transmission;

@Repository
public interface TransmissionRepository extends JpaRepository< Transmission, Long >
{

	Boolean existsByName( String name );

}
