package org.example.ws.service;

import java.util.concurrent.Future;

import org.example.ws.model.Greeting;
import org.example.ws.util.AsyncResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceBean implements EmailService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Boolean send(Greeting greeting) {
		logger.info("> send");
		
		Boolean sucess = Boolean.FALSE;
		
		long pause = 5000;
		try{
			Thread.sleep(pause);
		} catch (Exception e){
			// nothing
		}
		
		logger.info("Processing time was {} seconds.", pause / 1000);
		
		sucess = Boolean.TRUE;
		
		logger.info("< send");
		return sucess;
	}

	@Async
	@Override
	public void sendAsync(Greeting greeting) {
		logger.info("> sendAsync");
		
		try {
			send(greeting);
		} catch (Exception e){
			logger.warn("Exception caugth sending assynchronous mail.",e);
		}
		logger.info("< sendAsync");
	}

	@Async
	@Override
	public Future<Boolean> sendAsyncWithResult(Greeting greeting) {
		logger.info("> sendAsyncWithResult");
		
		AsyncResponse<Boolean> response  = new AsyncResponse<Boolean>();
		
		try {
			Boolean sucess = send(greeting);
			response.complete(sucess);
		} catch (Exception e){
			logger.warn("Exception caugth sending assynchronous mail.",e);
		}
		
		logger.info("< sendAsyncWithResult");
		return response;
	}

}
