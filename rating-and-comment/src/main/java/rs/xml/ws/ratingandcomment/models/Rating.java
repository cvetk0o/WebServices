package rs.xml.ws.ratingandcomment.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rating extends DateAudit
{

	private static final long serialVersionUID = 4590702548842519819L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( nullable = false, updatable = false )
	private Long car;

	@Column( nullable = false )
	private Long user;

	@Min( value = 1 )
	@Max( value = 5 )
	@Column( nullable = false )
	private Integer rating;

	@Column( nullable = false, updatable = false )
	private Long company;

}
