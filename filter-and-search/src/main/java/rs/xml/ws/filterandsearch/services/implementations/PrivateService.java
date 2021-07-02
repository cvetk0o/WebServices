package rs.xml.ws.filterandsearch.services.implementations;

import java.util.Optional;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.fasterxml.jackson.databind.ObjectMapper;


import rs.xml.ws.filterandsearch.feign.IFeignProxy;
import rs.xml.ws.filterandsearch.models.Brand;
import rs.xml.ws.filterandsearch.models.Car;
import rs.xml.ws.filterandsearch.models.CarClass;
import rs.xml.ws.filterandsearch.models.CarPicture;
import rs.xml.ws.filterandsearch.models.Company;
import rs.xml.ws.filterandsearch.models.FuelType;
import rs.xml.ws.filterandsearch.models.Model;
import rs.xml.ws.filterandsearch.models.PriceList;
import rs.xml.ws.filterandsearch.models.Transmission;
import rs.xml.ws.filterandsearch.models.dtos.ApiResponse;
import rs.xml.ws.filterandsearch.models.dtos.CarDTO;
import rs.xml.ws.filterandsearch.models.dtos.CompanyDTO;
import rs.xml.ws.filterandsearch.models.dtos.PriceListDTO;
import rs.xml.ws.filterandsearch.models.dtos.TwoFields;
import rs.xml.ws.filterandsearch.repositories.BrandRepository;
import rs.xml.ws.filterandsearch.repositories.CarClassRepository;
import rs.xml.ws.filterandsearch.repositories.CarPictureRepository;
import rs.xml.ws.filterandsearch.repositories.CarRepository;
import rs.xml.ws.filterandsearch.repositories.CompanyRepository;
import rs.xml.ws.filterandsearch.repositories.FuelTypeRepository;
import rs.xml.ws.filterandsearch.repositories.ModelRepository;
import rs.xml.ws.filterandsearch.repositories.PriceListRepository;
import rs.xml.ws.filterandsearch.repositories.TransmissionRepository;
import rs.xml.ws.filterandsearch.services.IPrivateService;

@Service
public class PrivateService implements IPrivateService
{

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private CarClassRepository carClassRepository;

	@Autowired
	private FuelTypeRepository fuelTypeRepository;

	@Autowired
	private ModelRepository modelRepository;

	@Autowired
	private CarPictureRepository carPictureRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private TransmissionRepository transmissionRepository;

	@Autowired
	private PriceListRepository priceListRepository;

	@Autowired
	private IFeignProxy proxy;

