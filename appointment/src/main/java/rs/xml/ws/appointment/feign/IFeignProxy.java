package rs.xml.ws.appointment.feign;

import java.util.List;
import java.util.Map;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import rs.xml.ws.appointment.models.dtos.ApiResponse;
import rs.xml.ws.appointment.models.dtos.Car;

@FeignClient( "netflix-zuul-gateway-server" )
public interface IFeignProxy
{

	@PostMapping( "/filter-and-search/public/check/owner/{c}/{o}" )
	public ResponseEntity< ApiResponse > checkCarsOwner( @PathVariable Long c, @PathVariable Long o );

	@PostMapping( "/filter-and-search/public/exists/cars/{company}" )
	public ResponseEntity< ApiResponse > checkCarsExists( @RequestBody List< Long > cars, @PathVariable Long company );

	@GetMapping( "/filter-and-search/public/car/present/{id}" )
	public ResponseEntity< ApiResponse > isCarPresent( @PathVariable Long id );

	@PostMapping( "/filter-and-search/public/group/cars" )
	public ResponseEntity< Map< Long, Long > > groupCars( @RequestBody List< Long > cars );

	@GetMapping( "/filter-and-search/public/cars" )
	public ResponseEntity< List< Car > > getAllCars();

}
