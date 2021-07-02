package rs.xml.ws.ratingandcomment.services;

import org.springframework.http.ResponseEntity;


import rs.xml.ws.ratingandcomment.models.dtos.ApiResponse;
import rs.xml.ws.ratingandcomment.models.dtos.RatingDTO;

public interface IRatingServiceUser
{

	ResponseEntity< ApiResponse > createRating( Long id, RatingDTO rating );

	ResponseEntity< ApiResponse > editRating( Long id, RatingDTO rating );

	/**
	 * 
	 * @param user
	 *            if id is -1, then it is admin
	 * @param rating
	 *            id of rating
	 * @return
	 */
	ResponseEntity< ApiResponse > removeRating( Long user, Long rating );

}
