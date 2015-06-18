package com.example.order.ejb;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.example.order.model.EJBIOFact;

public interface IFeeCalculationService {
	
	EJBIOFact exeService(EJBIOFact fact);
	
	@Remote
	public interface RemoteService extends IFeeCalculationService {
		
	}
	
	@Local
	public interface LocalService extends IFeeCalculationService {
		
	}

}
