package rs.xml.ws.agentsoapconsumer.exceptions;

import java.io.IOException;


import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerController
{

	private final Logger LOG = LoggerFactory.getLogger( this.getClass() );

	// @ExceptionHandler( CustomException.class )
	// public void handleCustomException( HttpServletResponse res, CustomException e
	// ) throws IOException
	// {
	// LOG.error( "ERROR", e );
	// res.sendError( e.getHttpStatus().value(), e.getMessage() );
	//
	// }

	@ExceptionHandler( AccessDeniedException.class )
	public void handleAccessDeniedException( HttpServletResponse res, AccessDeniedException e ) throws IOException
	{
		LOG.error( ">>> ERROR", e );

		res.sendError( HttpStatus.FORBIDDEN.value(), "Access denied" );

	}


	@ExceptionHandler( IllegalArgumentException.class )
	public void handleIllegalArgumentException( HttpServletResponse res, IllegalArgumentException e ) throws IOException
	{
		LOG.error( ">>> ERROR", e );
		res.sendError( HttpStatus.BAD_REQUEST.value(), "Illegal argument!" );

	}


	@ExceptionHandler( Exception.class )
	public void handleException( HttpServletResponse res, Exception e ) throws IOException
	{
		LOG.error( "ERROR", e );
		res.sendError( HttpStatus.BAD_REQUEST.value(), "Something went wrong" );

	}

}