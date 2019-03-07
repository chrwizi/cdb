package app.projetCdb.tmpServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("asService")
public class AsService implements IAsService {
	@Autowired
	@Qualifier("serviceA") IServiceHello service;
	
	/* (non-Javadoc)
	 * @see app.projetCdb.tmpServices.IAsService#useServiceHello()
	 */
	@Override
	public String useServiceHello() {
		return ("My service call "+service.Hello());
	}
}
