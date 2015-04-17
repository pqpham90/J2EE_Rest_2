package assign.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pqpham90 on 4/16/15.
 */
@XmlRootElement(name = "meeting")
@XmlAccessorType(XmlAccessType.FIELD)
public class Meeting {
	int meeting_id;
	String name;
	String year;
	int fk_meeting_project;

	public void setMeeting_id(int meeting_id) {
		this.meeting_id = meeting_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setFk_meeting_project(int fk_meeting_project) {
		this.fk_meeting_project = fk_meeting_project;
	}

	public int getMeeting_id() {
		return meeting_id;
	}

	public String getName() {
		return name;
	}

	public String getYear() {
		return year;
	}

	public int getFk_meeting_project() {
		return fk_meeting_project;
	}
}
