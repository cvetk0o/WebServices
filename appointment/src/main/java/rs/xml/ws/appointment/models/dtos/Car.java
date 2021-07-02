package rs.xml.ws.appointment.models.dtos;

import lombok.Data;

@Data
public class Car
{

	private Long id;

	private String plateNumber;

	private Long company;

	private Long owner;

	private Double mileage;

	private Double distanceAllowed;

	private Double discount;

	private Boolean protection;

	private Integer childSeats;

}
