package rs.xml.ws.filterandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.filterandsearch.models.Brand;

@Repository
public interface BrandRepository extends JpaRepository< Brand, Long >
{

	Boolean existsByName( String name );

}
