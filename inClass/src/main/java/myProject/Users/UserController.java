package myProject.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import myProject.Authentication.Authenticator;
import myProject.Groups.*;
import myProject.Tasks.TaskDB;
import myProject.Tasks.Task;

/**
 * Controller for users in the DB
 * @author Jacob Benton (jsbenton), Noah Cantrell (nbc)
 *
 */
@RestController
public class UserController
{
	/**
	 * User repository
	 */
	@Autowired
	UserDB userDB;
	
	/**
	 * Task repository
	 */
	@Autowired
	TaskDB taskDB;
	
	/**
	 * Group repository
	 */
	@Autowired
	GroupDB groupDB;
	
	/**
	 * Gets a user in the userDB by id
	 * @param id - id of the User
	 * @return User with the corresponding id
	 */
	@GetMapping("/user/{id}")
	User getUser(@PathVariable Integer id) {
		return userDB.findOne(id);
	}
	
	/**
	 * Gets a user by the given ID
	 * @param handle - given userID
	 * @return User with a userID matching the handle, null otherwise
	 */
	@GetMapping("/user/getbyID")
	User getUserByID(@RequestParam String handle) 
	{
		for(User u : userDB.findAll()) 
		{
			if(u.userID.equalsIgnoreCase(handle))
				return u;
		}
		
		return null;
	}
	
	/**
	 * Gets a user by their name
	 * @param name - name of the user
	 * @return User with the given name, null otherwise
	 */
	@GetMapping("/user/getbyName")
	User getUserByName(@RequestParam String name) 
	{
		for(User u : userDB.findAll()) 
		{
			if(u.getName().equalsIgnoreCase(name))
				return u;
		}
		
		return null;
	}
	
	/**
	 * Gets user by their email
	 * @param email - email of the user
	 * @return User with the given email, null otherwise
	 */
	@GetMapping("/user/getbyEmail")
	User getUserByEmail(@RequestParam String email)
	{
		for(User u : userDB.findAll())
		{
			if(u.email.equalsIgnoreCase(email))
				return u;
		}
		
		return null;
	}
	
	@GetMapping("/user/getbyPhone")
	User getUserByPhone(@RequestParam String userPhoneNum) 
	{
		for(User u : userDB.findAll()) 
		{
			if(u.phoneNum.equalsIgnoreCase(userPhoneNum))
				return u;
		}
		
		return null;
	}
	
	/**
	 * Returns a list of all groups the given user is a part of
	 * @param name - name of given user
	 * @return List of groups the given user is a part of
	 */
	@GetMapping("/user/getGroups")
	List<Group> getUserGroups(@RequestParam String handle)
	{	
		for(User u : userDB.findAll()) 
		{
			if(u.userID.equalsIgnoreCase(handle))
				return u.getGroups();
		}
		
		return null;
	}
	
	/**
	 * Returns all users stored in the repository
	 * @return list of User stored in the repository
	 */
	@RequestMapping("/users")
	List<User> getUsers()
	{
		return userDB.findAll();
	}
	
	
	/**
	 * Save a new user to the repository
	 * @param u - the user to be added
	 * @return the added User if it is authenticated, null if it already exists in the repository
	 */
	@PostMapping("/user")
	User createUser(@RequestBody User u)
	{
		if(Authenticator.userAuthenticate(u, userDB.findAll()))
		{
			User result = userDB.save(u);
			return result;
		}
		else
			return null;
	}
	
	/**
	 * Updates the user's name
	 * @param u - User with new information
	 * @param id - repository ID of the user to update
	 * @return updated User
	 */
	@PutMapping("/user/updateUserName/{id}")
	User updateUserName(@RequestBody User u, @PathVariable Integer id)
	{
		User old_U = userDB.findOne(id);
		old_U.setUserName(u.name);
		userDB.save(old_U);
		return old_U;
	}
	
	/**
	 * Updates the user's given ID
	 * @param u - User with new information
	 * @param id - repository ID of the user to update
	 * @return updated User
	 */
	@PutMapping("/user/updateUserID/{id}")
	User updateUserID(@RequestBody User u, @PathVariable Integer id)
	{
		User old_U = userDB.findOne(id);
		old_U.setUserID(u.userID);
		userDB.save(old_U);
		return old_U;
	}
	
	/**
	 * Updates the user's password
	 * @param u - User with new information
	 * @param id - repository ID of the user to update
	 * @return updated User
	 */
	@PutMapping("/user/updateUserPassword/{id}")
	User updateUserPassword(@RequestBody User u, @PathVariable Integer id)
	{
		User old_U = userDB.findOne(id);
		old_U.setUserPassword(u.password);
		userDB.save(old_U);
		return old_U;
	}
	
	/**
	 * Updates the user's email
	 * @param u - User with new information
	 * @param id - repository ID of the user to update
	 * @return updated User
	 */
	@PutMapping("/user/updateUserEmail/{id}")
	User updateUserEmail(@RequestBody User u, @PathVariable Integer id)
	{
		User old_U = userDB.findOne(id);
		old_U.setUserEmail(u.email);
		userDB.save(old_U);
		return old_U;
	}
	
	/**
	 * Updates the user's phone number
	 * @param u - User with new information
	 * @param id - repository ID of the user to update
	 * @return updated User
	 */
	@PutMapping("/user/updateUserPhone/{id}")
	User updateUserPhone(@RequestBody User u, @PathVariable Integer id)
	{
		User old_U = userDB.findOne(id);
		old_U.setUserPhoneNum(u.phoneNum);
		userDB.save(old_U);
		return old_U;
	}
	
