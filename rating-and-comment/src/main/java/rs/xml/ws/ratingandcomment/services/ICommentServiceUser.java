package rs.xml.ws.ratingandcomment.services;

import java.util.List;


import org.springframework.http.ResponseEntity;


import rs.xml.ws.ratingandcomment.models.Comment;
import rs.xml.ws.ratingandcomment.models.dtos.ApiResponse;
import rs.xml.ws.ratingandcomment.models.dtos.CommentDTO;

public interface ICommentServiceUser
{

	ResponseEntity< ApiResponse > createComment( Long user, CommentDTO comment );

	ResponseEntity< ApiResponse > editComment( Long user, CommentDTO comment );

	/**
	 * 
	 * @param user
	 *            if it is -1, then it is admin
	 * @param comment
	 *            id of comment
	 * @return
	 */
	ResponseEntity< ApiResponse > removeComment( Long user, Long comment );

	ResponseEntity< List< Comment > > getAllMyUnverifiedComments( Long id );

}
