package rs.xml.ws.ratingandcomment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.ratingandcomment.models.dtos.ApiResponse;
import rs.xml.ws.ratingandcomment.models.dtos.RatingDTO;
import rs.xml.ws.ratingandcomment.services.IRatingServiceUser;

@RestController
@RequestMapping( "/user/rating" )
public class RatingControllerUser
{

	@Autowired
	private IRatingServiceUser service;

	@PostMapping( "/" )
	public ResponseEntity< ApiResponse > createRating( @RequestHeader( required = true, value = "id" ) String id, @RequestBody RatingDTO rating )
	{
		return service.createRating( Long.parseLong( id ), rating );

	}


	@PutMapping( "/" )
	public ResponseEntity< ApiResponse > updateRating( @RequestHeader( required = true, value = "id" ) String id, @RequestBody RatingDTO rating )
	{
		return service.editRating( Long.parseLong( id ), rating );

	}


	@DeleteMapping( "/{rating}" )
	public ResponseEntity< ApiResponse > removeRating( @RequestHeader( required = true, value = "id" ) String id, @PathVariable Long rating )
	{
		return service.removeRating( Long.parseLong( id ), rating );

	}

}
