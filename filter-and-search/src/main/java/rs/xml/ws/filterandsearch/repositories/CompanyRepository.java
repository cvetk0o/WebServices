package rs.xml.ws.filterandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.filterandsearch.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository< Company, Long >
{

	Boolean existsByBussinessCode( Long code );

}
