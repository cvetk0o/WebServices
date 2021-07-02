package rs.xml.ws.ratingandcomment.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.ratingandcomment.models.Comment;
import rs.xml.ws.ratingandcomment.models.dtos.ApiResponse;
import rs.xml.ws.ratingandcomment.models.dtos.CommentDTO;
import rs.xml.ws.ratingandcomment.services.ICommentServiceUser;

@RestController
@RequestMapping( "/user/comment" )
public class CommentControllerUser
{

	@Autowired
	private ICommentServiceUser service;

	@PostMapping( "/" )
	public ResponseEntity< ApiResponse > createComment( @RequestHeader( required = true, value = "id" ) String id, @RequestBody CommentDTO comment )
	{
		return service.createComment( Long.parseLong( id ), comment );

	}


	@PutMapping( "/" )
	public ResponseEntity< ApiResponse > updateComment( @RequestHeader( required = true, value = "id" ) String id, @RequestBody CommentDTO comment )
	{
		return service.editComment( Long.parseLong( id ), comment );

	}


	@DeleteMapping( "/{comment}" )
	public ResponseEntity< ApiResponse > removeComment( @RequestHeader( required = true, value = "id" ) String id, @PathVariable Long comment )
	{
		return service.removeComment( Long.parseLong( id ), comment );

	}


	@GetMapping( "/unverified" )
	public ResponseEntity< List< Comment > > getMyUnverifiedComments( @RequestHeader( required = true, value = "id" ) String id )
	{
		return service.getAllMyUnverifiedComments( Long.parseLong( id ) );

	}

}
