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
public class Brand
{

	@Id
	private Long id;

	@Column( nullable = false, unique = true )
	private String name;

	public Brand( String name )
	{
		super();
		this.name = name;

	}

}
