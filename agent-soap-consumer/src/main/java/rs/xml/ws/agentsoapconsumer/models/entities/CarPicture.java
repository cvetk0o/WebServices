package rs.xml.ws.agentsoapconsumer.models.entities;

import javax.persistence.Entity;
import javax.persistence.Id;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CarPicture
{

	@Id
	private Long id;

	private String location;

}
