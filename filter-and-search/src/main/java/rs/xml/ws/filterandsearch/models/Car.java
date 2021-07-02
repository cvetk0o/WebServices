package rs.xml.ws.filterandsearch.models;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import org.hibernate.annotations.NaturalId;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car
{

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@NaturalId
	@Column( nullable = false, unique = true )
	private String plateNumber;

	@ManyToOne( fetch = FetchType.EAGER )
	private Brand brand;

	@ManyToOne( fetch = FetchType.EAGER )
	private Model model;

	@ManyToOne( fetch = FetchType.EAGER )
	private FuelType fuelType;

	@ManyToOne( fetch = FetchType.EAGER )
	private Transmission transmission;

	@ManyToOne( fetch = FetchType.EAGER )
	private CarClass carClass;

	@OneToMany( )
	@JoinColumn( name = "car_id" )
	private Set< CarPicture > images = new HashSet< CarPicture >();

	@ManyToOne( fetch = FetchType.EAGER )
	private PriceList priceList;

	@Column( updatable = false, nullable = true )
	private Long company;

	@Column( updatable = false, nullable = true )
	private Long owner;

	private Double mileage;

	private Double distanceAllowed;

	private Double discount;

	private Boolean protection;

	private Integer childSeats;

	public String getPlatNumber()
	{

		return plateNumber.toUpperCase();

	}


	public void setPlateNumber( String plate )
	{
		this.plateNumber = plate.toUpperCase();

	}


	public Car( String plate_number, Double mileage, Double distance_allowed, Boolean protection, Integer child_seats )
	{
		super();
		this.plateNumber = plate_number;
		this.mileage = mileage;
		this.distanceAllowed = distance_allowed;
		this.protection = protection;
		this.childSeats = child_seats;

	}

}
