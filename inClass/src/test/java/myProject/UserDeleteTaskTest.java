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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
public class UserDeleteTaskTest
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
	public void testUserTaskDelete() throws Exception
	{
		User testU = new User();
		testU.setUserName("Comp Sci");
		testU.setUserID("309");
		testU.setTasks(new ArrayList<Task>());
		
		Task testT = new Task();
		testT.setTaskTitle("Dishes");
		testT.setTaskID("2");
		
		testU.getTasks().add(testT);
		
		List<User> uList = new ArrayList<User>();
		
		when(uDB.findOne(1)).thenReturn(testU);
		when(tDB.findOne(1)).thenReturn(testT);
		
		when(uDB.save((User)any(User.class))).thenAnswer(x ->
		{
			Object[] arr = x.getArguments();
			uList.add((User)arr[0]);
			return null;
		});
		
		controller.perform(delete("/user/1/deleteTask/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userID", is("309")));
		
		verify(tDB).delete(1);
		assertEquals(true, testU.getTasks().isEmpty());
	}
}