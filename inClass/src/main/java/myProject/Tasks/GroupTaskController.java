package myProject.Tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for group tasks in the DB
 * @author Jacob Benton (jsbenton), Noah Cantrell (nbc)
 */
@RestController
public class GroupTaskController
{
	/**
	 * GroupTask repository
	 */
	@Autowired
	GroupTaskDB gTaskDB;
	
	/**
	 * Saves a GroupTask to the repository
	 * @param gt
	 * @return
	 */
	@PostMapping("/groupTask")
	GroupTask createGroupTask(@RequestBody GroupTask gt)
	{
		gTaskDB.save(gt);
		return gt;
	}
	
	/**
	 * Gets all GroupTasks saved in the repository
	 * @return List containing all items saved in the repository
	 */
	@RequestMapping("/groupTasks")
	List<GroupTask> getGroupTasks()
	{
		return gTaskDB.findAll();
	}
	
	/**
	 * Returns the GroupTask a the specific ID
	 * @param id - repository ID of the GroupTask
	 * @return GroupTask with the given ID
	 */
	@GetMapping("/groupTask/{id}")
	GroupTask getTask(@PathVariable Integer id)
	{
		return gTaskDB.findOne(id);
	}
	
	/**
	 * Updates the name of a GroupTask
	 * @param gt - GroupTask with updated information
	 * @param id - repository ID of the GroupTask to update
	 * @return updated GroupTask
	 */
	@PutMapping("/groupTask/updateTaskName/{id}")
	GroupTask updateTaskName(@RequestBody GroupTask gt, @PathVariable Integer id)
	{
		GroupTask old_T = gTaskDB.findOne(id);
		old_T.setGroupTaskName(gt.name);
		gTaskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Updates the given ID of a GroupTask
	 * @param gt - GroupTask with updated information
	 * @param id - repository ID of the GroupTask to update
	 * @return updated GroupTask
	 */
	@PutMapping("/groupTask/updateGroupTaskID/{id}")
	GroupTask updateTaskID(@RequestBody GroupTask gt, @PathVariable Integer id)
	{
		GroupTask old_T = gTaskDB.findOne(id);
		old_T.setGroupTaskID(gt.groupTaskID);
		gTaskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Updates the assign date of a GroupTask
	 * @param gt - GroupTask with updated information
	 * @param id - repository ID of the GroupTask to update
	 * @return updated GroupTask
	 */
	@PutMapping("/groupTask/updateAssignDate/{id}")
	GroupTask updateAssignDate(@RequestBody GroupTask gt, @PathVariable Integer id)
	{
		GroupTask old_T = gTaskDB.findOne(id);
		old_T.setAssignDate(gt.assignDate);;
		gTaskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Updates the due date of a GroupTask
	 * @param gt - GroupTask with updated information
	 * @param id - repository ID of the GroupTask to update
	 * @return updated GroupTask
	 */
	@PutMapping("/groupTask/updateDueDate/{id}")
	GroupTask updateDueDate(@RequestBody GroupTask gt, @PathVariable Integer id)
	{
		GroupTask old_T = gTaskDB.findOne(id);
		old_T.setDueDate(gt.dueDate);
		gTaskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Updates the completion status of a GroupTask
	 * @param gt - GroupTask with updated information
	 * @param id - repository ID of the GroupTask to update
	 * @return updated GroupTask
	 */
	@PutMapping("/groupTask/updateCompletion/{id}")
	GroupTask updateCompletion(@RequestBody GroupTask gt, @PathVariable Integer id)
	{
		GroupTask old_T = gTaskDB.findOne(id);
		old_T.setCompletion(gt.completed);
		gTaskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Updates the archive status of a GroupTask
	 * @param gt - GroupTask with updated information
	 * @param id - repository ID of the GroupTask to update
	 * @return updated GroupTask
	 */
	@PutMapping("/groupTask/updateArchived/{id}")
	GroupTask updateArchived(@RequestBody GroupTask gt, @PathVariable Integer id)
	{
		GroupTask old_T = gTaskDB.findOne(id);
		old_T.setArchived(gt.archived);
		gTaskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Deletes a GroupTask with the given ID
	 * @param id - repository ID of the GroupTask to be deleted
	 * @return deleted ID message
	 */
	@DeleteMapping("/groupTask/{id}/deleteTask")
	String deleteTask(@PathVariable Integer id)
	{
		GroupTask toRemove = gTaskDB.findOne(id);
		
		toRemove.getGroup().getTasks().remove(toRemove);
		toRemove.setGroup(null);
		
		gTaskDB.delete(toRemove);
		return "Deleted " + id;
	}
	
	/**
	 * PRECONDITION: startID < endID
	 * Deletes GroupTasks in the range between two given IDs, if they exist
	 * and using the deleteUser() method
	 * @param startID - starting repository ID of the range to be deleted
	 * @param endID - ending repository ID of the range to be deleted 
	 * @return message with the deleted range
	 */
	@DeleteMapping("/groupTask/{startID}/{endID}")
	String bulkDelete(@PathVariable Integer startID, @PathVariable Integer endID)
	{
		for(int i = startID; i <= endID; i++)
			if(gTaskDB.exists(i))
				this.deleteTask(i);
		
		return "Deleted ID range: "+startID+"-"+endID;
	}
}