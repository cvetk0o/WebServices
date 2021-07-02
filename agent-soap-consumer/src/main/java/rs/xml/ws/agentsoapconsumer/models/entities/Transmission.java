package rs.xml.ws.agentsoapconsumer.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transmission
{

	@Id
	private Long id;

	@Column( nullable = false, unique = true )
	private String name;

	public Transmission( String name )
	{
		this.name = name;

	}

}
