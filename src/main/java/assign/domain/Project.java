package assign.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {
	int project_id;
	String name;
	String description;

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getProject_id() {
		return project_id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
