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
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class TestBulkAddUser {
	
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
	public void bulkaddtest() throws Exception
	{
		Group group1 = new Group();
		group1.setID(1);
		group1.setGroupID("group1_id");
		group1.setGroupName("group1");
		group1.setUsers(new ArrayList<User>());

		User u1 = new User();
		u1.setID(1);
		u1.setUserID("user1_id");
		u1.setUserName("user1");
		u1.setGroups(new ArrayList<Group>());
		
		User u2 = new User();
		u2.setID(2);
		u2.setUserID("user2_id");
		u2.setUserName("user2");
		u2.setGroups(new ArrayList<Group>());
		
		User u3 = new User();
		u3.setID(3);
		u3.setUserID("user3_id");
		u3.setUserName("user3");
		u3.setGroups(new ArrayList<Group>());
		
		List<User> uList = new ArrayList<User>();
		List<Group> gList = new ArrayList<Group>();
		
		when(gDB.findOne(1)).thenReturn(group1);
		when(uDB.findOne(1)).thenReturn(u1);
		when(uDB.findOne(2)).thenReturn(u2);
		when(uDB.findOne(3)).thenReturn(u3);
		
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
			Group group = (Group)arr[0];
			gList.add(group);
			return null;
		});
		
		controller.perform(put("/group/1/bulkAdd/1,2,3"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.groupID" , is("group1_id")));
		
		verify(uDB).findOne(1);
		verify(uDB).findOne(2);
		verify(uDB).findOne(3);
		verify(gDB).findOne(1);
		
		assertEquals(uList , group1.getUsers());
		assertEquals(true , u1.getGroups().contains(group1));
		assertEquals(true , u2.getGroups().contains(group1));
		assertEquals(true , u3.getGroups().contains(group1));
	}
}