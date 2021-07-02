package rs.xml.ws.ratingandcomment.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO
{

	private String subject;

	private String message;

	private String email;

}
