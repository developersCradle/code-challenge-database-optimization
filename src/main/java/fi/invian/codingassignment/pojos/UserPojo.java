package fi.invian.codingassignment.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPojo {

	private int userId;
	private String name;
	private int count;

	public UserPojo() {
	}

	public UserPojo(String name) {
		this.name = name;
	}

	public UserPojo(int userId, int count) {
		this.userId = userId;
		this.count = count;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "UserPojo [userId=" + userId + ", name=" + name + "]";
	}

}