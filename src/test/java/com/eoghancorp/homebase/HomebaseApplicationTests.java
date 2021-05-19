package com.eoghancorp.homebase;

import com.eoghancorp.homebase.DbConnection.DbConnection;
import com.eoghancorp.homebase.Users.User;
import com.eoghancorp.homebase.Users.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;


@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
class HomebaseApplicationTests {

	private User user;

	public HomebaseApplicationTests()
	{
		this.setUser(new User("omcpartlan", "S0m3S3cur3DP$55", "omcpartlan@gmail.com"));
	}

	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	@Test
	@DisplayName("A. Load Context")
	void contextLoads() {
		System.out.println("Starting Tests");
	}

	@Test
	@DisplayName("B. Create New User")
	void createUser() {
	    // User is created in the constructor, but the password is encrypted after.

        // TODO: This should probably be done automatically.
		User.encryptPassword(this.user.getPass());
		this.user.printUser();

		// Want to make sure that when encryptPassword() is called, only the encrypted password is attached to the record.
		Assertions.assertNotEquals(this.getUser().getPass(), "S0m3S3cur3DP$55");

	}


	@Test
    @DisplayName("C. Insert User into MySQL")
	void insertUser() {
		DbConnection conn = new DbConnection();
		if(conn.createConnection()) {
			conn.createUser(this.getUser());
			conn.closeConnection();
		}

		else System.out.println("***** UNABLE TO CREATE CONNECTION TO MySQL ******");



	}


}
