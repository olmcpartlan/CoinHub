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

// These tests are run before the User Entry tests.
// Don't know what I want to use this class for, maybe misc tests.

@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
class HomebaseApplicationTests {
	@Test
	void createUser() {
		System.out.println("*************");
		System.out.println("APPLICATION CONTEXT TESTS");
		System.out.println("*************");
	}

}
