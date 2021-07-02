package rs.xml.ws.filterandsearch.services.implementations;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import rs.xml.ws.filterandsearch.feign.IFeignProxy;
import rs.xml.ws.filterandsearch.models.Brand;
import rs.xml.ws.filterandsearch.models.Car;
import rs.xml.ws.filterandsearch.models.CarClass;
import rs.xml.ws.filterandsearch.models.Company;
import rs.xml.ws.filterandsearch.models.FuelType;
import rs.xml.ws.filterandsearch.models.Model;
import rs.xml.ws.filterandsearch.models.Transmission;
import rs.xml.ws.filterandsearch.models.dtos.ApiResponse;
import rs.xml.ws.filterandsearch.models.dtos.SearchDTO;
import rs.xml.ws.filterandsearch.repositories.BrandRepository;
import rs.xml.ws.filterandsearch.repositories.CarClassRepository;
import rs.xml.ws.filterandsearch.repositories.CarRepository;
import rs.xml.ws.filterandsearch.repositories.CompanyRepository;
import rs.xml.ws.filterandsearch.repositories.FuelTypeRepository;
import rs.xml.ws.filterandsearch.repositories.ModelRepository;
import rs.xml.ws.filterandsearch.repositories.TransmissionRepository;
import rs.xml.ws.filterandsearch.services.IPublicService;

@Service
public class PublicService implements IPublicService
{

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private IFeignProxy proxyAppointment;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private CarClassRepository carClassRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private FuelTypeRepository fuelTypeRepository;

	@Autowired
	private ModelRepository modelRepository;

	@Autowired
	private TransmissionRepository transmissionRepository;

	@Override
	public ResponseEntity< List< Brand > > getAllBrands()
	{

		logger.info( ">>>> Retriving brands" );
		List< Brand > brands = brandRepository.findAll();
		return new ResponseEntity< List< Brand > >( brands, HttpStatus.OK );

	}


	@Override
	public ResponseEntity< List< Car > > getAllCars()
	{

		logger.info( ">>>> Retriving cars" );
		List< Car > cars = carRepository.findAll();
		return new ResponseEntity< List< Car > >( cars, HttpStatus.OK );

	}


	@Override
	public ResponseEntity< List< CarClass > > getAllCarClasses()
	{

		logger.info( ">>>> Retriving car classes" );
		List< CarClass > carClasses = carClassRepository.findAll();
		return new ResponseEntity< List< CarClass > >( carClasses, HttpStatus.OK );

	}


	@Override
	public ResponseEntity< List< Company > > getAllCompanies()
	{

		logger.info( ">>>> Retriving companies" );
		List< Company > companies = companyRepository.findAll();
		return new ResponseEntity< List< Company > >( companies, HttpStatus.OK );

	}


	@Override
	public ResponseEntity< List< FuelType > > getAllFuelTypes()
	{
		logger.info( ">>>> Retriving fuel types" );
		List< FuelType > fuelTypes = fuelTypeRepository.findAll();
		return new ResponseEntity< List< FuelType > >( fuelTypes, HttpStatus.OK );

	}


	@Override
	public ResponseEntity< List< Model > > getAllModels()
	{
		logger.info( ">>>> Retriving models" );
		List< Model > models = modelRepository.findAll();
		return new ResponseEntity< List< Model > >( models, HttpStatus.OK );

	}


	@Override
	public ResponseEntity< List< Transmission > > getAllTransmissions()
	{
		logger.info( ">>>> Retriving trasmissions" );
		List< Transmission > transmissions = transmissionRepository.findAll();
		return new ResponseEntity< List< Transmission > >( transmissions, HttpStatus.OK );

	}


	@Override
	public ResponseEntity< List< Long > > checkIfFree( SearchDTO search )
	{

		ResponseEntity< List< Long > > freeCarsIds = proxyAppointment.checkIfFree( search );
		System.err.println( freeCarsIds.getBody().size() );
		return null;

	}


	@Override
	public ResponseEntity< ApiResponse > checkIfExists( List< Long > cars, Long company )
	{
		// if company === -1, just check if cars exist, otherwise check owner of car

		Set< Car > carsRetrived = carRepository.findByIdIn( cars );

		if ( cars.size() != carsRetrived.size() )
		{
			logger.info( ">>>> Couldn't find all cars" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "NOT_ALL" ), HttpStatus.OK );
		}

		if ( company == -1 )
		{
			logger.info( ">>>> Everything is ok" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" ), HttpStatus.OK );
		}

		for ( Car car : carsRetrived )
		{
			if ( car.getCompany() != company )
			{
				logger.info( ">>>> This car is not owned by company with id {}", company );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "NOT_YOURS" ), HttpStatus.OK );
			}
		}

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > companyEmail( Long id )
	{

		Optional< Company > companyOptional = companyRepository.findById( id );

		if ( !companyOptional.isPresent() )
		{
			logger.error( ">>>> Cant find company" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "" ), HttpStatus.OK );
		}

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, companyOptional.get().getEmail() ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > checkCarOwner( Long car, Long owner )
	{

		Optional< Car > carOptional = carRepository.findById( car );

		if ( !carOptional.isPresent() )
		{
			logger.error( ">>>> Cant find car" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find car" ), HttpStatus.OK );
		}

		if ( carOptional.get().getOwner() != owner )
		{
			logger.error( ">>>> This car is not owned by this user" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "NOT_YOURS" ), HttpStatus.OK );
		}

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, carOptional.get().getMileage().toString() ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > isCarPresent( Long car )
	{

		return new ResponseEntity< ApiResponse >( new ApiResponse( carRepository.existsById( car ), "" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Map< Long, Long > > groupCarsByCompany( List< Long > cars )
	{

		return new ResponseEntity< Map< Long, Long > >( carRepository.findByIdIn( cars ).stream().collect( Collectors.toMap( Car::getId, Car::getCompany ) ),
				HttpStatus.OK );

	}

}
