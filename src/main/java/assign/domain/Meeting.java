package assign.domain;

/**
 * Created by pqpham90 on 4/16/15.
 */
public class Meeting {
	int meeting_id;
	String name;
	String year;

	public void setMeeting_id(int meeting_id) {
		this.meeting_id = meeting_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setYear(String year) {
		this.year = year;
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
}
