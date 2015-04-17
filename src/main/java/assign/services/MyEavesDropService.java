package assign.services;

import assign.domain.Meeting;
import assign.domain.Project;

public interface MyEavesDropService {

	Project addProject(Project c) throws Exception;

	Project getProject(int courseId) throws Exception;

	Project updateProject(Project p) throws Exception;

	Meeting addMeeting(Meeting m) throws Exception;

	public String getProjects(int project_ID) throws Exception;

	public String getMeetings(int project_ID) throws Exception;
}
