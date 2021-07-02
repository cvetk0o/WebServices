package rs.xml.ws.agentsoapconsumer.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import rs.xml.ws.agentsoapconsumer.models.entities.Client;
import rs.xml.ws.agentsoapconsumer.repositories.entities.IClientRepository;
import rs.xml.ws.agentsoapconsumer.services.ISynchService;
import rs.xml.ws.agentsoapconsumer.soap.GetAllUsersResponse;
import rs.xml.ws.agentsoapconsumer.soap.UserXML;
import rs.xml.ws.agentsoapconsumer.soap.clients.ZuulClient;

@Service
public class SynchService implements ISynchService
{

	@Autowired
	private IClientRepository clientRepository;

	@Autowired
	private ZuulClient zuulCLient;

	@Override
	public void synchAll()
	{
		synchUsers();
		synchCars();
		synchAppopintments();
		synchComments();
		synchMessages();
		synchRatings();

	}


	@Override
	public void synchUsers()
	{
		GetAllUsersResponse synchUsers = zuulCLient.synchUsers();

		for ( UserXML userXML : synchUsers.getUsers() )
		{
			if ( clientRepository.existsById( userXML.getId() ) )
			{
				continue;
			}

			Client c = new Client( userXML );

			clientRepository.save( c );
		}

	}


	@Override
	public void synchComments()
	{
		// TODO Auto-generated method stub

	}


	@Override
	public void synchRatings()
	{
		// TODO Auto-generated method stub

	}


	@Override
	public void synchAppopintments()
	{
		// TODO Auto-generated method stub

	}


	@Override
	public void synchMessages()
	{
		// TODO Auto-generated method stub

	}


	@Override
	public void synchCars()
	{
		// TODO Auto-generated method stub

	}

}
