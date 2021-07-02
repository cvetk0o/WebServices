package rs.xml.ws.agentsoapconsumer.models;

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
public class Company
{

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	private String email;

	private String name;

	private String adress;

	private Long bussinessCode;

}