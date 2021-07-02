package rs.xml.ws.ratingandcomment.controllers;

import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.ratingandcomment.models.Comment;
import rs.xml.ws.ratingandcomment.services.ICommentService;

@RestController
@RequestMapping( "/public/comment" )
public class CommentController
{

	@Autowired
	private ICommentService service;

	@GetMapping( "/" )
	public ResponseEntity< List< Comment > > getAllValidComments()
	{
		return service.getAllVerifiedComments();

	}


	@GetMapping( "/user/{user}" )
	public ResponseEntity< Set< Comment > > getAllCommentsByUser( @PathVariable Long user )
	{
		return service.getByUser( user );

	}


	@GetMapping( "/car/{car}" )
	public ResponseEntity< Set< Comment > > getAllCommentsByCar( @PathVariable Long car )
	{
		return service.getByCar( car );

	}

}
