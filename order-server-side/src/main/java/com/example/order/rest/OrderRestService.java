package com.example.order.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.order.ejb.IFeeCalculationService;
import com.example.order.model.EJBIOFact;

@Path("/")
public class OrderRestService {
	
	@Inject
	private IFeeCalculationService.LocalService service;
	
    @POST
    @Path("/fee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public EJBIOFact feeCalculate(EJBIOFact fact) {
    	
    	EJBIOFact result = service.exeService(fact);
    	
    	return result;
    }

}
