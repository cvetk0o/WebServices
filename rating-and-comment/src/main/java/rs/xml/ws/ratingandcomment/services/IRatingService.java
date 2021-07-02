package rs.xml.ws.ratingandcomment.services;

import java.util.List;
import java.util.Set;


import org.springframework.http.ResponseEntity;


import rs.xml.ws.ratingandcomment.models.Rating;

public interface IRatingService
{

	ResponseEntity< List< Rating > > getAllRatings();

	ResponseEntity< Set< Rating > > getByUser( Long id );

	ResponseEntity< Set< Rating > > getByCar( Long id );

}
