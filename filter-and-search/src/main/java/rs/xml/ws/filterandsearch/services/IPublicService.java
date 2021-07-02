package rs.xml.ws.filterandsearch.services;

import java.util.List;
import java.util.Map;


import org.springframework.http.ResponseEntity;


import rs.xml.ws.filterandsearch.models.Brand;
import rs.xml.ws.filterandsearch.models.Car;
import rs.xml.ws.filterandsearch.models.CarClass;
import rs.xml.ws.filterandsearch.models.Company;
import rs.xml.ws.filterandsearch.models.FuelType;
import rs.xml.ws.filterandsearch.models.Model;
import rs.xml.ws.filterandsearch.models.Transmission;
import rs.xml.ws.filterandsearch.models.dtos.ApiResponse;
import rs.xml.ws.filterandsearch.models.dtos.SearchDTO;

public interface IPublicService
{
	// get all models

	ResponseEntity< List< Brand > > getAllBrands();

	ResponseEntity< List< Car > > getAllCars();

	ResponseEntity< List< CarClass > > getAllCarClasses();

	ResponseEntity< List< Company > > getAllCompanies();

	ResponseEntity< List< FuelType > > getAllFuelTypes();

	ResponseEntity< List< Model > > getAllModels();

	ResponseEntity< List< Transmission > > getAllTransmissions();

	ResponseEntity< List< Long > > checkIfFree( SearchDTO search );

	ResponseEntity< ApiResponse > checkIfExists( List< Long > cars, Long company );

	ResponseEntity< ApiResponse > companyEmail( Long id );

	ResponseEntity< ApiResponse > checkCarOwner( Long car, Long owner );

	ResponseEntity< ApiResponse > isCarPresent( Long car );

	ResponseEntity< Map< Long, Long > > groupCarsByCompany( List< Long > cars );

}
