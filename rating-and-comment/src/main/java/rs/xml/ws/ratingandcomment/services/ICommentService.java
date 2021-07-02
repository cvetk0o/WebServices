package rs.xml.ws.ratingandcomment.services;

import java.util.List;
import java.util.Set;


import org.springframework.http.ResponseEntity;


import rs.xml.ws.ratingandcomment.models.Comment;

public interface ICommentService
{

	ResponseEntity< List< Comment > > getAllVerifiedComments();

	ResponseEntity< Set< Comment > > getByUser( Long id );

	ResponseEntity< Set< Comment > > getByCar( Long id );

}
