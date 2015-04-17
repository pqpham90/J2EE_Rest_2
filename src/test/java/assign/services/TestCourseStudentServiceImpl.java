package assign.services;

import assign.domain.Project;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCourseStudentServiceImpl {

	MyEavesDropService csService = null;

	@Before
	public void setUp() {
		String dburl = "jdbc:mysql://localhost:3306/myeavesdrop";
		String dbusername = "mwa";
		String dbpassword = "bKbYbK4A6QumJTrr";
		csService = new MyEavesDropServiceImpl(dburl, dbusername, dbpassword);
	}

	@Test
	public void testCourseAddition() {
		try {
			Project c = new Project();
			c.setName("Introduction to Computer Science.");
			c.setDescription("CS101");
			c = csService.addProject(c);

			Project c1 = csService.getProject(c.getProject_id());

			assertEquals(c1.getName(), c.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