	/**
	 * Updates the user's email verification
	 * @param u - User with new information
	 * @param id - repository ID of the user to update
	 * @return updated User
	 */
	@PutMapping("/user/verifyEmail/{id}")
	User updateEmailVerified(@RequestBody User u, @PathVariable Integer id)
	{
		User old_U = userDB.findOne(id);
		old_U.setEmailVerified(u.eMailVerif);
		userDB.save(old_U);
		return old_U;
	}
	
	/**
	 * Updates the user's phone verification
	 * @param u - User with new information
	 * @param id - repository ID of the user to update
	 * @return updated User
	 */
	@PutMapping("/user/verifyPhone/{id}")
	User updatePhoneVerified(@RequestBody User u, @PathVariable Integer id)
	{
		User old_U = userDB.findOne(id);
		old_U.setPhoneVerified(u.phoneVerif);
		userDB.save(old_U);
		return old_U;
	}
	
	@GetMapping("/user/{id}/tasks")
	List<Task> getTasks(@PathVariable Integer id)
	{
		return userDB.findOne(id).getTasks();
	}
	
	/**
	 * Assigns a task to user in the repository
	 * Adds the task to the task repository
	 * @param uID - repository ID of the user
	 * @param t - Task to add
	 * @return updated User
	 */
	@PostMapping("/user/{uID}/addTask")
	Task addTask(@PathVariable Integer uID, @RequestBody Task t)
	{
		User u = userDB.findOne(uID);
		
		if(u.getTasks().contains(t))
			return null;
		
		u.getTasks().add(t);
		t.setUser(u);
		
		Task result = taskDB.save(t);
		userDB.save(u);
		
		return result;
	}

	/**
	 * PRECONDITION: The group must already exist in the group repository
	 * Allows a user to join a group, updates both the group and users lists of each other
	 * @param uID - repository ID of the user
	 * @param gID - repository ID of the group
	 * @return updated User
	 */
	@PutMapping("/user/{uID}/group/{gID}/join")
	User joinGroup(@PathVariable Integer uID, @PathVariable Integer gID)
	{
		User u = userDB.findOne(uID);
		Group g = groupDB.findOne(gID);
		
		u.getGroups().add(g);
		g.getUsers().add(u);
		
		userDB.save(u);
		groupDB.save(g);
		
		return u;
	}
	
	/**
	 * PRECONDITION: The group must already exist in the group repository
	 * Allows a user to leave a group, updates both the group and users lists of each other
	 * @param uID - repository ID of the user
	 * @param gID - repository ID of the group
	 * @return updated User
	 */
	@PutMapping("/user/{uID}/group/{gID}/leave")
	User leaveGroup(@PathVariable Integer uID, @PathVariable Integer gID)
	{
		User old_U = userDB.findOne(uID);
		Group old_G = groupDB.findOne(gID);
		
		old_U.getGroups().remove(old_G);
		old_G.getUsers().remove(old_U);
		
		userDB.save(old_U);
		groupDB.save(old_G);
		
		return old_U;
	}
	
	/**
	 * Deletes a task from a user's list and the task repository
	 * @param uID - repository ID of the user
	 * @param tID - repository ID of the task
	 * @return updated User
	 */
	@DeleteMapping("/user/{uID}/deleteTask/{tID}")
	User removeTask(@PathVariable Integer uID, @PathVariable Integer tID)
	{
		User old_U = userDB.findOne(uID);
		Task toRem = taskDB.findOne(tID);
		
		old_U.getTasks().remove(toRem);
		toRem.setUser(null);
		
		taskDB.delete(tID);
		userDB.save(old_U);
		
		return old_U;
	}
	
	/**
	 * Updates a task from within a user
	 * @param uID - DB id of the user
	 * @param tID - DB id of the desired task
	 * @param t - Task to update with
	 * @return the updated task
	 */
	@PutMapping("/user/{uID}/putTask/{tID}")
	Task updateTask(@PathVariable Integer uID, @PathVariable Integer tID, @RequestBody Task t)
	{
		User u = userDB.findOne(uID);
		Task toUpdate = taskDB.findOne(tID);
		
		if(u.getTasks().contains(toUpdate))
		{
			myProject.Utils.Utilities.updateTask(toUpdate, t);
			
			toUpdate.setUser(u);
			
			taskDB.save(toUpdate);
			userDB.save(u);
			
			return toUpdate;
		}
		else
			return null;
	}
	
	/**
	 * Deletes all tasks associated with the user,
	 * removes the user from any groups it is in,
	 * delete the user
	 * @param id - repository id of the user
	 * @return message with deleted id
	 */
	@DeleteMapping("/user/{id}")
	String deleteUser(@PathVariable Integer id)
	{
		User u = userDB.findOne(id);
		
		for(Task t : u.getTasks())
			taskDB.delete(t);
		
		for(Group g : u.getGroups())
			g.getUsers().remove(u);
		
		userDB.delete(u);
		return "Deleted " + id;
	}
	
	/**
	 * PRECONDITION: startID < endID
	 * Deletes users in the range between two given IDs, if they exist
	 * and using the deleteUser() method
	 * @param startID - starting repository ID of the range to be deleted
	 * @param endID - ending repository ID of the range to be deleted 
	 * @return message with the deleted range
	 */
	@DeleteMapping("/user/{startID}/{endID}")
	String bulkDelete(@PathVariable Integer startID, @PathVariable Integer endID)
	{
		for(int i = startID; i <= endID; i++)
			if(userDB.exists(i))
				this.deleteUser(i);
		
		return "Deleted ID range: "+startID+"-"+endID;
	}
}