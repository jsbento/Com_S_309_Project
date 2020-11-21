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
import static org.mockito.Mockito.any;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.internal.matchers.Any;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserContollerTaskTest
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
		public TaskDB getTaskRepo()
		{
			return mock(TaskDB.class);
		}
		
		@Bean
		public GroupDB getGroupRepo()
		{
			return mock(GroupDB.class);
		}
	}
	
	@Autowired
	private UserDB uDB;
	
	@Autowired
	private TaskDB tDB;
	
	@Autowired
	private MockMvc controller;
	
	@Test
	public void testAddTaskCalls() throws Exception
	{
		User testU = new User();
		testU.setID(1);
		testU.setUserID("309");
		testU.setUserName("Bob Doe");
		testU.setTasks(new ArrayList<Task>());
		
		Task testT = new Task();
		testT.setTaskTitle("Dishes");
		testT.setTaskID("2");
		
		testU.getTasks().add(testT);
		
		List<User> uList = new ArrayList<User>();
		List<Task> tList = new ArrayList<Task>();
		
		when(uDB.findOne(1)).thenReturn(testU);
		
		when(uDB.save((User)any(User.class))).thenAnswer(x ->
		{
			Object[] arr = x.getArguments();
			User u = (User)arr[0];
			uList.add(u);
			return null;
		});
		
		when(tDB.save((Task)any(Task.class))).thenAnswer(y ->
		{
			Object[] arr = y.getArguments();
			Task t = (Task)arr[0];
			tList.add(t);
			return t;
		});
		
		ObjectMapper om = new ObjectMapper();
		
		controller.perform(post("/user/1/addTask")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(testT)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.taskID", is("2")));
		
		verify(uDB).findOne(1);
		verify(uDB).save((User)any(User.class));
		verify(tDB).save((Task)any(Task.class));
	}
}