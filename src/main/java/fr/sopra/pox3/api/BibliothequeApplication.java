package fr.sopra.pox3.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class BibliothequeApplication extends Application{

	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(MaisonDeDisqueResource.class);
		s.add(AuteurResource.class);
		return s;
		
	}
}
