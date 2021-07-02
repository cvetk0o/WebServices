package rs.xml.ws.filterandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.filterandsearch.models.PriceList;

@Repository
public interface PriceListRepository extends JpaRepository< PriceList, Long >
{

	Boolean existsByName( String name );

}
