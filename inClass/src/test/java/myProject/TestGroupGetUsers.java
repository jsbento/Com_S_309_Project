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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.internal.matchers.Any;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
public class TestGroupGetUsers
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
	}
	
	@Autowired
	private UserDB uDB;
	
	@Autowired
	private GroupDB gDB;
	
	@Autowired
	private MockMvc controller;
	
	@Test
	public void testGroupGetUsers() throws Exception
	{
		User u1 = new User();
		u1.setUserName("Bob Bobby");
		u1.setGroups(new ArrayList<Group>());
		
		User u2 = new User();
		u2.setUserName("Tom Tommy");
		u2.setUserID("Tom Tommy");
		u2.setGroups(new ArrayList<Group>());
		
		User u3 = new User();
		u3.setUserName("Bot Kekli");
		u3.setUserID("Bot Kekli");
		u3.setGroups(new ArrayList<Group>());
		
		Group g1 = new Group();
		g1.setGroupName("COM S 309");
		g1.setGroupID("coms309");
		g1.setUsers(new ArrayList<User>());
		
		Group g2 = new Group();
		g2.setGroupName("QuackMasters");
		g2.setGroupID("quackMas");
		g2.setUsers(new ArrayList<User>());
		
		g1.getUsers().add(u1);
		u1.getGroups().add(g1);
		
		g2.getUsers().add(u1);
		u1.getGroups().add(g2);
		
		g1.getUsers().add(u2);
		u2.getGroups().add(g1);
		
		g2.getUsers().add(u2);
		u2.getGroups().add(g2);
		
		g2.getUsers().add(u3);
		u3.getGroups().add(g2);
		
		List<Group> gList = new ArrayList<Group>();
		gList.add(g1);
		gList.add(g2);
		
		when(gDB.findAll()).thenReturn(gList);
		
		controller.perform(get("/group/getGroupUsers?gName=QuackMasters"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[2].name", is("Bot Kekli")));
		
		controller.perform(get("/group/getGroupUsers?gName=COM S 309"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[1].name", is("Tom Tommy")));
	}
}