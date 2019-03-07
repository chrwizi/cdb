package app.projetCdb.tmpServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("asService")
public class AsService {
	@Autowired
	@Qualifier("serviceB")
	IServiceHello service;
	
	public String useServiceHello() {
		return ("My service call "+service.Hello());
	}
}
