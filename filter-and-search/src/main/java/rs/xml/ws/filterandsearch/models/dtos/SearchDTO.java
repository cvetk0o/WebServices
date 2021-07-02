package rs.xml.ws.filterandsearch.models.dtos;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO
{

	private List< Long > cars;

	private String start;

	private String end;

}
