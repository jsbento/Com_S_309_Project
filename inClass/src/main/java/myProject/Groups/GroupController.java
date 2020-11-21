package myProject.Groups;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import myProject.Users.UserDB;
import myProject.Tasks.GroupTaskDB;
import myProject.Tasks.Task;
import myProject.Authentication.Authenticator;
import myProject.Tasks.GroupTask;
import myProject.Users.User;
import io.swagger.annotations.*;
/**
 * Controller for groups in the DB
 * @author Jacob Benton (jsbenton), Noah Cantrell (nbc)
 *
 */
@RestController
public class GroupController
{
	/**
	 * Group repository
	 */
	@Autowired
	GroupDB groupDB;
	
	/**
	 * User repository
	 */
	@Autowired
	UserDB userDB;
	
	/**
	 * GroupTask repository
	 */
	@Autowired
	GroupTaskDB gTaskDB;
	
	/**
	 * Gets a Group with the given repository ID
	 * @param id - repository ID of the desired Group
	 * @return Group with the given ID
	 */
	@GetMapping("/group/{id}")
	Group getGroup(@PathVariable Integer id)
	{
		return groupDB.findOne(id);
	}
	
	/**
	 * Gets the Group with the desired name
	 * @param requested - name of the requested Group
	 * @return Group with the requested name if it exists, null otherwise
	 */
	@GetMapping("/group/getGroupByName")
	Group getGroupByName(@RequestParam String requested) 
	{
		List<Group> groups = groupDB.findAll();
		
		for(Group g : groups) 
		{
			if(g.groupName.equalsIgnoreCase(requested))
				return g;
		}
		
		return null;
	}
	
	/**
	 * Gets all Groups in the repository
	 * @return List of all Groups in the repository
	 */
	@RequestMapping("/groups")
	List<Group> getGroups()
	{
		return groupDB.findAll();
	}
	
	/**
	 * Gets all users in a given group
	 * @param gName
	 * @return List of all Users in requested group, null if no group is found
	 */
	@GetMapping("/group/getGroupUsers")
	List<User> getGroupUsers(@RequestParam String gName)
	{	
		for(Group g : groupDB.findAll()) 
		{
			if(g.getName().equalsIgnoreCase(gName))
				return g.getUsers();
		}
		
		return null;
	}
	
	/**
	 * Saves a Group to the repository
	 * @param g - Group to be added
	 * @return added Group if it is authenticated, null otherwise
	 */
	@PostMapping("/group")
	Group createGroup(@RequestBody Group g)
	{
		if(Authenticator.groupAuthenticate(g, groupDB.findAll()))
		{
			groupDB.save(g);
			return g;
		}
		
		return null;
	}
	
	/**
	 * Updates the name of the Group
	 * @param g - updated Group
	 * @param id - repository ID of the Group to update
	 * @return updated Group
	 */
	@PutMapping("/group/updateGroupName/{id}")
	Group updateGroupName(@RequestBody Group g, @PathVariable Integer id)
	{
		Group old_G = groupDB.findOne(id);
		old_G.setGroupName(g.groupName);
		groupDB.save(old_G);
		return old_G;
	}
	
	/**
	 * Updates the start of the Group
	 * @param g - updated Group
	 * @param id - repository ID of the Group to update
	 * @return updated Group
	 */
	@PutMapping("/group/{id}/updateStartDate")
	Group updateStartDate(@PathVariable Integer id, @RequestBody Group g)
	{
		Group old_G = groupDB.findOne(id);
		old_G.setStartDate(g.getStartDate());
		groupDB.save(old_G);
		return old_G;
	}
	
	/**
	 * Merges two groups together
	 * @param mergerID - repository ID of the host Group
	 * @param mergeeID - repository ID of the Group to be merged
	 * @return merged host Group
	 */
	@PutMapping("/group/{g1}/mergeGroups/{g2}")
	Group mergeGroup(@PathVariable Integer g1, @PathVariable Integer g2) 
	{	
		Group group1 = groupDB.findOne(g1);
		Group group2 = groupDB.findOne(g2);
		
		for(User u : group2.getUsers()) 
		{
			if(!(group1.getUsers().contains(u))) 
			{
				group1.getUsers().add(u);
				u.getGroups().add(group1);
				u.getGroups().remove(group2);
				userDB.save(u);
			}
		}
		
		for(GroupTask gt : group2.getTasks()) 
		{
			if(!(group1.getTasks().contains(gt))) 
			{
				group1.getTasks().add(gt);
				gt.setGroup(group1);
				gTaskDB.save(gt);
			}
		}
		
		groupDB.delete(group2);
		
		groupDB.save(group1);
		
		return group1;
	}
	
