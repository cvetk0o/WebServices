package rs.xml.ws.appointment.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppointmentReport
{

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	private Double distance;

	private String note;

}
