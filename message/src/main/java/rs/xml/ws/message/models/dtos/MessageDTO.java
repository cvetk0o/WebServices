package rs.xml.ws.message.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO
{

	private Long id;

	private Long sender;

	private Long receiver;

	private String message;

	private String subject;

	private Long company;

	private Long appointment;

}
