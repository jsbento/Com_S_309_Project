package myProject;

import myProject.Users.*;
import myProject.Authentication.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class TestUserAuthentication
{
	@Test
	public void testUserAuthSameUser()
	{	
		List<User> l = new ArrayList<User>();
		
		User u1 = new User();
		u1.setUserID("001ABF");
		u1.setUserEmail("user@iastate.edu");
		u1.setUserPhoneNum("1234567890");
		
		User u2 = new User();
		u2.setUserID("001ABF");
		u2.setUserEmail("user@iastate.edu");
		u2.setUserPhoneNum("1234567890");
		
		User u3 = new User();
		u3.setUserID("11BCD");
		u3.setUserEmail("u3@iastate.edu");
		u3.setUserPhoneNum("1112223333");
		
		l.add(u1);
		l.add(u3);
		
		assertEquals(false, Authenticator.userAuthenticate(u2, l));
	}
	
	@Test
	public void testUserAuthDiffUser()
	{
		List<User> l = new ArrayList<User>();
		
		User u1 = new User();
		u1.setUserID("001ABF");
		u1.setUserEmail("user@iastate.edu");
		u1.setUserPhoneNum("1234567890");
		
		User u2 = new User();
		u2.setUserID("001ABD");
		u2.setUserEmail("user2@iastate.edu");
		u2.setUserPhoneNum("2223334444");
		
		User u3 = new User();
		u3.setUserID("11BCD");
		u3.setUserEmail("user3@iastate.edu");
		u3.setUserPhoneNum("1112223333");
		
		l.add(u1);
		l.add(u2);
		
		assertEquals(true, Authenticator.userAuthenticate(u3, l));
	}
}