package rs.xml.ws.ratingandcomment.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.ratingandcomment.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository< Comment, Long >
{

	Set< Comment > findByUser( Long id );

	Optional< Comment > findByUserAndId( Long user, Long id );

	List< Comment > findByVerified( Boolean verified );

	Set< Comment > findByCar( Long car );

	List< Comment > findByUserAndVerified( Long user, Boolean verified );

	Boolean existsByUserAndId( Long user, Long id );

	Set< Comment > findByCompany( Long company );

	Optional< Comment > findByUserAndCar( Long user, Long car );

}
