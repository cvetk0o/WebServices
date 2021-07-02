package rs.xml.ws.netflixzuulgatewayserver.filter;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


import rs.xml.ws.netflixzuulgatewayserver.models.User;
import rs.xml.ws.netflixzuulgatewayserver.repositories.UserRepository;

@Component
public class AuthenticationFilter extends ZuulFilter
{

	@Autowired
	private UserRepository repo;

	@Override
	public boolean shouldFilter()
	{
		return true;

	}


	@Override
	public Object run() throws ZuulException
	{

		RequestContext context = RequestContext.getCurrentContext();

		if ( SecurityContextHolder.getContext().getAuthentication() != null )
		{
			String username = SecurityContextHolder.getContext().getAuthentication().getName();

			Optional< User > userOptional = repo.findByUsername( username );

			Long id = -1L;

			if ( userOptional.isPresent() )
			{
				id = userOptional.get().getId();
			}

			context.addZuulRequestHeader( "id", id.toString() );

		}

		return null;

	}


	@Override
	public String filterType()
	{
		return "pre";

	}


	@Override
	public int filterOrder()
	{
		return 1;

	}

}
