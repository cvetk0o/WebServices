package rs.xml.ws.ratingandcomment.services.implementations;

import java.util.List;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import rs.xml.ws.ratingandcomment.models.Rating;
import rs.xml.ws.ratingandcomment.repositories.RatingRepository;
import rs.xml.ws.ratingandcomment.services.IRatingService;

@Service
public class RatingService implements IRatingService
{

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public ResponseEntity< List< Rating > > getAllRatings()
	{

		logger.info( ">>>> Retriving all ratings" );
		List< Rating > findAll = ratingRepository.findAll();

		return new ResponseEntity< List< Rating > >( findAll, HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Rating > > getByUser( Long id )
	{

		logger.info( ">>>> Retriving all ratings from user with id {}", id );

		Set< Rating > findByUser = ratingRepository.findByUser( id );
		return new ResponseEntity< Set< Rating > >( findByUser, HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Rating > > getByCar( Long id )
	{

		logger.info( ">>>> Retriving all ratings from appointment with id {}", id );

		Set< Rating > findByAppointment = ratingRepository.findByCar( id );
		return new ResponseEntity< Set< Rating > >( findByAppointment, HttpStatus.OK );

	}

}
