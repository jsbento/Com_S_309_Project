package myProject;

import myProject.Users.*;
import myProject.Tasks.*;
import myProject.Authentication.Authenticator;
import myProject.Groups.*;

//Import Java libraries
import java.util.List;
import java.util.ArrayList;

//import junit/spring tests
import org.junit.Test;
import org.junit.runner.RunWith;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.MediaType;

//import mockito related
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.any;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.internal.matchers.Any;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
public class removeUserTest 
{
	@TestConfiguration
	static class StringContextConfiguration
	{
		@Bean
		public UserDB getUserRepo()
		{
			return mock(UserDB.class);
		}
		
		@Bean
		public GroupTaskDB getGroupTaskRepo()
		{
			return mock(GroupTaskDB.class);
		}
		
		@Bean
		public GroupDB getGroupRepo()
		{
			return mock(GroupDB.class);
		}
		
		@Bean
		public TaskDB getTaskRepo()
		{
			return mock(TaskDB.class);
		}
	}
	
	@Autowired
	private UserDB uDB;
	
	@Autowired
	private GroupDB gDB;
	
	@Autowired
	private MockMvc controller;
	
	@Test
	public void testRemoveUser() throws Exception
	{
		User testUser = new User();
		testUser.setUserName("test user");
		testUser.setUserID("test userID");
		testUser.setGroups(new ArrayList<Group>());
		
		Group testGroup = new Group();
		testGroup.setGroupName("test Group");
		testGroup.setGroupID("test groupID");
		testGroup.setTasks(null);
		testGroup.setUsers(new ArrayList<User>());
		
		testUser.getGroups().add(testGroup);
		testGroup.getUsers().add(testUser);
		
		List<User> uList = new ArrayList<User>();
		List<Group> gList = new ArrayList<Group>();
		
		when(uDB.findOne(1)).thenReturn(testUser);
		when(gDB.findOne(1)).thenReturn(testGroup);
		
		when(uDB.save((User)any(User.class))).thenAnswer(x ->
		{
			Object[] arr = x.getArguments();
			User u = (User)arr[0];
			uList.add(u);
			return null;
		});
		
		when(gDB.save((Group)any(Group.class))).thenAnswer(y ->
		{
			Object[] arr = y.getArguments();
			Group g = (Group)arr[0];
			gList.add(g);
			return null;
		});
		
		controller.perform(put("/group/1/userID/1/removeUser"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.groupID", is("test groupID")));
		
		verify(uDB).findOne(1);
		verify(gDB).findOne(1);
		verify(uDB).save((User)any(User.class));
		verify(gDB).save((Group)any(Group.class));
		
		//assertEquals(0, testGroup.getUsers().size());
	}
}