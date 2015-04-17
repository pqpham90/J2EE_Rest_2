package assign.resources;

import assign.domain.Meeting;
import assign.domain.Project;
import assign.services.MyEavesDropService;
import assign.services.MyEavesDropServiceImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

@Path("/myeavesdrop/projects")
public class MyEavesDropResource {
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

	@GET
	@Path("/helloworld")
	@Produces("text/html")
	public Response helloWorld() {
		String result = "Hello world: " + dburl + " " + username + " " + password;
		return Response.status(405).entity(result).build();
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
	}

	@PUT
	@Path("{id}")
	@Consumes("application/xml")
	public void updateCustomer(@PathParam("id") int id, InputStream is) {
		Project update = readProject(is);
		Project current = null;

		try {
			current = myEavesDropService.getProject(id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (current == null) throw new WebApplicationException(Response.Status.NOT_FOUND);

		current.setName(update.getName());
		current.setDescription(update.getDescription());

		try {
			myEavesDropService.updateProject(current);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@POST
	@Path("{id}/meetings")
	@Consumes("application/xml")
	public Response createMeeting(@PathParam("id") int id, InputStream is) {
		Project current = null;

		try {
			current = myEavesDropService.getProject(id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (current == null) throw new WebApplicationException(Response.Status.NOT_FOUND);

		Meeting meeting = readMeeting(is);
		meeting.setFk_meeting_project(id);
		try {
			myEavesDropService.addMeeting(meeting);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		String result = "Meeting created";
		return Response.status(201).entity(result).build();
	}

	@GET
	@Path("{id}")
	@Produces("application/xml")
	public StreamingOutput displayProject(@PathParam("id") int id, @Context final HttpServletResponse response) {
		Project current = null;

		try {
			current = myEavesDropService.getProject(id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (current == null) throw new WebApplicationException(Response.Status.NOT_FOUND);

		String output = "<project id=" + id + ">";

		try {
			output += myEavesDropService.getProjects(id);
			output += myEavesDropService.getMeetings(id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		output += "</project>";

		response.setStatus(200);
		try {
			response.flushBuffer();
		}
		catch(Exception e){
			e.printStackTrace();
		}


		final String result = output;

		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException
		{
			PrintStream writer = new PrintStream(outputStream);
				writer.println(result);
			}
		};

//		String result = "Displaying Project";
//		return Response.status(200).entity(result).build();
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

	protected Meeting readMeeting(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			Meeting meet = new Meeting();
			if (root.getAttribute("meeting_id") != null && !root.getAttribute("meeting_id").trim().equals(""))
				meet.setMeeting_id(Integer.valueOf(root.getAttribute("meeting_id")));
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (element.getTagName().equals("name")) {
					meet.setName(element.getTextContent());
				}
				else if (element.getTagName().equals("year")) {
					meet.setYear(element.getTextContent());
				}

			}
			return meet;
		}
		catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}
}
