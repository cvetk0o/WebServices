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
import rs.xml.ws.ratingandcomment.models.dtos.MailDTO;
import rs.xml.ws.ratingandcomment.repositories.CommentRepository;
import rs.xml.ws.ratingandcomment.services.ICommentServiceAdmin;

@Service
public class CommentServiceAdmin implements ICommentServiceAdmin
{

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private IFeignProxy proxy;

	@Override
	public ResponseEntity< List< Comment > > getAllUnVerifiedComments()
	{

		logger.info( ">>>> Retriving unverified comments " );

		return new ResponseEntity< List< Comment > >( commentRepository.findByVerified( false ), HttpStatus.OK );

	}


	@Override
	public ResponseEntity< ApiResponse > removeComment( Long comment )
	{
		logger.info( ">>>> Retriving comment with id {}", comment );

		if ( !commentRepository.existsById( comment ) )
		{
			logger.error( ">>>> Cant find comment with id {}", comment );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find comment" ), HttpStatus.BAD_REQUEST );
		}

		Comment comment2remove = commentRepository.findById( comment ).get();

		logger.info( ">>>> Removing comment with id {}", comment );
		commentRepository.deleteById( comment );
		logger.info( ">>>> Comment with id {} is removed", comment );

		if ( comment2remove.getUser() != -1l ) // if user is -1, then the comment is made by company
		{
			sendMail( comment2remove.getUser(), "Your comment [" + comment2remove.getComment() + "] is removed", "Comment is removed", false );
		}
		else
		{
			sendMail( comment2remove.getCompany(), "Your comment [" + comment2remove.getComment() + "] is removed", "Comment is removed", true );
		}
		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Comment is removed" ), HttpStatus.OK );

	}


	public void sendMail( Long id, String message, String subject, Boolean toCompany )
	{
		ResponseEntity< ApiResponse > mail;

		if ( toCompany )
		{
			mail = proxy.getCompanyEmail( id );
		}
		else
		{
			mail = proxy.getMail( id );
		}

		if ( mail.getBody().getSuccess() )
		{
			MailDTO newMail = new MailDTO();

			newMail.setEmail( mail.getBody().getMessage() );
			newMail.setMessage( message );
			newMail.setSubject( "Comment removed" );
			proxy.sendMail( newMail );
		}

	}


	@Override
	public ResponseEntity< ApiResponse > verifyComment( Long id, Boolean action )
	{

		logger.info( ">>>> Retriving comment with id {} ", id );

		Optional< Comment > findById = commentRepository.findById( id );

		if ( !findById.isPresent() )
		{
			logger.error( ">>>> Cant find comment with id {}", id );
			return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find comment" ), HttpStatus.BAD_REQUEST );
		}

		Comment comment = findById.get();

		logger.info( ">>>> Verifying comment with id {} ", id );
		if ( action )
		{
			// comment should be verified
			comment.setVerified( true );
			commentRepository.save( comment );

			if ( !comment.getIsResponse() )
			{
				sendMail( comment.getUser(), "Your comment [" + comment.getComment() + "] is accepted", "Email is verified", false );
			}

		}
		else
		{
			commentRepository.delete( comment );

			if ( !comment.getIsResponse() )
			{
				sendMail( comment.getUser(), "Your comment [" + comment.getComment() + "] is not accepted and is removed", "Email is verified", false );
			}
		}

		logger.info( ">>>> Comment if processed " );
		return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Comment is processed" ), HttpStatus.OK );

	}

}
