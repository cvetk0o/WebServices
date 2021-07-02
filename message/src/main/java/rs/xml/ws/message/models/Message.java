package rs.xml.ws.message.models;

import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.springframework.data.annotation.CreatedDate;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message
{

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( nullable = false )
	private Long sender;

	@Column( nullable = false )
	private Long receiver;

	@Column( nullable = false )
	private String message;

	@Column( nullable = false )
	private String subject;

	private Long company;

	private Long appointment;

	@CreatedDate
	@Column( nullable = false, updatable = false )
	private LocalDateTime createdAt;

}
