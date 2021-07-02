package rs.xml.ws.netflixzuulgatewayserver.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import rs.xml.ws.netflixzuulgatewayserver.security.JwtAuthenticationEntryPoint;
import rs.xml.ws.netflixzuulgatewayserver.security.JwtAuthenticationFilter;
import rs.xml.ws.netflixzuulgatewayserver.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( securedEnabled = true, jsr250Enabled = true, prePostEnabled = true )
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter()
	{
		return new JwtAuthenticationFilter();

	}


	@Override
	public void configure( AuthenticationManagerBuilder authenticationManagerBuilder ) throws Exception
	{
		authenticationManagerBuilder.userDetailsService( customUserDetailsService ).passwordEncoder( passwordEncoder() );

	}


	@Bean( BeanIds.AUTHENTICATION_MANAGER )
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();

	}


	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder( 15 );

	}


	@Override
	protected void configure( HttpSecurity http ) throws Exception
	{
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint( unauthorizedHandler ).and().sessionManagement()
				.sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and().authorizeRequests()
				.antMatchers( "/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js" ).permitAll()

				// zuul
				.antMatchers( "/api/auth/me", "/api/auth/signout" ).hasAnyRole( "USER", "ADMIN" )

				.antMatchers( "/admin/**" ).hasRole( "ADMIN" )

				.antMatchers( "/api/auth/**", "/ws/**", "/api/auth/exists/**", "/api/auth/mail/**", "/api/auth/users/**" ).permitAll()

				// Message
				.antMatchers( "/message/ws/**" ).permitAll()

				.antMatchers( "/message/mail/**" ).permitAll()

				.antMatchers( "/message/**" ).hasRole( "MESSAGE" )
				// Rating and comment
				.antMatchers( "/rating-and-comment/ws/**" ).permitAll()

				.antMatchers( "/rating-and-comment/public/**" ).permitAll()

				.antMatchers( "/rating-and-comment/admin/**" ).hasRole( "ADMIN" )

				.antMatchers( "/rating-and-comment/user/rating/**" ).hasRole( "RATING" )

				.antMatchers( "/rating-and-comment/user/comment/**" ).hasRole( "COMMENT" )
				// Filter
				.antMatchers( "/filter-and-search/ws/**" ).permitAll()

				.antMatchers( "/filter-and-search/user/**" ).hasRole( "USER" )

				.antMatchers( "/filter-and-search/public/**" ).permitAll()

				// Appointment
				.antMatchers( "/appointment/ws/**", "/appointment/exists/**", "/appointment/check", "/appointment/used/**", "/appointment/customers/**" )
				.permitAll()

				// FIXME change this to exact role
				.antMatchers( "/appointment/**" ).hasRole( "USER" )

				.anyRequest().authenticated();

		http.addFilterBefore( jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class );

	}

}
