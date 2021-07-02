package rs.xml.ws.filterandsearch.soap.endpoints;

import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import rs.xml.ws.filterandsearch.models.Brand;
import rs.xml.ws.filterandsearch.models.Car;
import rs.xml.ws.filterandsearch.models.CarClass;
import rs.xml.ws.filterandsearch.models.FuelType;
import rs.xml.ws.filterandsearch.models.Model;
import rs.xml.ws.filterandsearch.models.PriceList;
import rs.xml.ws.filterandsearch.models.Transmission;
import rs.xml.ws.filterandsearch.repositories.BrandRepository;
import rs.xml.ws.filterandsearch.repositories.CarClassRepository;
import rs.xml.ws.filterandsearch.repositories.CarRepository;
import rs.xml.ws.filterandsearch.repositories.FuelTypeRepository;
import rs.xml.ws.filterandsearch.repositories.ModelRepository;
import rs.xml.ws.filterandsearch.repositories.PriceListRepository;
import rs.xml.ws.filterandsearch.repositories.TransmissionRepository;
import rs.xml.ws.filterandsearch.soap.ApiResponseXML;
import rs.xml.ws.filterandsearch.soap.CarXML;
import rs.xml.ws.filterandsearch.soap.CreateCarRequest;
import rs.xml.ws.filterandsearch.soap.CreatePriceListRequest;
import rs.xml.ws.filterandsearch.soap.GetAllCompanyDataRequest;
import rs.xml.ws.filterandsearch.soap.GetAllCompanyDataResponse;
import rs.xml.ws.filterandsearch.soap.IdNameXML;
import rs.xml.ws.filterandsearch.soap.PriceListXML;
import rs.xml.ws.filterandsearch.soap.RemoveCarRequest;
import rs.xml.ws.filterandsearch.soap.UpdateCarRequest;

@org.springframework.ws.server.endpoint.annotation.Endpoint
public class Endpoint
{

	private static final String NAMESPACE_URI = "www.rent-a-car.ws.xml.rs/filter-and-search";

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private CarClassRepository carClassRepository;

	// @Autowired
	// private CarPictureRepository carPictureRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private FuelTypeRepository fuelTypeRepository;

	@Autowired
	private ModelRepository modelRepository;

	@Autowired
	private PriceListRepository priceListRepository;

