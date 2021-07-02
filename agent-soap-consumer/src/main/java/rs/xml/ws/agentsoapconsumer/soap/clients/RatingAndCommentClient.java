package rs.xml.ws.agentsoapconsumer.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


import rs.xml.ws.agentsoapconsumer.payload.CommentDTO;
import rs.xml.ws.agentsoapconsumer.soap.AllCommentsRequest;
import rs.xml.ws.agentsoapconsumer.soap.AllCommentsResponse;
import rs.xml.ws.agentsoapconsumer.soap.AllRatingRequest;
import rs.xml.ws.agentsoapconsumer.soap.AllRatingResponse;
import rs.xml.ws.agentsoapconsumer.soap.CommentRequest;
import rs.xml.ws.agentsoapconsumer.soap.CommentResponse;

public class RatingAndCommentClient extends WebServiceGatewaySupport
{

	public AllCommentsResponse synchComments( Long company )
	{
		AllCommentsRequest request = new AllCommentsRequest();

		request.setCompany( company );

		AllCommentsResponse response = ( AllCommentsResponse ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.RATING_AND_COMMENT, request );

		return response;

	}


	public AllRatingResponse synchRatings( Long company )
	{
		AllRatingRequest request = new AllRatingRequest();

		request.setCompany( company );

		AllRatingResponse response = ( AllRatingResponse ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.RATING_AND_COMMENT, request );

		return response;

	}


	public CommentResponse createComment( CommentDTO comment, Long company )
	{
		CommentRequest request = new CommentRequest();

		CommentResponse response = ( CommentResponse ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.RATING_AND_COMMENT, request );

		return response;

	}

}
