package rs.xml.ws.ratingandcomment.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.ratingandcomment.models.Comment;
import rs.xml.ws.ratingandcomment.models.dtos.ApiResponse;
import rs.xml.ws.ratingandcomment.services.ICommentServiceAdmin;

@RestController
@RequestMapping( "/admin" )
public class CommentControllerAdmin
{

	@Autowired
	private ICommentServiceAdmin service;

	@GetMapping( "/comments/unverified" )
	public ResponseEntity< List< Comment > > getAllUnverifiedComments()
	{
		return service.getAllUnVerifiedComments();

	}


	@DeleteMapping( "/comment/id/{comment}" )
	public ResponseEntity< ApiResponse > removeComment( @PathVariable Long comment )
	{
		return service.removeComment( comment );

	}


	@PatchMapping( "/comment/id/{comment}/action/{action}" )
	public ResponseEntity< ApiResponse > verifyComment( @PathVariable Long comment, @PathVariable Boolean action )
	{
		return service.verifyComment( comment, action );

	}

}