	@Override
	public ResponseEntity< ApiResponse > createBrand( TwoFields brand )
	{
		if ( brandRepository.existsByName( brand.getName() ) )
		{
			logger.error( ">>>> Name is in use" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in use" ), HttpStatus.BAD_REQUEST );
		}

		Brand newBrand = new Brand();
		newBrand.setName( brand.getName() );

		logger.info( ">>>> Saving brand" );
		brandRepository.save( newBrand );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Created" ), HttpStatus.CREATED );

	}


	@Override
	public ResponseEntity< ApiResponse > createCar( String carStr, Long person, Boolean agent, MultipartFile[] files )
	{

		try
		{
			ObjectMapper objectMapper = new ObjectMapper();

			CarDTO car = objectMapper.readValue( carStr, CarDTO.class );

			Set< Car > carsFromThisUser = carRepository.findByOwnerAndCompany( person, -1l );

			if ( carsFromThisUser.size() >= 3 )
			{
				logger.error( ">>>> This user has reached his maximum cap for cars" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "You can have maximum 3 cars" ), HttpStatus.BAD_REQUEST );
			}

			if ( carRepository.existsByPlateNumber( car.getPlateNumber() ) )
			{
				logger.error( ">>>> There is car with the same plate number" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Plate number is already in use" ), HttpStatus.BAD_REQUEST );
			}

			Optional< Brand > brandOptional = brandRepository.findById( car.getBrand() );
			if ( !brandOptional.isPresent() )
			{
				logger.error( ">>>> Cant find brand" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find brand" ), HttpStatus.BAD_REQUEST );
			}

			Optional< Model > modelOptional = modelRepository.findById( car.getModel() );
			if ( !modelOptional.isPresent() )
			{
				logger.error( ">>>> Cant find model" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find model" ), HttpStatus.BAD_REQUEST );
			}

			Optional< CarClass > carClassOptional = carClassRepository.findById( car.getCarClass() );
			if ( !carClassOptional.isPresent() )
			{
				logger.error( ">>>> Cant find car class" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find car class" ), HttpStatus.BAD_REQUEST );
			}

			Optional< Company > companyOptional = companyRepository.findById( car.getCompany() );
			if ( car.getCompany() != -1 && !companyOptional.isPresent() )
			{
				logger.error( ">>>> Cant find company" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find company" ), HttpStatus.BAD_REQUEST );
			}

			Optional< FuelType > fuelTypeOptional = fuelTypeRepository.findById( car.getFuelType() );
			if ( !fuelTypeOptional.isPresent() )
			{
				logger.error( ">>>> Cant find fuel type" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find fuel type" ), HttpStatus.BAD_REQUEST );
			}

			Optional< PriceList > priceListOptional = priceListRepository.findById( car.getPriceList() );
			if ( !priceListOptional.isPresent() )
			{
				logger.error( ">>>> Cant find price list" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find price list" ), HttpStatus.BAD_REQUEST );
			}

			Optional< Transmission > transmissionOptional = transmissionRepository.findById( car.getTransmission() );
			if ( !transmissionOptional.isPresent() )
			{
				logger.error( ">>>> Cant find transmission" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find transmission" ), HttpStatus.BAD_REQUEST );
			}

			Car newCar = new Car();

			newCar.setBrand( brandOptional.get() );
			newCar.setCarClass( carClassOptional.get() );
			newCar.setChildSeats( car.getChildSeats() );
			newCar.setCompany( -1l );
			newCar.setDiscount( car.getDiscount() );
			newCar.setDistanceAllowed( car.getDistanceAllowed() );
			newCar.setFuelType( fuelTypeOptional.get() );

			newCar.setMileage( car.getMileage() );
			newCar.setModel( modelOptional.get() );
			newCar.setOwner( person );
			newCar.setPlateNumber( car.getPlateNumber() );
			newCar.setPriceList( priceListOptional.get() );
			newCar.setProtection( car.getProtection() );
			newCar.setTransmission( transmissionOptional.get() );

			try
			{

				for ( MultipartFile iterator : files )
				{
					CarPicture picture = new CarPicture();

					picture.setData( iterator.getBytes() );

					carPictureRepository.save( picture );

					newCar.getImages().add( picture );
				}
			}
			catch ( Exception e )
			{
				logger.error( ">>>> Error with images" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Error with images" ), HttpStatus.BAD_REQUEST );
			}
			logger.info( ">>>> Saving car" );
			Car save = carRepository.save( newCar );
			return new ResponseEntity< ApiResponse >( new ApiResponse( true, save.getId().toString() ), HttpStatus.CREATED );
		}
		catch ( Exception e )
		{
			System.err.println( "errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" );
		}
		return new ResponseEntity< ApiResponse >( new ApiResponse( false, "exception" ), HttpStatus.BAD_REQUEST );

	}


	@Override
	public ResponseEntity< ApiResponse > createCarClass( TwoFields carClass )
	{
		if ( carClassRepository.existsByName( carClass.getName() ) )
		{
			logger.error( ">>>> Name is in use" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in use" ), HttpStatus.BAD_REQUEST );
		}

		CarClass newCarClass = new CarClass( carClass.getName() );

		logger.info( ">>>> Saving car class" );

		carClassRepository.save( newCarClass );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Created" ), HttpStatus.CREATED );

	}


	@Override
	public ResponseEntity< ApiResponse > createCompany( CompanyDTO company )
	{

		if ( companyRepository.existsByBussinessCode( company.getBussinessCode() ) )
		{
			logger.error( ">>>> Bussiness code {} is in use", company.getBussinessCode() );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Bussiness code is in use" ), HttpStatus.BAD_REQUEST );
		}

		Company newCompany = new Company();

		newCompany.setAdress( company.getAdress() );
		newCompany.setBussinessCode( company.getBussinessCode() );
		newCompany.setEmail( company.getEmail() );
		newCompany.setName( company.getName() );

		logger.info( ">>>> Saving company" );

		companyRepository.save( newCompany );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Created" ), HttpStatus.CREATED );

	}


	@Override
	public ResponseEntity< ApiResponse > createFuelType( TwoFields fuelType )
	{

		if ( fuelTypeRepository.existsByName( fuelType.getName() ) )
		{
			logger.error( ">>>> Name is in use" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in use" ), HttpStatus.BAD_REQUEST );
		}

		FuelType newFuelType = new FuelType();

		newFuelType.setName( fuelType.getName() );

		logger.info( ">>>> Saving fuel type" );

		fuelTypeRepository.save( newFuelType );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Created" ), HttpStatus.CREATED );

	}


	@Override
	public ResponseEntity< ApiResponse > createModel( TwoFields model )
	{
		if ( modelRepository.existsByName( model.getName() ) )
		{
			logger.error( ">>>> Name is in use" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in use" ), HttpStatus.BAD_REQUEST );
		}

		Model newModel = new Model( model.getName() );

		logger.info( ">>>> Saving model" );

		modelRepository.save( newModel );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Created" ), HttpStatus.CREATED );

	}


	@Override
	public ResponseEntity< ApiResponse > createTransmission( TwoFields transmission )
	{

		if ( transmissionRepository.existsByName( transmission.getName() ) )
		{
			logger.error( ">>>> Name is in use" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in user" ), HttpStatus.BAD_REQUEST );
		}

		Transmission newTransmission = new Transmission( transmission.getName() );

		logger.info( ">>>> Saving transmission" );

		transmissionRepository.save( newTransmission );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Created" ), HttpStatus.CREATED );

	}


	@Override
	public ResponseEntity< ApiResponse > createPriceList( PriceListDTO priceList )
	{

		if ( priceListRepository.existsByName( priceList.getName() ) )
		{
			logger.error( ">>>> Name is in use" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in user" ), HttpStatus.BAD_REQUEST );
		}

		PriceList newPriceList = new PriceList();

		newPriceList.setCollisionDamageWaiver( priceList.getCollisionDamageWaiver() );
		newPriceList.setLimitedPrice( priceList.getLimitedPrice() );
		newPriceList.setName( priceList.getName() );
		newPriceList.setRegularPrice( priceList.getRegularPrice() );

		logger.info( ">>>> Saving price list" );

		PriceList save = priceListRepository.save( newPriceList );
		return new ResponseEntity< ApiResponse >( new ApiResponse( true, save.getId().toString() ), HttpStatus.CREATED );

	}


	@Override
	public ResponseEntity< ApiResponse > updateBrand( TwoFields brand )
	{

		Optional< Brand > brandOptional = brandRepository.findById( brand.getId() );

		if ( !brandOptional.isPresent() )
		{
			logger.error( ">>>> Brand with id {} is not present!" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Brand with id " + brand.getId() + " is not present" ), HttpStatus.BAD_REQUEST );
		}

		if ( brandRepository.existsByName( brand.getName() ) )
		{
			logger.error( ">>>> Name is in use" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in use" ), HttpStatus.BAD_REQUEST );
		}

		Brand brand2change = brandOptional.get();

		brand2change.setName( brand.getName() );

		logger.info( ">>>> Saving change" );
		brandRepository.save( brand2change );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > updateCar( String carStr, Long person, Boolean agent, MultipartFile[] files )
	{

		try
		{
			ObjectMapper objectMapper = new ObjectMapper();

			CarDTO car = objectMapper.readValue( carStr, CarDTO.class );

			Optional< Car > carOptional = carRepository.findById( car.getId() );

			if ( !carOptional.isPresent() )
			{
				logger.error( ">>>> Cant find car" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find car" ), HttpStatus.BAD_REQUEST );
			}

			Car car2change = carOptional.get();

			if ( !agent && car2change.getOwner() != person )
			{
				logger.error( ">>>> This is not your car" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "This is not your car" ), HttpStatus.BAD_REQUEST );
			}

			Optional< Brand > brandOptional = brandRepository.findById( car.getBrand() );
			if ( !brandOptional.isPresent() )
			{
				logger.error( ">>>> Cant find brand" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find brand" ), HttpStatus.BAD_REQUEST );
			}

			Optional< Model > modelOptional = modelRepository.findById( car.getModel() );
			if ( !modelOptional.isPresent() )
			{
				logger.error( ">>>> Cant find model" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find model" ), HttpStatus.BAD_REQUEST );
			}

			Optional< CarClass > carClassOptional = carClassRepository.findById( car.getCarClass() );
			if ( !carClassOptional.isPresent() )
			{
				logger.error( ">>>> Cant find car class" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find car class" ), HttpStatus.BAD_REQUEST );
			}

			Optional< Company > companyOptional = companyRepository.findById( car.getCompany() );
			if ( car.getCompany() != -1 && !companyOptional.isPresent() )
			{
				logger.error( ">>>> Cant find company" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find company" ), HttpStatus.BAD_REQUEST );
			}

			Optional< FuelType > fuelTypeOptional = fuelTypeRepository.findById( car.getFuelType() );
			if ( !fuelTypeOptional.isPresent() )
			{
				logger.error( ">>>> Cant find fuel type" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find fuel type" ), HttpStatus.BAD_REQUEST );
			}

			Optional< PriceList > priceListOptional = priceListRepository.findById( car.getPriceList() );
			if ( !priceListOptional.isPresent() )
			{
				logger.error( ">>>> Cant find price list" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find price list" ), HttpStatus.BAD_REQUEST );
			}

			Optional< Transmission > transmissionOptional = transmissionRepository.findById( car.getTransmission() );
			if ( !transmissionOptional.isPresent() )
			{
				logger.error( ">>>> Cant find transmission" );
				return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find transmission" ), HttpStatus.BAD_REQUEST );
			}

			car2change.setBrand( brandOptional.get() );
			car2change.setCarClass( carClassOptional.get() );
			car2change.setChildSeats( car.getChildSeats() );
			car2change.setCompany( car.getCompany() );
			car2change.setDiscount( car.getDiscount() );
			car2change.setDistanceAllowed( car.getDistanceAllowed() );
			car2change.setFuelType( fuelTypeOptional.get() );

			car2change.setMileage( car.getMileage() );
			car2change.setModel( modelOptional.get() );
			car2change.setOwner( car.getOwner() );
			car2change.setPriceList( priceListOptional.get() );
			car2change.setProtection( car.getProtection() );
			car2change.setTransmission( transmissionOptional.get() );

			carPictureRepository.deleteAll( car2change.getImages() );

			car2change.getImages().clear();

			for ( MultipartFile iterator : files )
			{
				CarPicture carPicture = new CarPicture();

				carPicture.setData( iterator.getBytes() );

			}

			logger.info( ">>>> Saving car" );
			carRepository.save( car2change );
			return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" ), HttpStatus.OK );
		}
		catch ( Exception e )
		{

			System.err.println( "errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" );
		}

		return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Exception" ), HttpStatus.BAD_REQUEST );

	}


	@Override
	public ResponseEntity< ApiResponse > updateCarClass( TwoFields carClass )
	{
		Optional< CarClass > carClassOptional = carClassRepository.findById( carClass.getId() );

		if ( !carClassOptional.isPresent() )
		{
			logger.error( ">>>> Car class with id {} is not present", carClass.getId() );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Car class with id not found" ), HttpStatus.BAD_REQUEST );
		}

		if ( ( carClassRepository.existsByName( carClass.getName() ) ) )
		{
			logger.error( ">>>> Name is in use" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in use" ), HttpStatus.BAD_REQUEST );
		}

		CarClass carClass2change = carClassOptional.get();

		carClass2change.setName( carClass.getName() );

		logger.info( ">>>> Saving change" );

		carClassRepository.save( carClass2change );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > updateCompany( CompanyDTO company, Long person, Boolean admin )
	{

		Optional< Company > companyOptional = companyRepository.findById( company.getId() );

		if ( !companyOptional.isPresent() )
		{
			logger.error( ">>>> Cant find company with id {}", company.getId() );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find company with id " + company.getId() ), HttpStatus.BAD_REQUEST );
		}

		Company company2change = companyOptional.get();

		if ( !admin && person != company2change.getId() )
		{
			logger.error( ">>>> You cant update this company" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "You cant update this company" ), HttpStatus.BAD_REQUEST );
		}

		company2change.setAdress( company.getAdress() );
		company2change.setEmail( company.getEmail() );
		company2change.setAdress( company.getName() );

		logger.info( ">>>> Saving changes" );

		companyRepository.save( company2change );
		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > updateFuelType( TwoFields fuelType )
	{

		Optional< FuelType > fuelTypeOptional = fuelTypeRepository.findById( fuelType.getId() );

		if ( !fuelTypeOptional.isPresent() )
		{
			logger.error( ">>>> Cant find fuel type with id {}", fuelType.getId() );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find fuel type with id " + fuelType.getId() ), HttpStatus.BAD_REQUEST );
		}

		if ( fuelTypeRepository.existsByName( fuelType.getName() ) )
		{
			logger.error( ">>>> Name is in use" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in use" ), HttpStatus.BAD_REQUEST );
		}

		FuelType fuelType2change = fuelTypeOptional.get();

		fuelType2change.setName( fuelType.getName() );

		logger.info( ">>>> Saving change" );
		fuelTypeRepository.save( fuelType2change );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > updateModel( TwoFields model )
	{
		Optional< Model > modelOptional = modelRepository.findById( model.getId() );

		if ( !modelOptional.isPresent() )
		{
			logger.error( ">>>> Cant find model with id {}", model.getId() );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find model" ), HttpStatus.BAD_REQUEST );
		}

		if ( modelRepository.existsByName( model.getName() ) )
		{
			logger.error( ">>>> Name is in use" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in use" ), HttpStatus.BAD_REQUEST );
		}

		Model model2change = modelOptional.get();

		model2change.setName( model.getName() );

		logger.info( ">>>> Saving change" );

		modelRepository.save( model2change );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > updateTransmission( TwoFields transmission )
	{
		Optional< Transmission > transmissionOptional = transmissionRepository.findById( transmission.getId() );

		if ( !transmissionOptional.isPresent() )
		{
			logger.error( ">>>> Cant find transmission with id {}", transmission.getId() );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find transmission" ), HttpStatus.BAD_REQUEST );
		}

		if ( transmissionRepository.existsByName( transmission.getName() ) )
		{
			logger.error( ">>>> Name is in use" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in use" ), HttpStatus.BAD_REQUEST );
		}

		Transmission transmission2change = transmissionOptional.get();

		transmission2change.setName( transmission.getName() );

		logger.info( ">>>> Saving change" );
		transmissionRepository.save( transmission2change );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > removeBrand( Long id )
	{

		if ( !brandRepository.existsById( id ) )
		{
			logger.error( ">>>> Cant find brand" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find brand" ), HttpStatus.BAD_REQUEST );
		}

		if ( carRepository.existsByBrandId( id ) )
		{
			logger.error( ">>>> There is car of that brand" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "There are cars of that brand" ), HttpStatus.BAD_REQUEST );
		}
		logger.info( ">>>> Removing brand" );
		brandRepository.deleteById( id );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Removed" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > removeCarClass( Long id )
	{

		if ( !carClassRepository.existsById( id ) )
		{
			logger.error( ">>>> Cant find car class" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find car class" ), HttpStatus.BAD_REQUEST );
		}

		if ( carRepository.existsByCarClassId( id ) )
		{
			logger.error( ">>>> There is car of this class" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "There are cars of this class" ), HttpStatus.BAD_REQUEST );
		}

		logger.info( ">>>> Removing car class" );
		carClassRepository.deleteById( id );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Removed" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > removeCompany( Long id, Long person, Boolean admin )
	{

		Optional< Company > companyOptional = companyRepository.findById( id );

		if ( !companyOptional.isPresent() )
		{
			logger.info( ">>>> Cant find company" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find company" ), HttpStatus.BAD_REQUEST );
		}

		Company company = companyOptional.get();

		if ( !admin && company.getId() != person )
		{
			logger.error( ">>>> This is not your company" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "This is not your company" ), HttpStatus.BAD_REQUEST );
		}

		return null;

	}


	@Override
	public ResponseEntity< ApiResponse > removeFuelType( Long id )
	{

		if ( !fuelTypeRepository.existsById( id ) )
		{
			logger.error( ">>>> Cant find fuel type" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find fuel type" ), HttpStatus.BAD_REQUEST );
		}

		if ( carRepository.existsByFuelTypeId( id ) )
		{
			logger.error( ">>>> There are cars with this fuel type" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "There are cars with this fuel type" ), HttpStatus.BAD_REQUEST );
		}

		logger.info( ">>>> Removing fuel type" );
		fuelTypeRepository.deleteById( id );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Fuel type is removed" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > removeModel( Long id )
	{

		if ( !modelRepository.existsById( id ) )
		{
			logger.error( ">>>> Cant find model" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find model" ), HttpStatus.BAD_REQUEST );
		}

		if ( carRepository.existsByModelId( id ) )
		{
			logger.error( ">>>> There are cars of this model" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "There are cars of this model" ), HttpStatus.BAD_REQUEST );
		}

		logger.info( ">>>> Removing model" );
		modelRepository.deleteById( id );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Removed" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > removeTransmission( Long id )
	{
		if ( !transmissionRepository.existsById( id ) )
		{
			logger.error( ">>>> Cant find transmission" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find transmission" ), HttpStatus.BAD_REQUEST );
		}

		if ( carRepository.existsByTransmissionId( id ) )
		{
			logger.error( ">>>> There are cars with this transmission" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "There are cars with this transmission" ), HttpStatus.BAD_REQUEST );
		}

		logger.info( ">>>> Removing transmission" );
		transmissionRepository.deleteById( id );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Removed" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > removeCar( Long id, Long person, Boolean agent )
	{
		Optional< Car > optionalCar = carRepository.findById( id );

		if ( !optionalCar.isPresent() )
		{
			logger.error( ">>>> Cant find car" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find car" ), HttpStatus.BAD_REQUEST );
		}

		ResponseEntity< ApiResponse > checkIfCarIsInUse = proxy.checkIfCarIsInUse( id );

		if ( checkIfCarIsInUse.getBody().getSuccess() )
		{
			logger.error( ">>>> There are appointments with this car" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "There are appointments with this car" ), HttpStatus.BAD_REQUEST );
		}

		Car car = optionalCar.get();

		if ( agent && car.getCompany() != person )
		{
			logger.error( ">>>> This is not your car" );

			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "This is not your car" ), HttpStatus.BAD_REQUEST );
		}

		logger.info( ">>>> Removing car" );

		carRepository.deleteById( id );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Removed" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Car > > getMyCars( Long id )
	{

		return new ResponseEntity< Set< Car > >( carRepository.findByOwner( id ), HttpStatus.OK );

	}

}
