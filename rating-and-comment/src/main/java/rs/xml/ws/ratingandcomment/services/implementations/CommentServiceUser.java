package rs.xml.ws.ratingandcomment.services.implementations;

import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import rs.xml.ws.ratingandcomment.controllers.feign.IFeignProxy;
import rs.xml.ws.ratingandcomment.models.Comment;
import rs.xml.ws.ratingandcomment.models.dtos.ApiResponse;
import rs.xml.ws.ratingandcomment.models.dtos.CommentDTO;
import rs.xml.ws.ratingandcomment.repositories.CommentRepository;
import rs.xml.ws.ratingandcomment.services.ICommentServiceUser;

@Service
public class CommentServiceUser implements ICommentServiceUser
{

	@Autowired
	private IFeignProxy proxy;

	@Autowired
	private CommentRepository commentRepository;

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Override
	public ResponseEntity< ApiResponse > createComment( Long user, CommentDTO comment )
	{

		logger.info( ">>>> Checking if appointment with id {} exists", comment.getId() );
		ResponseEntity< ApiResponse > existsById = proxy.existsById( user, comment.getCar() );

		if ( existsById.getBody().getSuccess() )
		{
			Optional< Comment > commentOptional = commentRepository.findByUserAndCar( user, comment.getCar() );

			if ( commentOptional.isPresent() )
			{
				comment.setId( commentOptional.get().getId() );
				comment.setCompany( commentOptional.get().getCompany() );
				logger.info( ">>>> User has already commented this car" );
				return editComment( user, comment );
			}

			logger.info( ">>>> Creating comment with id {} ", comment );

			Comment newComment = new Comment();

			newComment.setCar( comment.getCar() );
			newComment.setComment( comment.getComment() );
			newComment.setCompany( comment.getCompany() );
			newComment.setUser( user );
			newComment.setVerified( false );
			newComment.setCommentId( comment.getCommentId() );
			newComment.setIsResponse( comment.getResponse() );

			commentRepository.save( newComment );

			return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Created" ), HttpStatus.CREATED );
		}

		return new ResponseEntity< ApiResponse >( new ApiResponse( false, existsById.getBody().getMessage() ), HttpStatus.BAD_REQUEST );

	}


	@Override
	public ResponseEntity< ApiResponse > editComment( Long user, CommentDTO comment )
	{

		logger.info( ">>>> Checking if appointment with id {} exists", comment.getId() );

		Optional< Comment > optionalComment = commentRepository.findByUserAndId( user, comment.getId() );

		if ( !optionalComment.isPresent() )
		{
			logger.error( ">>>> Cant find comment with id {}", comment.getId() );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find comment" ), HttpStatus.BAD_REQUEST );
		}

		Comment comment2change = optionalComment.get();

		comment2change.setComment( comment.getComment() );

		comment2change.setVerified( false );

		commentRepository.save( comment2change );

		logger.info( ">>>> Updating comment with id {} ", comment.getId() );
		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Comment is updated" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > removeComment( Long user, Long comment )
	{

		logger.info( ">>>> Retriving comment with id {} ", comment );
		Boolean isPresent = commentRepository.existsByUserAndId( user, comment );

		if ( !isPresent )
		{
			logger.error( ">>>> Cant find comment" );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "" ), HttpStatus.BAD_REQUEST );
		}

		logger.info( ">>>> Removing comment with id {} ", comment );

		commentRepository.deleteById( comment );

		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Removed" ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< List< Comment > > getAllMyUnverifiedComments( Long id )
	{

		logger.info( ">>>> Retriving comments created by user with id {} ", id );

		return new ResponseEntity< List< Comment > >( commentRepository.findByUserAndVerified( id, false ), HttpStatus.OK );

	}

}
