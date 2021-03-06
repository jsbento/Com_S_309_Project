package myProject;

import myProject.Users.*;
import myProject.Tasks.*;
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
@WebMvcTest(UserController.class)
public class TestJoinGroup 
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
	public void testUserJoinGroup() throws Exception
	{
		User u1 = new User();
		u1.setID(1);
		u1.setGroups(new ArrayList<Group>());
		u1.setUserID("user1_id");
		u1.setUserName("user1");
		
		Group g1 = new Group();
		g1.setGroupID("group1_id");
		g1.setID(1);
		g1.setUsers(new ArrayList<User>());
		g1.setGroupName("group1");
		
		List<Group> gList = new ArrayList<Group>();
		List<User> uList = new ArrayList<User>();
		
		when(uDB.findOne(1)).thenReturn(u1);
		when(gDB.findOne(1)).thenReturn(g1);
		
		when(uDB.save((User)any(User.class))).thenAnswer(x ->
		{
			Object[] arr = x.getArguments();
			User user = (User)arr[0];
			uList.add(user);
			return null;
		});
		
		when(gDB.save((Group)any(Group.class))).thenAnswer(y ->
		{
			Object[] arr = y.getArguments();
			Group g = (Group)arr[0];
			gList.add(g);
			return null;
		});
		
		controller.perform(put("/user/1/group/1/join"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.userID" ,is("user1_id")));
		
		verify(uDB).findOne(1);
		verify(gDB).findOne(1);
		
		assertEquals(true , g1.getUsers().contains(u1));
		assertEquals(true , u1.getGroups().contains(g1));
	}
}