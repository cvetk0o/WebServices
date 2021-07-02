package rs.xml.ws.agentsoapconsumer.models.entities;

import java.time.LocalDateTime;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import org.hibernate.annotations.NaturalId;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Appointment
{

	@Id
	private Long id;

	@OneToOne( fetch = FetchType.EAGER )
	private Client client;

	@Column( nullable = false )
	private LocalDateTime start;

	@Column( nullable = false )
	private LocalDateTime end;

	private String startingPointLocation;

	@NaturalId
	@Enumerated( EnumType.STRING )
	private AppointmentStatus status;

	@OneToMany( )
	@JoinColumn( name = "appointment" )
	private Set< AppointmentItem > items;

}
