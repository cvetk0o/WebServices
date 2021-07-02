package rs.xml.ws.message.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import rs.xml.ws.message.models.dtos.ApiResponse;

@FeignClient( "netflix-zuul-gateway-server" )
public interface IFeignProxy
{

	@GetMapping( "api/auth/exists/{id}" )
	public ResponseEntity< ApiResponse > existsByIdUser( @PathVariable( "id" ) Long id );

	@GetMapping( "/appointment/exists/{user}/{car}" )
	public ResponseEntity< ApiResponse > existsById( @PathVariable Long user, @PathVariable( "car" ) Long car );

	@GetMapping( "/filter-and-search/company/mail/{id}" )
	public ResponseEntity< ApiResponse > getCompanyEmail( @PathVariable( "id" ) Long id );

	@GetMapping( "/exists/apointment/{a}/user/{u}" )
	public ResponseEntity< ApiResponse > checkAppointmentWithUser( @PathVariable( "a" ) Long a, @PathVariable( "u" ) Long u );

}
