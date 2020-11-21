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
@WebMvcTest(UserController.class)
public class updateTaskTest {
	
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
	private TaskDB tDB;
	
	@Autowired
	private MockMvc controller;
	
	@Test
	public void updateTaskTester() throws Exception
	{
		User u1 = new User();
		u1.setID(1);
		u1.setUserID("user1_ID");
		u1.setUserName("user1");
		u1.setTasks(new ArrayList<Task>());
		
		User u2 = new User();
		u2.setID(2);
		u2.setUserID("user2_ID");
		u2.setUserName("user2");
		u2.setTasks(new ArrayList<Task>());
		
		Task t1 = new Task();
		t1.setID(1);
		t1.setTag("");
		t1.setTaskID("t1_ID");
		t1.setUser(u1);
		t1.setTaskTitle("t1_title");
		
		Task t2 = new Task();
		t2.setID(2);
		t2.setTag("modified");
		t2.setTaskID("t2_ID");
		t2.setUser(u2);
		t2.setTaskTitle("t2_title");
		
		u1.getTasks().add(t1);
		u2.getTasks().add(t2);
		
		List<User> uList = new ArrayList<User>();
		List<Task> tList = new ArrayList<Task>();
		
		uList.add(u1);
		uList.add(u2);
		tList.add(t1);
		tList.add(t2);
		
		when(uDB.findOne(1)).thenReturn(u1);
		when(tDB.findOne(1)).thenReturn(t1);
		
		when(uDB.save((User)any(User.class))).thenAnswer(x ->
		{
			Object[] arr = x.getArguments();
			User user = (User)arr[0];
			uList.add(user);
			return null;
		});
		
		when(tDB.save((Task)any(Task.class))).thenAnswer(z ->
		{
			Object[] arr = z.getArguments();
			Task Task = (Task)arr[0];
			tList.add(Task);
			return null;
		});
		
		ObjectMapper om = new ObjectMapper();
		
		assertEquals(u2 , t2.getUser());
		assertEquals(u1 , t1.getUser());
		assertEquals("", t1.getTag());
		assertEquals("modified", t2.getTag());
		assertEquals("t1_ID", t1.getTaskID());
		assertEquals("t2_ID", t2.getTaskID());
		
		controller.perform(put("/user/1/putTask/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(t2)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.taskID", is("t2_ID")));
		
		verify(uDB).findOne(1);
		verify(tDB).findOne(1);
		verify(uDB).save((User)any(User.class));
		verify(tDB).save((Task)any(Task.class));
		
		assertEquals(t2.getTaskID() , t1.getTaskID());
		assertEquals(t2.getTitle() , t1.getTitle());
		assertEquals("modified" , t1.getTag());
		assertEquals("user2" , t2.getUser().getName());
		assertEquals("user1" , t1.getUser().getName());
		assertEquals(u2.getId() , t2.getUser().getId());
		assertEquals(u1.getId() , t1.getUser().getId());
		assertEquals(true , t1.getUser().equals(u1));
	}
}