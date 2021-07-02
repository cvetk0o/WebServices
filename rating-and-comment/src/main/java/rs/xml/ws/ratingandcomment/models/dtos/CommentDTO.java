package rs.xml.ws.ratingandcomment.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO
{

	private Long id;

	private Long user;

	private Long appointment;

	private Long car;

	private String comment;

	private Long company;

	private Boolean response;

	private Long commentId;

}
