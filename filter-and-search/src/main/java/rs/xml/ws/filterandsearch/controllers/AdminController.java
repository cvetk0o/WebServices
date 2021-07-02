package rs.xml.ws.filterandsearch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.filterandsearch.models.dtos.ApiResponse;
import rs.xml.ws.filterandsearch.models.dtos.CompanyDTO;
import rs.xml.ws.filterandsearch.models.dtos.TwoFields;
import rs.xml.ws.filterandsearch.services.IPrivateService;

@RestController
@RequestMapping( "/admin" )
public class AdminController
{

	@Autowired
	private IPrivateService service;

	@PostMapping( "/company" )
	public ResponseEntity< ApiResponse > createCompany( @RequestBody CompanyDTO company )
	{
		return service.createCompany( company );

	}


	@PostMapping( "/brand" )
	public ResponseEntity< ApiResponse > createBrand( @RequestBody TwoFields brand )
	{
		return service.createBrand( brand );

	}


	@PostMapping( "/carclass" )
	public ResponseEntity< ApiResponse > createCarClass( @RequestBody TwoFields carClass )
	{
		return service.createCarClass( carClass );

	}


	@PostMapping( "/fueltype" )
	public ResponseEntity< ApiResponse > createFuelType( @RequestBody TwoFields fuelType )
	{
		return service.createFuelType( fuelType );

	}


	@PostMapping( "/model" )
	public ResponseEntity< ApiResponse > createModel( @RequestBody TwoFields model )
	{
		return service.createModel( model );

	}


	@PostMapping( "/transmission" )
	public ResponseEntity< ApiResponse > createTransmission( @RequestBody TwoFields transmission )
	{
		return service.createTransmission( transmission );

	}


	@PutMapping( "/brand" )
	public ResponseEntity< ApiResponse > updateBrand( @RequestBody TwoFields brand )
	{
		return service.updateBrand( brand );

	}


	@PutMapping( "/carclass" )
	public ResponseEntity< ApiResponse > updateCarClass( @RequestBody TwoFields carClass )
	{
		return service.updateCarClass( carClass );

	}


	@PutMapping( "/fueltype" )
	public ResponseEntity< ApiResponse > updateFuelType( @RequestBody TwoFields fuelType )
	{
		return service.updateFuelType( fuelType );

	}


	@PutMapping( "/model" )
	public ResponseEntity< ApiResponse > updateModel( @RequestBody TwoFields model )
	{
		return service.updateModel( model );

	}


	@PutMapping( "/transmission" )
	public ResponseEntity< ApiResponse > updateTransmission( @RequestBody TwoFields transmission )
	{
		return service.updateTransmission( transmission );

	}


	@DeleteMapping( "/brand/{id}" )
	public ResponseEntity< ApiResponse > deleteBrand( @PathVariable Long id )
	{
		return service.removeBrand( id );

	}


	@DeleteMapping( "/carclass/{id}" )
	public ResponseEntity< ApiResponse > deleteCarClass( @PathVariable Long id )
	{
		return service.removeCarClass( id );

	}


	@DeleteMapping( "/fueltype/{id}" )
	public ResponseEntity< ApiResponse > deleteFuelType( @PathVariable Long id )
	{
		return service.removeFuelType( id );

	}


	@DeleteMapping( "/model/{id}" )
	public ResponseEntity< ApiResponse > deleteModel( @PathVariable Long id )
	{
		return service.removeModel( id );

	}


	@DeleteMapping( "/transmission/{id}" )
	public ResponseEntity< ApiResponse > deleteTransmission( @PathVariable Long id )
	{
		return service.removeTransmission( id );

	}

}
