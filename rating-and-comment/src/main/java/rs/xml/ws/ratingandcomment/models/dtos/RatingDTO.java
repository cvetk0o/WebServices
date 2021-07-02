package rs.xml.ws.ratingandcomment.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO
{

	private Long id;

	private Long appointment;

	private Long car;

	private Long user;

	private Integer rating;

	private Long company;

}
