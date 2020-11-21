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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.status.Status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.internal.matchers.Any;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
public class TestGroupDelete {
	
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
	private GroupTaskDB gtDB;
	
	@Autowired
	private MockMvc controller;
	
	@Test
	public void deleteTest() throws Exception
	{
		Group g1 = new Group();
		g1.setID(1);
		g1.setGroupID("group1_id");
		g1.setGroupName("group1");
		g1.setTasks(new ArrayList<GroupTask>());
		g1.setUsers(new ArrayList<User>());
		
		User u1 = new User();
		u1.setGroups(new ArrayList<Group>());
		
		GroupTask gt1 = new GroupTask();
		gt1.setGroup(g1);
		
		u1.getGroups().add(g1);
		g1.getUsers().add(u1);
		g1.getTasks().add(gt1);
		
		//List<User> uList = new ArrayList<User>();
		List<Group> gList = new ArrayList<Group>();
		//List<GroupTask> gtList = new ArrayList<GroupTask>();
		
		when(gDB.findOne(1)).thenReturn(g1);
		
		doNothing().when(gDB).delete(g1);
		doNothing().when(gtDB).delete((GroupTask)any(GroupTask.class));
		
		when(uDB.save((User)any(User.class))).thenReturn(u1);
		when(gtDB.save((GroupTask)any(GroupTask.class))).thenReturn(gt1);
		
		when(gDB.save((Group)any(Group.class))).thenAnswer(x ->
		{
			Object[] arr = x.getArguments();
			Group group = (Group)arr[0];
			gList.add(group);
			return null;
		});
		/*
		when(gtDB.save((GroupTask)any(GroupTask.class))).thenAnswer(y ->
		{
			Object[] arr = y.getArguments();
			GroupTask groupTask = (GroupTask)arr[0];
			gtList.add(groupTask);
			return null;
		});
		
		when(uDB.save((User)any(User.class))).thenAnswer(z ->
		{
			Object[] arr = z.getArguments();
			User user = (User)arr[0];
			uList.add(user);
			return null;
		});
	*/
		
	    controller.perform(delete("/group/{id}", g1.getID()))
	            .andExpect(status().isOk());
	    
		verify(gDB).findOne(1);
		verify(gDB).delete((Group)any(Group.class));
		verify(gtDB).delete((GroupTask)any(GroupTask.class));
		
		assertEquals(null , gDB.findOne(g1.getID()));
		
	}
}