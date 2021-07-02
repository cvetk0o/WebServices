package rs.xml.ws.filterandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.filterandsearch.models.Model;

@Repository
public interface ModelRepository extends JpaRepository< Model, Long >
{

	Boolean existsByName( String name );

}
