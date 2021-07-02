package rs.xml.ws.ratingandcomment.services;

import java.util.List;


import org.springframework.http.ResponseEntity;


import rs.xml.ws.ratingandcomment.models.Comment;
import rs.xml.ws.ratingandcomment.models.dtos.ApiResponse;

public interface ICommentServiceAdmin
{

	ResponseEntity< List< Comment > > getAllUnVerifiedComments();

	// TODO send mail
	ResponseEntity< ApiResponse > removeComment( Long comment );

	// TODO send mail
	ResponseEntity< ApiResponse > verifyComment( Long id, Boolean action );

}