	@Autowired
	private TransmissionRepository transmissionRepository;

	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "getAllCompanyDataRequest" )
	@ResponsePayload
	public GetAllCompanyDataResponse synchAll( @RequestPayload GetAllCompanyDataRequest request )
	{

		long company = request.getCompany();

		GetAllCompanyDataResponse response = new GetAllCompanyDataResponse();

		logger.info( ">>>> Retriving cars from company with id {}", request.getCompany() );
		Set< Car > cars = carRepository.findByCompany( company );

		logger.info( ">>>> Retriving all brands" );
		List< Brand > brands = brandRepository.findAll();

		logger.info( ">>>> Retriving all car classes" );
		List< CarClass > carClasses = carClassRepository.findAll();

		logger.info( ">>>> Retriving all fuel types" );
		List< FuelType > fuelTypes = fuelTypeRepository.findAll();

		logger.info( ">>>> Retriving all models" );
		List< Model > models = modelRepository.findAll();

		logger.info( ">>>> Retriving all transmissions" );
		List< Transmission > transmissions = transmissionRepository.findAll();

		logger.info( ">>>> Retriving all price lists" );
		List< PriceList > priceLists = priceListRepository.findAll();

		for ( PriceList pl : priceLists )
		{
			PriceListXML p = new PriceListXML();

			p.setCollisionDamageWaiver( pl.getCollisionDamageWaiver() );
			p.setId( pl.getId() );
			p.setLimitedPrice( pl.getLimitedPrice() );
			p.setName( pl.getName() );
			p.setRegularPrice( pl.getRegularPrice() );

			response.getPriceLists().add( p );
		}

		for ( Transmission t : transmissions )
		{
			IdNameXML tl = new IdNameXML();

			tl.setId( t.getId() );
			tl.setName( t.getName() );

			response.getTransmissions().add( tl );
		}

		for ( Model m : models )
		{
			IdNameXML ml = new IdNameXML();

			ml.setId( m.getId() );
			ml.setName( m.getName() );

			response.getModels().add( ml );
		}

		for ( FuelType f : fuelTypes )
		{
			IdNameXML ft = new IdNameXML();

			ft.setId( f.getId() );
			ft.setName( f.getName() );

			response.getFuelTypes().add( ft );
		}

		for ( CarClass c : carClasses )
		{
			IdNameXML cc = new IdNameXML();

			cc.setId( c.getId() );
			cc.setName( c.getName() );

			response.getCarClasses().add( cc );
		}

		for ( Brand b : brands )
		{
			IdNameXML br = new IdNameXML();

			br.setId( b.getId() );
			br.setName( b.getName() );

			response.getBrands().add( br );
		}

		for ( Car c : cars )
		{
			CarXML car = new CarXML();

			IdNameXML carClass = new IdNameXML();
			carClass.setId( c.getCarClass().getId() );
			carClass.setName( c.getCarClass().getName() );
			car.setCarClass( carClass );
			IdNameXML brand = new IdNameXML();
			brand.setId( c.getBrand().getId() );
			brand.setName( c.getBrand().getName() );
			car.setBrand( brand );
			car.setChildSeats( c.getChildSeats() );
			car.setCompany( c.getCompany() );
			car.setDistanceAllowed( c.getDistanceAllowed() );

			IdNameXML fuelType = new IdNameXML();
			fuelType.setId( c.getFuelType().getId() );
			fuelType.setName( c.getFuelType().getName() );
			car.setFuelType( fuelType );

			car.setId( car.getId() );

			// for ( CarPicture carPicture : c.getImages() )
			// {
			// IdNameXML image = new IdNameXML();
			//
			// image.setId( carPicture.getId() );
			// image.setName( carPicture.getLocation() );
			//
			// car.getImages().getImage().add( image );
			//
			// }

			car.setMileage( c.getMileage() );

			IdNameXML model = new IdNameXML();
			model.setId( c.getModel().getId() );
			model.setName( c.getModel().getName() );

			car.setModel( model );

			car.setOwner( c.getOwner() );

			car.setPlateNumber( c.getPlateNumber() );

			PriceListXML priceList = new PriceListXML();

			priceList.setCollisionDamageWaiver( c.getPriceList().getCollisionDamageWaiver() );
			priceList.setId( c.getPriceList().getId() );
			priceList.setLimitedPrice( c.getPriceList().getLimitedPrice() );
			priceList.setName( c.getPriceList().getName() );
			priceList.setRegularPrice( c.getPriceList().getRegularPrice() );
			car.setPriceList( priceList );

			car.setProtection( c.getProtection() );
			IdNameXML transmission = new IdNameXML();

			transmission.setId( c.getTransmission().getId() );
			transmission.setName( c.getTransmission().getName() );
			car.setTransmission( transmission );

			car.setDiscount( c.getDiscount() );
			car.setProtection( c.getProtection() );
			response.getCars().add( car );
		}

		return response;

	}


	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "createCarRequest" )
	@ResponsePayload
	public ApiResponseXML createCar( @RequestPayload CreateCarRequest request )
	{
		// TODO TEST

		ApiResponseXML response = new ApiResponseXML();
		response.setSuccess( false );

		CarXML car = request.getCar();

		Car newCar = new Car();

		long brand = car.getBrand().getId();

		Optional< Brand > brandOptional = brandRepository.findById( brand );
		if ( !brandOptional.isPresent() )
		{
			logger.error( ">>>> Cant find brand" );
			response.setMessage( "Cant find brand" );
			return response;
		}
		newCar.setBrand( brandOptional.get() );

		long carClass = car.getCarClass().getId();

		Optional< CarClass > carClassOptional = carClassRepository.findById( carClass );

		if ( !carClassOptional.isPresent() )
		{
			logger.error( ">>>> Cant find car class" );
			response.setMessage( "Cant find car class" );
			return response;
		}

		newCar.setCarClass( carClassOptional.get() );
		newCar.setChildSeats( car.getChildSeats() );
		newCar.setCompany( car.getCompany() );
		newCar.setDiscount( car.getDiscount() );
		newCar.setDistanceAllowed( car.getDistanceAllowed() );

		long fuelType = car.getFuelType().getId();

		Optional< FuelType > fuelTypeOptional = fuelTypeRepository.findById( fuelType );
		if ( !fuelTypeOptional.isPresent() )
		{
			logger.error( ">>>> Cant find fuel type" );
			response.setMessage( "Cant find fuel type" );
			return response;
		}

		newCar.setFuelType( fuelTypeOptional.get() );

		newCar.setMileage( car.getMileage() );

		long model = car.getModel().getId();

		Optional< Model > modelOptional = modelRepository.findById( model );

		if ( !modelOptional.isPresent() )
		{
			logger.error( ">>>> Cant find model" );
			response.setMessage( "Cant find model" );
			return response;
		}

		newCar.setModel( modelOptional.get() );

		newCar.setOwner( -1l );
		newCar.setPlateNumber( car.getPlateNumber() );

		long priceList = car.getPriceList().getId();

		Optional< PriceList > priceListOptional = priceListRepository.findById( priceList );

		if ( !priceListOptional.isPresent() )
		{
			logger.error( ">>>> Cant find price list" );
			response.setMessage( "Cant find price list" );
			return response;
		}
		newCar.setPriceList( priceListOptional.get() );

		newCar.setProtection( car.isProtection() );

		long transmission = car.getTransmission().getId();

		Optional< Transmission > transmissionOptional = transmissionRepository.findById( transmission );

		if ( !transmissionOptional.isPresent() )
		{
			logger.error( ">>>> Cant find transmission" );
			response.setMessage( "Cant find transmission" );
			return response;
		}

		newCar.setTransmission( transmissionOptional.get() );

		Car save = carRepository.save( newCar );

		response.setSuccess( true );
		response.setMessage( save.getId().toString() );
		// FIXME images
		return response;

	}


	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "updateCarRequest" )
	@ResponsePayload
	public ApiResponseXML updateCar( @RequestPayload UpdateCarRequest request )
	{
		// TODO test

		ApiResponseXML response = new ApiResponseXML();
		response.setSuccess( false );

		long id = request.getCar().getId();

		Optional< Car > carOptional = carRepository.findById( id );

		if ( !carOptional.isPresent() )
		{
			logger.error( ">>>> Cant find car" );
			response.setMessage( "Cant find car" );
			return response;
		}

		Car car2update = carOptional.get();
		CarXML car = request.getCar();

		if ( car2update.getCompany() != car.getCompany() )
		{
			logger.error( "You cant update this car" );
			response.setMessage( "You cant update this car" );
			return response;
		}

		long brand = car.getBrand().getId();

		Optional< Brand > brandOptional = brandRepository.findById( brand );
		if ( !brandOptional.isPresent() )
		{
			logger.error( ">>>> Cant find brand" );
			response.setMessage( "Cant find brand" );
			return response;
		}
		car2update.setBrand( brandOptional.get() );

		long carClass = car.getCarClass().getId();

		Optional< CarClass > carClassOptional = carClassRepository.findById( carClass );

		if ( !carClassOptional.isPresent() )
		{
			logger.error( ">>>> Cant find car class" );
			response.setMessage( "Cant find car class" );
			return response;
		}

		car2update.setCarClass( carClassOptional.get() );
		car2update.setChildSeats( car.getChildSeats() );
		car2update.setCompany( car.getCompany() );
		car2update.setDiscount( car.getDiscount() );
		car2update.setDistanceAllowed( car.getDistanceAllowed() );

		long fuelType = car.getFuelType().getId();

		Optional< FuelType > fuelTypeOptional = fuelTypeRepository.findById( fuelType );
		if ( !fuelTypeOptional.isPresent() )
		{
			logger.error( ">>>> Cant find fuel type" );
			response.setMessage( "Cant find fuel type" );
			return response;
		}

		car2update.setFuelType( fuelTypeOptional.get() );

		// Set< CarPicture > images = car2update.getImages();
		//
		// carPictureRepository.deleteAll( images );
		// car2update.getImages().clear();
		// String ids = "";
		// for ( IdNameXML idNameXML : car.getImages().getImage() )
		// {
		// CarPicture cp = new CarPicture();
		//
		// cp.setLocation( idNameXML.getName() );
		// CarPicture save = carPictureRepository.save( cp );
		//
		// ids += save.getId() + ";";
		// car2update.getImages().add( cp );
		// }

		car2update.setMileage( car.getMileage() );

		long model = car.getModel().getId();

		Optional< Model > modelOptional = modelRepository.findById( model );

		if ( !modelOptional.isPresent() )
		{
			logger.error( ">>>> Cant find model" );
			response.setMessage( "Cant find model" );
			return response;
		}

		car2update.setModel( modelOptional.get() );

		car2update.setOwner( -1l );
		car2update.setPlateNumber( car.getPlateNumber() );

		long priceList = car.getPriceList().getId();

		Optional< PriceList > priceListOptional = priceListRepository.findById( priceList );

		if ( !priceListOptional.isPresent() )
		{
			logger.error( ">>>> Cant find price list" );
			response.setMessage( "Cant find price list" );
			return response;
		}
		car2update.setPriceList( priceListOptional.get() );

		car2update.setProtection( car.isProtection() );

		long transmission = car.getTransmission().getId();

		Optional< Transmission > transmissionOptional = transmissionRepository.findById( transmission );

		if ( !transmissionOptional.isPresent() )
		{
			logger.error( ">>>> Cant find transmission" );
			response.setMessage( "Cant find transmission" );
			return response;
		}

		car2update.setTransmission( transmissionOptional.get() );

		Car save = carRepository.save( car2update );

		response.setSuccess( true );
		response.setMessage( save.getId().toString() );
		// FIXME images
		return response;

	}


	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "removeCarRequest" )
	@ResponsePayload
	public ApiResponseXML removeCar( @RequestPayload RemoveCarRequest request )
	{
		ApiResponseXML response = new ApiResponseXML();
		response.setSuccess( false );

		long carId = request.getCarId();
		long companyId = request.getCompanyId();

		Optional< Car > carOptional = carRepository.findById( carId );

		if ( !carOptional.isPresent() )
		{
			logger.error( ">>>> Cant find car" );
			response.setMessage( "Cant find car" );
			return response;
		}

		Car car = carOptional.get();

		if ( car.getCompany() != companyId )
		{
			logger.error( ">>>> You cant remove this car" );
			response.setMessage( "You cant remove this car" );
			return response;
		}

		carRepository.delete( car );

		response.setSuccess( true );
		response.setMessage( "Removed" );
		return response;

	}


	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "createPriceListRequest" )
	@ResponsePayload
	public ApiResponseXML createPriceList( @RequestPayload CreatePriceListRequest request )
	{
		ApiResponseXML response = new ApiResponseXML();

		PriceListXML priceList = request.getPriceList();

		PriceList newPriceList = new PriceList();

		newPriceList.setCollisionDamageWaiver( priceList.getCollisionDamageWaiver() );
		newPriceList.setLimitedPrice( priceList.getLimitedPrice() );
		newPriceList.setName( priceList.getName() );
		newPriceList.setRegularPrice( priceList.getRegularPrice() );

		PriceList save = priceListRepository.save( newPriceList );

		response.setSuccess( true );
		response.setMessage( save.getId().toString() );
		return response;

	}

}
