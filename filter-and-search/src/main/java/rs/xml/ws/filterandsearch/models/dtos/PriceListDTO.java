package rs.xml.ws.filterandsearch.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceListDTO
{

	private Long id;

	private String name;

	private Double regularPrice;

	private Double limitedPrice;

	private Double collisionDamageWaiver;

}
