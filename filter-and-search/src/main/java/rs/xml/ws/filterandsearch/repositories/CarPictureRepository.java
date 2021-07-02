package rs.xml.ws.filterandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.filterandsearch.models.CarPicture;

@Repository
public interface CarPictureRepository extends JpaRepository< CarPicture, Long >
{

}
