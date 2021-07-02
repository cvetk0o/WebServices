package rs.xml.ws.filterandsearch.feign;

import java.util.List;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import rs.xml.ws.filterandsearch.models.dtos.ApiResponse;
import rs.xml.ws.filterandsearch.models.dtos.SearchDTO;

@FeignClient( "netflix-zuul-gateway-server" )
public interface IFeignProxy
{

	@PostMapping( "/appointment/check" )
	public ResponseEntity< List< Long > > checkIfFree( @RequestBody SearchDTO search );

	@GetMapping( "/appointment/used/{id}" )
	public ResponseEntity< ApiResponse > checkIfCarIsInUse( @PathVariable( "id" ) Long id );

	@GetMapping( "api/auth/exists/{id}" )
	public ResponseEntity< ApiResponse > existsByIdUser( @PathVariable( "id" ) Long id );

}
