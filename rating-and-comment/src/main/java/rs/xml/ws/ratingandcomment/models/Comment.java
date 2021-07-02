package rs.xml.ws.ratingandcomment.models;

import javax.persistence.Column;
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
public class Comment extends DateAudit
{

	private static final long serialVersionUID = 981220635624066529L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( nullable = false, updatable = false )
	private Long user;

	@Column( nullable = false, updatable = false )
	private Long car;

	@Column( nullable = false )
	private String comment;

	private Boolean verified;

	private Boolean isResponse;

	private Long commentId;

	@Column( nullable = false, updatable = false )
	private Long company;

}