	/**
	 * PRECONDITION: User must already exist
	 * Adds a user to a Group
	 * @param gID - repository ID of the Group
	 * @param uID - repository ID of the joining User
	 * @return updated Group
	 */
	@PutMapping("/group/{gID}/userID/{uID}/addUser")
	Group addUser(@PathVariable Integer gID, @PathVariable Integer uID)
	{
		Group old_G = groupDB.findOne(gID);
		User newU = userDB.findOne(uID);
		
		old_G.getUsers().add(newU);
		newU.getGroups().add(old_G);
		
		groupDB.save(old_G);
		userDB.save(newU);
		
		return old_G;
		
	}
	
	/**
	 * PRECONDITION: Users must all exist
	 * Adds multiple users to group by their repository IDs
	 * @param gID - repository ID of the group
	 * @param userIDs - List of repository IDs of joining users
	 * @return updated group
	 */
	@PutMapping("/group/{gID}/bulkAdd/{userIDs}")
	Group bulkAddUser(@PathVariable Integer gID, @PathVariable int[] userIDs)
	{
		Group g = groupDB.findOne(gID);
		
		for(int i : userIDs) 
		{
			User u = userDB.findOne(i);
			if((!(g.getUsers().contains(u))) && u != null) 
			{
				g.getUsers().add(u);
				u.getGroups().add(g);
				userDB.save(u);
			}
		}
		
		groupDB.save(g);
		
		return g;
	}
	
	
	/**
	 * Adds a GroupTask the target Group
	 * @param id - repository ID of the Group
	 * @param gt - GroupTask to add
	 * @return updated Group
	 */
	@PutMapping("/group/{id}/addTask")
	GroupTask addGroupTask(@PathVariable Integer id, @RequestBody GroupTask gt)
	{
		Group old_G = groupDB.findOne(id);
		
		old_G.getTasks().add(gt);
		gt.setGroup(old_G);
		
		GroupTask result = gTaskDB.save(gt);
		groupDB.save(old_G);
		
		return result;
	}
		
	/**
	 * Removes a user from a Group
	 * @param gID - repository ID of the Group
	 * @param uID - repository ID of the leaving User
	 * @return updated Group
	 */
	@PutMapping("/group/{gID}/userID/{uID}/removeUser")
	Group removeUser(@PathVariable Integer gID, @PathVariable Integer uID)
	{	
		Group old_G = groupDB.findOne(gID);
		User toRem = userDB.findOne(uID);
		
		old_G.getUsers().remove(toRem);
		toRem.getGroups().remove(old_G);
		
		groupDB.save(old_G);
		userDB.save(toRem);
		
		return old_G;
	}
	
	/**
	 * Removes a GroupTask from a Group
	 * @param gID - repository ID of the Group
	 * @param gtID - repository ID of the GroupTask to remove
	 * @return updated Group
	 */
	@DeleteMapping("/group/{gID}/groupTask/{gtID}")
	Group removeTask(@PathVariable Integer gID, @PathVariable Integer gtID)
	{
		Group old_G = groupDB.findOne(gID);
		GroupTask toRem = gTaskDB.findOne(gtID);
		
		old_G.getTasks().remove(toRem);
		toRem.setGroup(null);
		
		gTaskDB.delete(gtID);
		groupDB.save(old_G);
		
		return old_G;
	}

	/**
	 * Deletes a Group,
	 * deletes all associated GroupTasks,
	 * updated Group lists of member Users
	 * @param id - repository ID of the group to be deleted
	 * @return deleted ID message
	 */
	@DeleteMapping("/group/{id}")
	String deleteGroup(@PathVariable Integer id)
	{
		Group toDelete = groupDB.findOne(id);
		
		for(User u : toDelete.getUsers())
			for(Group g : u.getGroups())
				if(toDelete.getGroupID().equalsIgnoreCase(g.getGroupID()))
				{
					u.getGroups().remove(g);
					g.getUsers().remove(u);
					
					userDB.save(u);
					groupDB.save(g);
				}
		
		for(GroupTask gt : toDelete.getTasks())
		{
			gt.setGroup(null);
			toDelete.getTasks().remove(gt);
			gTaskDB.delete(gt);
		}
		
		groupDB.delete(toDelete);
		return "Deleted " + id;
	}
	
	/**
	 * PRECONDITION: startID < endID
	 * Deletes Groups in the range between two given IDs, if they exist
	 * and using the deleteUser() method
	 * @param startID - starting repository ID of the range to be deleted
	 * @param endID - ending repository ID of the range to be deleted 
	 * @return message with the deleted range
	 */
	@DeleteMapping("/group/{startID}/{endID}")
	String bulkDelete(@PathVariable Integer startID, @PathVariable Integer endID)
	{
		for(int i = startID; i <= endID; i++)
			if(groupDB.exists(i))
				this.deleteGroup(i);
		
		return "Deleted ID range: "+startID+"-"+endID;
	}
}
