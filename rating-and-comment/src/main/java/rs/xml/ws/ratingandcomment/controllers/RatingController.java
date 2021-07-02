package rs.xml.ws.ratingandcomment.controllers;

import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import rs.xml.ws.ratingandcomment.models.Rating;
import rs.xml.ws.ratingandcomment.services.IRatingService;

@RestController
@RequestMapping( "/public/rating" )
public class RatingController
{

	@Autowired
	private IRatingService service;

	@GetMapping( "/" )
	public ResponseEntity< List< Rating > > getAllRatings()
	{
		return service.getAllRatings();

	}


	@GetMapping( "/appointment/{car}" )
	public ResponseEntity< Set< Rating > > getAllRatingsForCar( @PathVariable Long car )
	{
		return service.getByCar( car );

	}


	@GetMapping( "/user/{user}" )
	public ResponseEntity< Set< Rating > > getAllRatingsForUser( @PathVariable Long user )
	{
		return service.getByUser( user );

	}

}
