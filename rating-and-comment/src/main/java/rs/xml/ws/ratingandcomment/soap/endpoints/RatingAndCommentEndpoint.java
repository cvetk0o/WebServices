package rs.xml.ws.ratingandcomment.soap.endpoints;

import java.util.Optional;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import rs.xml.ws.ratingandcomment.models.Comment;
import rs.xml.ws.ratingandcomment.models.Rating;
import rs.xml.ws.ratingandcomment.repositories.CommentRepository;
import rs.xml.ws.ratingandcomment.repositories.RatingRepository;
import rs.xml.ws.ratingandcomment.soap.AllCommentsRequest;
import rs.xml.ws.ratingandcomment.soap.AllCommentsResponse;
import rs.xml.ws.ratingandcomment.soap.AllRatingRequest;
import rs.xml.ws.ratingandcomment.soap.AllRatingResponse;
import rs.xml.ws.ratingandcomment.soap.CommentRequest;
import rs.xml.ws.ratingandcomment.soap.CommentResponse;
import rs.xml.ws.ratingandcomment.soap.CommentXML;
import rs.xml.ws.ratingandcomment.soap.RatingXML;

@Endpoint
public class RatingAndCommentEndpoint
{

	private static final String NAMESPACE_URI = "www.rent-a-car.ws.xml.rs/rating-and-comment";

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "allCommentsRequest" )
	@ResponsePayload
	public AllCommentsResponse synchComments( @RequestPayload AllCommentsRequest request )
	{

		AllCommentsResponse response = new AllCommentsResponse();

		logger.info( ">>>> Retriving comments where company === {}", request.getCompany() );
		Set< Comment > comments = commentRepository.findByCompany( request.getCompany() );

		for ( Comment iterator : comments )
		{
			CommentXML newComment = new CommentXML();

			newComment.setCar( iterator.getCar() );
			newComment.setComment( iterator.getComment() );
			newComment.setCreatedAt( iterator.getCreatedAt().toString() );
			newComment.setId( iterator.getId() );
			newComment.setUser( iterator.getUser() );
			newComment.setVerified( iterator.getVerified() );
			newComment.setIsResponse( iterator.getIsResponse() );
			newComment.setCommentId( iterator.getCommentId() );

			response.getComments().add( newComment );
		}

		logger.info( ">>>> Returning comments" );

		return response;

	}


	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "allRatingRequest" )
	@ResponsePayload
	public AllRatingResponse synchRatings( @RequestPayload AllRatingRequest request )
	{
		AllRatingResponse response = new AllRatingResponse();

		logger.info( ">>>> Retriving ratings where company === {}", request.getCompany() );
		Set< Rating > ratings = ratingRepository.findByCompany( request.getCompany() );

		for ( Rating iterator : ratings )
		{
			RatingXML newRating = new RatingXML();

			newRating.setCar( iterator.getCar() );
			newRating.setCreatedAt( iterator.getCreatedAt().toString() );
			newRating.setId( iterator.getId() );
			newRating.setRating( iterator.getRating() );
			newRating.setUser( iterator.getUser() );

			response.getRatings().add( newRating );
		}

		logger.info( ">>>> Returning comments" );

		return response;

	}


	@PayloadRoot( namespace = NAMESPACE_URI, localPart = "commentRequest" )
	@ResponsePayload
	public CommentResponse createComment( @RequestPayload CommentRequest request )
	{

		System.err.println( ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
		CommentResponse response = new CommentResponse();

		long commentId = request.getComment().getCommentId();

		Optional< Comment > commentOptional = commentRepository.findById( commentId );

		if ( !commentOptional.isPresent() )
		{
			logger.error( ">>>> Trying  to make response comment to non existing comment" );
			response.setSuccess( false );
			response.setMessage( "Comment to which you are tying to respond is not existing" );

			return response;
		}

		Comment comment2respond2 = commentOptional.get();

		CommentXML comment = request.getComment();

		Comment commentResponse = new Comment();

		commentResponse.setCar( comment2respond2.getCar() );
		commentResponse.setComment( comment.getComment() );
		commentResponse.setCommentId( comment2respond2.getId() );
		commentResponse.setCompany( comment2respond2.getCompany() );
		commentResponse.setIsResponse( true );
		commentResponse.setUser( -1l );
		commentResponse.setVerified( false );

		Comment save = commentRepository.save( commentResponse );
		System.err.println( ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
		response.setMessage( save.getId().toString() );
		response.setSuccess( true );

		return response;

	}

}
