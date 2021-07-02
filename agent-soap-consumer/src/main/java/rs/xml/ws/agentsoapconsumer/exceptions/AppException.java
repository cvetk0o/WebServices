package rs.xml.ws.agentsoapconsumer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
public class AppException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2299431941446764197L;

	public AppException( String message )
	{
		super( message );

	}


	public AppException( String message, Throwable cause )
	{
		super( message, cause );

	}

}