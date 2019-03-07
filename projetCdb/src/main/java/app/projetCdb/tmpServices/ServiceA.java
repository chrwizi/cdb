package app.projetCdb.tmpServices;

import org.springframework.stereotype.Service;

@Service("serviceA")
public class ServiceA implements IServiceHello {
	@Override
	public String Hello() {
		return "hello serviceA";
	}
}
