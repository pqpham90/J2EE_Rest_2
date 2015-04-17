package assign.services;

import assign.domain.Meeting;
import assign.domain.Project;

public interface MyEavesDropService {

	Project addProject(Project c) throws Exception;

	Project getProject(int project_ID) throws Exception;

	Project updateProject(Project p) throws Exception;

	void deleteProject (int project_ID) throws  Exception;

	Meeting addMeeting(Meeting m) throws Exception;

	public String getProjects(int project_ID) throws Exception;

	public String getMeetings(int meeting_ID) throws Exception;
}
