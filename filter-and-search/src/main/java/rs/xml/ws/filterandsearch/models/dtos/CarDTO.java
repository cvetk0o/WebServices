package rs.xml.ws.filterandsearch.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarDTO
{

	private Long id;

	private String plateNumber;

	private Long brand;

	private Long model;

	private Long fuelType;

	private Long transmission;

	private Long carClass;

	private Long priceList;

	private Long company;

	private Long owner;

	private Double mileage;

	private Double distanceAllowed;

	private Double discount;

	private Boolean protection;

	private Integer childSeats;

}
