package app.projetCdb.tmpServices;

import org.springframework.stereotype.Service;

@Service("serviceB")
public class ServiceB implements IServiceHello{
	public String Hello() {
		return "hello serviceB";
	}
}
