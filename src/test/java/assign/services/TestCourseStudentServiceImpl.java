package assign.services;

import org.junit.Before;

public class TestCourseStudentServiceImpl {

	MyEavesDropService csService = null;

	@Before
	public void setUp() {
		String dburl = "jdbc:mysql://localhost:3306/student_courses";
		String dbusername = "devdatta";
		String dbpassword = "";
		csService = new MyEavesDropServiceImpl(dburl, dbusername, dbpassword);
	}

//	@Test
//	public void testCourseAddition() {
//		try {
//			Project c = new Project();
//			c.setName("Introduction to Computer Science.");
//			c.setCourseNum("CS101");
//			c = csService.addCourse(c);
//
//			Project c1 = csService.getCourse(c.getCourseId());
//
//			assertEquals(c1.getName(), c.getName());
//			assertEquals(c1.getCourseNum(), c.getCourseNum());
//			assertEquals(c1.getCourseId(), c.getCourseId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
