package rs.xml.ws.filterandsearch.controllers;

import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import rs.xml.ws.filterandsearch.models.Car;
import rs.xml.ws.filterandsearch.models.dtos.ApiResponse;
import rs.xml.ws.filterandsearch.services.IPrivateService;

@RestController
@RequestMapping( "/user" )
public class UserController
{

	@Autowired
	private IPrivateService service;

	@PostMapping( "/car" )
	public ResponseEntity< ApiResponse > createCar( @RequestHeader( required = true, value = "id" ) String id, @RequestParam( "files" ) MultipartFile[] files,
			@RequestParam( "car" ) String car )
	{
		return service.createCar( car, Long.parseLong( id ), false, files );

	}


	@PatchMapping( "/car" )
	public ResponseEntity< ApiResponse > updateCar( @RequestHeader( required = true, value = "id" ) String id, @RequestParam( "files" ) MultipartFile[] files,
			@RequestBody String car )
	{
		return service.updateCar( car, Long.parseLong( id ), false, files );

	}


	@DeleteMapping( "/car/{car}" )
	public ResponseEntity< ApiResponse > removeCar( @RequestHeader( required = true, value = "id" ) String id, @PathVariable Long car )
	{
		return service.removeCar( car, Long.parseLong( id ), false );

	}


	@GetMapping( "/cars" )
	public ResponseEntity< Set< Car > > getMyCars( @RequestHeader( required = true, value = "id" ) String id )
	{
		return service.getMyCars( Long.parseLong( id ) );

	}

}
