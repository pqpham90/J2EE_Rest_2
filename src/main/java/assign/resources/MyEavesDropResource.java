package assign.resources;

import assign.domain.Project;
import assign.services.MyEavesDropService;
import assign.services.MyEavesDropServiceImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/myeavesdrop/projects")
public class MyEavesDropResource {
	private AtomicInteger idCounter = new AtomicInteger();

	MyEavesDropService myEavesDropService;
	String password;
	String username;
	String dburl;

	public MyEavesDropResource(@Context ServletContext servletContext) {
		dburl = servletContext.getInitParameter("DBURL");
		username = servletContext.getInitParameter("DBUSERNAME");
		password = servletContext.getInitParameter("DBPASSWORD");
		this.myEavesDropService = new MyEavesDropServiceImpl(dburl, username, password);
	}

	@POST
	@Consumes("application/xml")
	public Response createProject(InputStream is) {
		Project project = readProject(is);
		try {
			myEavesDropService.addProject(project);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		String result = "Project created";
		return Response.status(201).entity(result).build();
//		return Response.created(URI.create("/customers/" + project.getProject_id())).build();
	}

	@GET
	@Path("{id}")
	@Produces("text/html")
	public Response helloWorld() {
		String result = "Get Method";
		return Response.status(405).entity(result).build();
	}

	protected Project readProject(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			Project proj = new Project();
			if (root.getAttribute("project_id") != null && !root.getAttribute("project_id").trim().equals(""))
				proj.setProject_id(Integer.valueOf(root.getAttribute("project_id")));
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (element.getTagName().equals("name")) {
					proj.setName(element.getTextContent());
				}
				else if (element.getTagName().equals("description")) {
					proj.setDescription(element.getTextContent());
				}

			}
			return proj;
		}
		catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}
}
