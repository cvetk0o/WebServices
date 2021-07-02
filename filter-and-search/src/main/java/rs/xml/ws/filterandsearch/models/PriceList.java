package rs.xml.ws.filterandsearch.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceList
{

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	private String name;

	private Double regularPrice;

	private Double limitedPrice;

	private Double collisionDamageWaiver;

}
