package assign.services;

import assign.domain.Project;

public interface MyEavesDropService {

	public Project addCourse(Project c) throws Exception;

	public Project getCourse(int courseId) throws Exception;

}
