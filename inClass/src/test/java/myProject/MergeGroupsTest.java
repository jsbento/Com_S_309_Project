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
public class MergeGroupsTest {
	
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
	public void testGroupAuth()
	{
		List<Group> list = new ArrayList<Group>();
		
		Group group1 = new Group();
		group1.setGroupID("group1_ID");
		group1.setGroupName("group1@iastate.edu");
		
		Group group2 = new Group();
		group2.setGroupID("group2_ID");
		group2.setGroupName("group2@iastate.edu");
		
		Group group3 = new Group();
		group3.setGroupID("group3_ID");
		group3.setGroupName("group3@iastate.edu");
		
		list.add(group1);
		list.add(group2);
		
		assertEquals(true, Authenticator.groupAuthenticate(group3, list));
	}
	
	@Test
	public void mergeTest() throws Exception
	{
		Group group1 = new Group();
		group1.setID(1);
		group1.setGroupID("mergerGroupID");
		group1.setGroupName("mergerGroupName");
		group1.setTasks(new ArrayList<GroupTask>());
		group1.setUsers(new ArrayList<User>());
		
		Group group2 = new Group();
		group2.setID(2);
		group2.setGroupID("mergeeGroupID");
		group2.setGroupName("mergeeGroupName");
		group2.setTasks(new ArrayList<GroupTask>());
		group2.setUsers(new ArrayList<User>());

		User u = new User();
		u.setUserID("JeremyBearimy");
		u.setUserName("Jeremy");
		u.setGroups(new ArrayList<Group>());
		
		GroupTask gt = new GroupTask();
		gt.setGroup(group2);
		gt.setGroupTaskID("testTaskID");
		gt.setGroupTaskName("testTaskName");
		gt.setGroup(group2);
		
		group2.getUsers().add(u);
		u.getGroups().add(group2);
		group2.getTasks().add(gt);
		
		List<User> uList = new ArrayList<User>();
		List<Group> gList = new ArrayList<Group>();
		List<GroupTask> gtList = new ArrayList<GroupTask>();

		uList.add(u);
		gList.add(group1);
		gList.add(group2);
		gtList.add(gt);
		
		when(gDB.findOne(1)).thenReturn(group1);
		when(gDB.findOne(2)).thenReturn(group2);
		
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
		
		when(gtDB.save((GroupTask)any(GroupTask.class))).thenAnswer(z ->
		{
			Object[] arr = z.getArguments();
			GroupTask groupTask = (GroupTask)arr[0];
			gtList.add(groupTask);
			return null;
		});
		
		controller.perform(put("/group/1/mergeGroups/2"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.groupID", is("mergerGroupID")));
		
		verify(gDB).findOne(2);
		verify(uDB).save((User)any(User.class));
		verify(gDB).save((Group)any(Group.class));
		verify(gtDB).save((GroupTask)any(GroupTask.class));

		assertEquals(true , group1.getUsers().contains(u));
		assertEquals(true , group1.getTasks().contains(gt));
		assertEquals(false, gDB.exists(group2.getID()));
	}
}