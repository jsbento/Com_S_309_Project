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
public class getGroupByNameTest 
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
	private GroupDB gDB;
	
	@Autowired
	private MockMvc controller;
	
	@Test
	public void getGroupByNameTester() throws Exception
	{
		Group testGroup = new Group();
		testGroup.setGroupName("testGroup");
		testGroup.setGroupID("F2020TestGroup");
		testGroup.setTasks(null);
		testGroup.setUsers(null);
		
		List<Group> gList = new ArrayList<Group>();
		gList.add(testGroup);
		
		when(gDB.findAll()).thenReturn(gList);
		
		controller.perform(get("/group/getGroupByName?requested=testGroup"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.groupID", is("F2020TestGroup")))
				.andExpect(jsonPath("$.name", is("testGroup")));
		
		verify(gDB).findAll();
		
		assertEquals("testGroup", gList.get(0).getName());
	}
}