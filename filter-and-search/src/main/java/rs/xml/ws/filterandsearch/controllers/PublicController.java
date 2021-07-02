package rs.xml.ws.filterandsearch.controllers;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.filterandsearch.models.Brand;
import rs.xml.ws.filterandsearch.models.Car;
import rs.xml.ws.filterandsearch.models.CarClass;
import rs.xml.ws.filterandsearch.models.Company;
import rs.xml.ws.filterandsearch.models.FuelType;
import rs.xml.ws.filterandsearch.models.Model;
import rs.xml.ws.filterandsearch.models.Transmission;
import rs.xml.ws.filterandsearch.models.dtos.ApiResponse;
import rs.xml.ws.filterandsearch.models.dtos.SearchDTO;
import rs.xml.ws.filterandsearch.services.IPublicService;

@RestController
@RequestMapping( "/public" )
public class PublicController
{

	@Autowired
	private IPublicService service;

	@GetMapping( "/brands" )
	public ResponseEntity< List< Brand > > getAllBrands()
	{
		return service.getAllBrands();

	}


	@PostMapping( "/checkCars" )
	public ResponseEntity< List< Long > > checkIfFree( @RequestBody SearchDTO search )
	{
		return service.checkIfFree( search );

	}


	@GetMapping( "/cars" )
	public ResponseEntity< List< Car > > getAllCars()
	{
		return service.getAllCars();

	}


	@GetMapping( "/class" )
	public ResponseEntity< List< CarClass > > getAllCarClasses()
	{
		return service.getAllCarClasses();

	}


	@GetMapping( "/companies" )
	public ResponseEntity< List< Company > > getAllCompanies()
	{
		return service.getAllCompanies();

	}


	@GetMapping( "type" )
	public ResponseEntity< List< FuelType > > getAllFuelTypes()
	{
		return service.getAllFuelTypes();

	}


	@GetMapping( "/model" )
	public ResponseEntity< List< Model > > getAllModels()
	{
		return service.getAllModels();

	}


	@GetMapping( "/transmission" )
	public ResponseEntity< List< Transmission > > getAllTransmissions()
	{
		return service.getAllTransmissions();

	}


	@PostMapping( "/exists/cars/{company}" )
	public ResponseEntity< ApiResponse > checkCarsExists( @RequestBody List< Long > cars, @PathVariable Long company )
	{
		return service.checkIfExists( cars, company );

	}


	@GetMapping( "/company/mail/{id}" )
	public ResponseEntity< ApiResponse > getCompanyEmail( @PathVariable Long id )
	{
		return service.companyEmail( id );

	}


	@PostMapping( "/check/owner/{c}/{o}" )
	public ResponseEntity< ApiResponse > checkCarsOwner( @PathVariable Long c, @PathVariable Long o )
	{
		return service.checkCarOwner( c, o );

	}


	@GetMapping( "/car/present/{id}" )
	public ResponseEntity< ApiResponse > isCarPresent( @PathVariable Long id )
	{
		return service.isCarPresent( id );

	}


	@PostMapping( "/group/cars" )
	public ResponseEntity< Map< Long, Long > > groupCars( @RequestBody List< Long > cars )
	{
		return service.groupCarsByCompany( cars );

	}

}
