package rs.xml.ws.filterandsearch.services;

import java.util.Set;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


import rs.xml.ws.filterandsearch.models.Car;
import rs.xml.ws.filterandsearch.models.dtos.ApiResponse;
import rs.xml.ws.filterandsearch.models.dtos.CompanyDTO;
import rs.xml.ws.filterandsearch.models.dtos.PriceListDTO;
import rs.xml.ws.filterandsearch.models.dtos.TwoFields;

public interface IPrivateService
{

	// CREATE

	ResponseEntity< ApiResponse > createBrand( TwoFields brand );

	ResponseEntity< ApiResponse > createCar( String car, Long person, Boolean agent, MultipartFile[] files );

	ResponseEntity< ApiResponse > createCarClass( TwoFields carClass );

	ResponseEntity< ApiResponse > createCompany( CompanyDTO company );

	ResponseEntity< ApiResponse > createFuelType( TwoFields fuelType );

	ResponseEntity< ApiResponse > createModel( TwoFields model );

	ResponseEntity< ApiResponse > createTransmission( TwoFields transmission );

	ResponseEntity< ApiResponse > createPriceList( PriceListDTO priceList );

	// UPDATE

	ResponseEntity< ApiResponse > updateBrand( TwoFields brand );

	ResponseEntity< ApiResponse > updateCar( String car, Long person, Boolean agent, MultipartFile[] files );

	ResponseEntity< ApiResponse > updateCarClass( TwoFields carClass );

	ResponseEntity< ApiResponse > updateCompany( CompanyDTO company, Long person, Boolean admin );

	ResponseEntity< ApiResponse > updateFuelType( TwoFields fuelType );

	ResponseEntity< ApiResponse > updateModel( TwoFields model );

	ResponseEntity< ApiResponse > updateTransmission( TwoFields transmission );

	// REMOVE

	ResponseEntity< ApiResponse > removeBrand( Long id );

	ResponseEntity< ApiResponse > removeCar( Long id, Long person, Boolean agent );

	ResponseEntity< ApiResponse > removeCarClass( Long id );

	ResponseEntity< ApiResponse > removeCompany( Long id, Long person, Boolean admin );

	ResponseEntity< ApiResponse > removeFuelType( Long id );

	ResponseEntity< ApiResponse > removeModel( Long id );

	ResponseEntity< ApiResponse > removeTransmission( Long id );

	// custom

	ResponseEntity< Set< Car > > getMyCars( Long id );

}
