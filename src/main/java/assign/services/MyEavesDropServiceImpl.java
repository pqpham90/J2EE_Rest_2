package assign.services;

import assign.domain.Meeting;
import assign.domain.Project;
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

	public Project addProject(Project p) throws Exception {
		Connection conn = ds.getConnection();

		String insert = "INSERT INTO projects(name, description) VALUES(?, ?)";
		PreparedStatement stmt = conn.prepareStatement(insert,
                Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, p.getName());
		stmt.setString(2, p.getDescription());

		int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating project failed, no rows affected.");
        }

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
        	p.setProject_id(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Creating project failed, no ID obtained.");
        }

        // Close the connection
        conn.close();

		return p;
	}

	public Project updateProject(Project p) throws Exception {
		Connection conn = ds.getConnection();

		String update = "UPDATE projects SET name = ?, description = ? WHERE project_id = ?";
		PreparedStatement stmt = conn.prepareStatement(update,
				Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, p.getName());
		stmt.setString(2, p.getDescription());
		stmt.setInt(3, p.getProject_id());

		int affectedRows = stmt.executeUpdate();

		if (affectedRows == 0) {
			throw new SQLException("Creating course failed, no rows affected.");
		}

		// Close the connection
		conn.close();

		return p;
	}

	public Project getProject(int project_ID) throws Exception {
		String query = "select * from projects where project_id=" + project_ID;
		Connection conn = ds.getConnection();
		PreparedStatement s = conn.prepareStatement(query);
		ResultSet r = s.executeQuery();

		if (!r.next()) {
			return null;
		}

		Project p = new Project();
		p.setProject_id(r.getInt("project_id"));
		p.setName(r.getString("name"));
		p.setDescription(r.getString("description"));
		return p;
	}

	public Meeting addMeeting(Meeting m) throws Exception {
		Connection conn = ds.getConnection();

		String insert = "INSERT INTO meetings(name, year, fk_meeting_project) VALUES(?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(insert,
				Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, m.getName());
		stmt.setString(2, m.getYear());
		stmt.setInt(3, m.getFk_meeting_project());

		int affectedRows = stmt.executeUpdate();

		if (affectedRows == 0) {
			throw new SQLException("Creating meeting failed, no rows affected.");
		}

		ResultSet generatedKeys = stmt.getGeneratedKeys();
		if (generatedKeys.next()) {
			m.setMeeting_id(generatedKeys.getInt(1));
		}
		else {
			throw new SQLException("Creating meeting failed, no ID obtained.");
		}

		// Close the connection
		conn.close();

		return m;
	}

	public String getProjects(int project_ID) throws Exception {
		String query = "select * from projects where project_id=" + project_ID;
		Connection conn = ds.getConnection();
		PreparedStatement s = conn.prepareStatement(query);
		ResultSet r = s.executeQuery();

		String result = "";

		while(r.next()) {
			result += "<name>" + r.getString("name") + "</name>";
			result += "<description>" + r.getString("description") + "</description>";
		}

		return result;
	}

	public String getMeetings(int project_ID) throws Exception {
		String query = "select * from meetings where fk_meeting_project=" + project_ID;
		Connection conn = ds.getConnection();
		PreparedStatement s = conn.prepareStatement(query);
		ResultSet r = s.executeQuery();

		String result = "<meetings>";

		while(r.next()) {
			result += "<meeting>";
			result += "<name>" + r.getString("name") + "</name>";
			result += "<year>" + r.getString("year") + "</year>";
			result += "</meeting>";
		}

		result += "</meetings>";

		return result;
	}
}
