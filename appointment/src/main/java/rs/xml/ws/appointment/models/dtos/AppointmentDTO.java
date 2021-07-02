package rs.xml.ws.appointment.models.dtos;

import java.time.LocalDateTime;
import java.util.List;


import lombok.Data;

@Data
public class AppointmentDTO
{

	private Long id;

	private LocalDateTime start;

	private LocalDateTime end;

	private Long user;

	private String location;

	private List< Long > cars;

	private Long car;

	private Boolean bundle;

}
