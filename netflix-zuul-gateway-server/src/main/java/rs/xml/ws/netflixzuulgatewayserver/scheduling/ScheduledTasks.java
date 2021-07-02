package rs.xml.ws.netflixzuulgatewayserver.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks
{

	Logger logger = LoggerFactory.getLogger( this.getClass() );

	// FIXME: uncomment this at the end, please
	// @Autowired
	// private JwtTokenProvider provider;
	//
	// @Autowired
	// private JwtTokenRepository repository;

	// @Scheduled( fixedDelay = 60000 )
	// public void sniffTokens()
	// {
	// logger.info( ">>>>>>>>>>>>>>>> ajaoooo" );
	//
	// List< JwtToken > tokens = repository.findAll();
	//
	// Set< JwtToken > tokens2remove =
	// tokens.stream().filter( t -> provider.getExpirationFromJWT( t.getToken()
	// ).after( new Date() ) ).collect( Collectors.toSet() );
	//
	// repository.deleteAll( tokens2remove );
	//
	// }

}
