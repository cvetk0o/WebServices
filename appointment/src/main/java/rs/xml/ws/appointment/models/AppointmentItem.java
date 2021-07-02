package rs.xml.ws.appointment.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppointmentItem
{

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( name = "car", nullable = false )
	private Long carId;

	@Column( name = "owner", nullable = false, updatable = false )
	private Long owner;

	private Double mileageStart;

	private Double mileageEnd;

	@OneToOne( fetch = FetchType.EAGER )
	private AppointmentReport report;

}
