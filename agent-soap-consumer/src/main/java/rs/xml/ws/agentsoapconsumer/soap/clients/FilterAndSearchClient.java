package rs.xml.ws.agentsoapconsumer.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


import rs.xml.ws.agentsoapconsumer.payload.CarDTO;
import rs.xml.ws.agentsoapconsumer.payload.PriceListDTO;
import rs.xml.ws.agentsoapconsumer.soap.ApiResponseXML;
import rs.xml.ws.agentsoapconsumer.soap.CreateCarRequest;
import rs.xml.ws.agentsoapconsumer.soap.CreatePriceListRequest;
import rs.xml.ws.agentsoapconsumer.soap.GetAllCompanyDataRequest;
import rs.xml.ws.agentsoapconsumer.soap.GetAllCompanyDataResponse;
import rs.xml.ws.agentsoapconsumer.soap.RemoveCarRequest;
import rs.xml.ws.agentsoapconsumer.soap.UpdateCarRequest;

public class FilterAndSearchClient extends WebServiceGatewaySupport
{

	public GetAllCompanyDataResponse synchAll( Long company )
	{
		// TODO
		GetAllCompanyDataRequest request = new GetAllCompanyDataRequest();
		request.setCompany( company );

		GetAllCompanyDataResponse response = ( GetAllCompanyDataResponse ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.APPOINTMENT, request );

		return response;

	}


	public ApiResponseXML createCar( CarDTO car )
	{ // TODO
		CreateCarRequest request = new CreateCarRequest();

		request.setCar( null );
		ApiResponseXML response = ( ApiResponseXML ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.FILTER_AND_SEARCH, request );

		return response;

	}


	public ApiResponseXML updateCar( CarDTO car )
	{ // TODO
		UpdateCarRequest request = new UpdateCarRequest();

		request.setCar( null );
		ApiResponseXML response = ( ApiResponseXML ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.FILTER_AND_SEARCH, request );

		return response;

	}


	public ApiResponseXML removeCar( Long car )
	{ // TODO
		RemoveCarRequest request = new RemoveCarRequest();

		request.setCarId( car );
		ApiResponseXML response = ( ApiResponseXML ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.FILTER_AND_SEARCH, request );

		return response;

	}


	public ApiResponseXML createPriceList( PriceListDTO priceList )
	{
		// TODO
		CreatePriceListRequest request = new CreatePriceListRequest();

		ApiResponseXML response = ( ApiResponseXML ) getWebServiceTemplate().marshalSendAndReceive( NameSpaceURI.FILTER_AND_SEARCH, request );

		return response;

	}

}
