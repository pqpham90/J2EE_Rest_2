package assign.resources;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/example")
public class UTCoursesApplication extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();
		
	public UTCoursesApplication() {
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		classes.add(UTCoursesResource.class);
		return classes;
	}
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
