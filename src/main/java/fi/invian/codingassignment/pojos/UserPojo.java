package fi.invian.codingassignment.pojos;

public class UserPojo {
    private int userPojoId;
    private String name;

    public UserPojo() {
    }

    public UserPojo(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userPojoId;
    }

    public void setUserId(int userId) {
        this.userPojoId = userId;
    }

    public String getName() {
        return name;
    }
 
	public void setName(String name) {
        this.name = name;
    }
	
	   @Override
		public String toString() {
			return "UserPojo [userPojoId=" + userPojoId + ", name=" + name + "]";
		}

}