package rs.xml.ws.appointment.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


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
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( name = "user", nullable = false, updatable = false )
	private Long userId;

	@Column( name = "company", nullable = false, updatable = false )
	private Long company;

	@Column( nullable = false )
	private LocalDateTime start;

	@Column( nullable = false )
	private LocalDateTime end;

	private String startingPointLocation;

	@Enumerated( EnumType.STRING )
	private AppointmentStatus status;

	@OneToMany( )
	@JoinColumn( name = "appointment" )
	private Set< AppointmentItem > items = new HashSet< AppointmentItem >();

}
