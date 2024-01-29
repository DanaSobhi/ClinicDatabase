package application;
import java.util.ArrayList;


public class UsersClass implements Comparable<UsersClass> {

// A UsersClass has a ID, password
protected String id;
protected String password;
protected ArrayList<UsersClass> user;
private static UsersClass users;

		
		public UsersClass() {
			user = new ArrayList<>();

		}

		public UsersClass(String id, String password) {
			
			this.id = id;
			this.password = password;

		}

		public String getId() {
			return id;
		}

		public String getPassword() {
			return password;
		}


		@Override
		public int compareTo(UsersClass o) {// this method for comparing the instance of
			UsersClass temp = (UsersClass) o;

			return this.getId().compareTo(temp.getId());
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) { // if this object is equally to this object "USER"
				return true; // return true
			}
			if (obj == null || getClass() != obj.getClass()) { // if this object is null or not from this class
				return false; // return false
			}
			UsersClass UsersClass = (UsersClass) obj; // make this object a user object
			
			
			return id.equals(UsersClass.getId());
		}
		

		
		public void addUser(String id, String password) {

			
			if (user.size() == 0) {
				user.add(new UsersClass(id, password));
			}
			for (int i = 0; i < user.size(); i++) {
				UsersClass user1 = new UsersClass(id, password);

				if (!user.contains(user1)) { // here we will use equal , since contain have equal (o==null ?

					user.add(user1); // if the customer isn't in the list then add it
					System.out.println("User Has been added");
				}}}
		
		
		public ArrayList<UsersClass> getDataList() {
	        return user;
	    }
		
	    public static UsersClass getUsers() {
	        if (users == null) {
	        	users = new UsersClass();
	        }
	        return users;
	    }

	}


