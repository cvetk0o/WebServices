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
public class Model
{

	@Id
	private Long id;

	@Column( nullable = false, unique = true )
	private String name;

	public Model( String name )
	{
		super();
		this.name = name;

	}

}
