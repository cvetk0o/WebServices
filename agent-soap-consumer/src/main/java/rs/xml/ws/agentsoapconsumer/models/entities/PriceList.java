package rs.xml.ws.agentsoapconsumer.models.entities;

import javax.persistence.Entity;
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
	private Long id;

	private String name;

	private Double regularPrice;

	private Double limitedPrice;

	private Double collisionDamageWaiver;

}
