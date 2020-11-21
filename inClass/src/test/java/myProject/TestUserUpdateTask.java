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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class TestUserUpdateTask
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
	public void testTaskUpdate() throws Exception
	{
		User testU = new User();
		testU.setID(1);
		testU.setUserID("test user");
		testU.setUserName("Test McTesty");
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		Task t1 = new Task();
		t1.setID(1);
		t1.setTaskTitle("Dishes");
		t1.setTaskID("Task1D");
		t1.setUser(testU);
		t1.setDueDate("2020-12-01");
		t1.setAssignDate("2020-09-01");
		
		Task t2 = new Task();
		t2.setTaskTitle("Vaccuum");
		t2.setTaskID("Task1B");
		t2.setDueDate("2020-12-03");
		t2.setAssignDate("2020-09-03");
		
		tasks.add(t1);
		testU.setTasks(tasks);
		
		ArrayList<Task> tList = new ArrayList<Task>();
		ArrayList<User> uList = new ArrayList<User>();
		
		when(uDB.findOne(1)).thenReturn(testU);
		when(tDB.findOne(1)).thenReturn(t1);
		
		when(uDB.save((User)any(User.class))).then(x ->
		{
			Object[] arr = x.getArguments();
			uList.add((User)arr[0]);
			return null;
		});
		
		when(tDB.save((Task)any(Task.class))).then(x ->
		{
			Object[] arr = x.getArguments();
			tList.add((Task)arr[0]);
			return null;
		});
		
		ObjectMapper om = new ObjectMapper();
		
		controller.perform(put("/user/1/putTask/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(om.writeValueAsString(t2)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.taskID", is("Task1B")))
			.andExpect(jsonPath("$.dueDate", is("2020-12-03")));
		
		verify(tDB).save((Task)any(Task.class));
		verify(uDB).save((User)any(User.class));
	}
}