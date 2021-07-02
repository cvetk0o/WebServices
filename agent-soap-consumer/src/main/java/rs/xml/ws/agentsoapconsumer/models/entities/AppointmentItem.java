package rs.xml.ws.agentsoapconsumer.models.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppointmentItem
{

	private Long id;

	private Double mileageStart;

	private Double mileageEnd;

	@ManyToOne( fetch = FetchType.EAGER )
	private Car car;

}
