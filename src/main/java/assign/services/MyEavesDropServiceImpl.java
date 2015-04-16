package assign.services;

import assign.domain.Course;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class MyEavesDropServiceImpl implements MyEavesDropService {

	String dbURL = "";
	String dbUsername = "";
	String dbPassword = "";
	DataSource ds;

	// DB connection information would typically be read from a config file.
	public MyEavesDropServiceImpl(String dbUrl, String username, String password) {
		this.dbURL = dbUrl;
		this.dbUsername = username;
		this.dbPassword = password;

		ds = setupDataSource();
	}

	public DataSource setupDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setUsername(this.dbUsername);
		ds.setPassword(this.dbPassword);
		ds.setUrl(this.dbURL);
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		return ds;
	}

	public Course addCourse(Course c) throws Exception {
		Connection conn = ds.getConnection();

		String insert = "INSERT INTO courses(name, course_num) VALUES(?, ?)";
		PreparedStatement stmt = conn.prepareStatement(insert,
				Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, c.getName());
		stmt.setString(2, c.getCourseNum());

		int affectedRows = stmt.executeUpdate();

		if (affectedRows == 0) {
			throw new SQLException("Creating course failed, no rows affected.");
		}

		ResultSet generatedKeys = stmt.getGeneratedKeys();
		if (generatedKeys.next()) {
			c.setCourseId(generatedKeys.getInt(1));
		}
		else {
			throw new SQLException("Creating course failed, no ID obtained.");
		}

		// Close the connection
		conn.close();

		return c;
	}

	public Course getCourse(int courseId) throws Exception {
		String query = "select * from courses where course_id=" + courseId;
		Connection conn = ds.getConnection();
		PreparedStatement s = conn.prepareStatement(query);
		ResultSet r = s.executeQuery();

		if (!r.next()) {
			return null;
		}

		Course c = new Course();
		c.setCourseNum(r.getString("course_num"));
		c.setName(r.getString("name"));
		c.setCourseId(r.getInt("course_id"));
		return c;
	}

}