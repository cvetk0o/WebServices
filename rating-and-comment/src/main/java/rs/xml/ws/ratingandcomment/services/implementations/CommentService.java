package rs.xml.ws.ratingandcomment.services.implementations;

import java.util.List;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import rs.xml.ws.ratingandcomment.models.Comment;
import rs.xml.ws.ratingandcomment.repositories.CommentRepository;
import rs.xml.ws.ratingandcomment.services.ICommentService;

@Service
public class CommentService implements ICommentService
{

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public ResponseEntity< List< Comment > > getAllVerifiedComments()
	{

		logger.info( ">>>> Retriving verified comments" );

		return new ResponseEntity< List< Comment > >( commentRepository.findByVerified( true ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Comment > > getByUser( Long id )
	{
		logger.info( ">>>> Retriving verified comments for user with id {}", id );

		return new ResponseEntity< Set< Comment > >( commentRepository.findByUser( id ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< Set< Comment > > getByCar( Long id )
	{

		logger.info( ">>>> Retriving verified comments for car with id {}", id );

		return new ResponseEntity< Set< Comment > >( commentRepository.findByCar( id ), HttpStatus.OK );

	}

}
