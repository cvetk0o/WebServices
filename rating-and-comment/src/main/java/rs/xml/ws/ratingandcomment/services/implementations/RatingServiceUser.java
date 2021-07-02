package rs.xml.ws.ratingandcomment.services.implementations;

import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import rs.xml.ws.ratingandcomment.controllers.feign.IFeignProxy;
import rs.xml.ws.ratingandcomment.models.Rating;
import rs.xml.ws.ratingandcomment.models.dtos.ApiResponse;
import rs.xml.ws.ratingandcomment.models.dtos.RatingDTO;
import rs.xml.ws.ratingandcomment.repositories.RatingRepository;
import rs.xml.ws.ratingandcomment.services.IRatingServiceUser;

@Service
public class RatingServiceUser implements IRatingServiceUser
{

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private IFeignProxy proxy;

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public ResponseEntity< ApiResponse > createRating( Long id, RatingDTO rating )
	{

		logger.info( ">>>> Checking if appointment with id {} exists", rating.getCar() );

		ResponseEntity< ApiResponse > existsById = proxy.existsById( id, rating.getCar() );

		if ( existsById.getBody().getSuccess() )
		{

			// can be created

			Optional< Rating > ratingOptional = ratingRepository.findByUserAndCar( id, rating.getCar() );
			if ( ratingOptional.isPresent() )
			{
				logger.info( ">>>> User has already rated car" );
				rating.setId( ratingOptional.get().getId() );
				rating.setCompany( ratingOptional.get().getCompany() );

				return editRating( id, rating );
			}

			logger.info( ">>>> Creating rating" );

			Rating newRating = new Rating();

			newRating.setCar( rating.getCar() );
			newRating.setCompany( rating.getCompany() );
			newRating.setRating( rating.getRating() );
			newRating.setUser( id );

			logger.info( ">>>> Saving rating" );

			ratingRepository.save( newRating );
			return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Created" ), HttpStatus.CREATED );

		}
		logger.error( ">>>> Cant create rating" );

		return new ResponseEntity< ApiResponse >( new ApiResponse( false, existsById.getBody().getMessage() ), HttpStatus.BAD_REQUEST );

	}


	@Override
	public ResponseEntity< ApiResponse > editRating( Long id, RatingDTO rating )
	{

		logger.info( ">>>> Retriving rating with id {}", rating.getId() );
		Optional< Rating > ratingOptional = ratingRepository.findByUserAndCar( id, rating.getCar() );
		logger.info( ">>>> Checking if creator of rating with id {} is user with id {}", rating.getId(), id );

		if ( !ratingOptional.isPresent() )
		{
			logger.error( ">>>> Cant update rating" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "" ), HttpStatus.BAD_REQUEST );
		}

		Rating rating2change = ratingOptional.get();

		if ( rating2change.getUser() != id )
		{
			logger.error( ">>>> This is not yours rating" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "This is not your rating" ), HttpStatus.BAD_REQUEST );
		}

		logger.info( ">>>> Updating rating" );

		rating2change.setRating( rating.getRating() );
		ratingRepository.save( rating2change );
		logger.info( ">>>> Saving rating" );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated rating" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > removeRating( Long user, Long rating )
	{

		logger.info( ">>>> Retriving rating" );

		Optional< Rating > ratingOptional = ratingRepository.findById( rating );

		if ( !ratingOptional.isPresent() )
		{
			logger.error( ">>>> Cant find rating with id {}" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "" ), HttpStatus.BAD_REQUEST );
		}
		Rating rating2remove = ratingOptional.get();

		logger.info( ">>>> Checking if creator of rating with id {} is user with id {}", rating, user );
		if ( rating2remove.getUser() != user )
		{
			logger.error( ">>>> User cant change rating which he didnt create" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "" ), HttpStatus.BAD_REQUEST );
		}

		logger.info( ">>>> Removing rating" );

		ratingRepository.delete( rating2remove );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" ), HttpStatus.OK );

	}

}
