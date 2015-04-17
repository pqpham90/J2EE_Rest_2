package assign.services;

import assign.domain.Project;

public interface MyEavesDropService {

	public Project addProject(Project c) throws Exception;

	public Project getProject(int courseId) throws Exception;

}
