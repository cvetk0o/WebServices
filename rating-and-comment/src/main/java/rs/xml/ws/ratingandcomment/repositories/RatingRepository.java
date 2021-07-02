package rs.xml.ws.ratingandcomment.repositories;

import java.util.Optional;
import java.util.Set;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.ratingandcomment.models.Rating;

@Repository
public interface RatingRepository extends JpaRepository< Rating, Long >
{

	Set< Rating > findByUser( Long id );

	Optional< Rating > findByUserAndCar( Long user, long car );

	Set< Rating > findByCompany( Long company );

	Set< Rating > findByCar( Long car );

}
