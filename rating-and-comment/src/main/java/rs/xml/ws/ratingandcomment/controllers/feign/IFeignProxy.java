package rs.xml.ws.ratingandcomment.controllers.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import rs.xml.ws.ratingandcomment.models.dtos.ApiResponse;
import rs.xml.ws.ratingandcomment.models.dtos.MailDTO;

@FeignClient( name = "netflix-zuul-gateway-server" ) // zuul
// @RibbonClient( name = "netflix-zuul-gateway-server" ) // app to talk to
// @FeignClient( "appointment" )
public interface IFeignProxy
{

	@GetMapping( "/appointment/exists/{user}/{car}" )
	public ResponseEntity< ApiResponse > existsById( @PathVariable( "user" ) Long user, @PathVariable( "car" ) Long car );

	@PostMapping( "/message/mail" )
	public ResponseEntity< ApiResponse > sendMail( @RequestBody MailDTO mail );

	@GetMapping( "/api/auth/mail/{id}" )
	public ResponseEntity< ApiResponse > getMail( @PathVariable( "id" ) Long id );

	@GetMapping( "/filter-and-search/public/company/mail/{id}" )
	public ResponseEntity< ApiResponse > getCompanyEmail( @PathVariable( "id" ) Long id );

}
