package myProject.Tasks;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for tasks in the DB, specifically for users
 * @author Jacob Benton (jsbenton), Noah Cantrell (nbc)
 *
 */
@RestController
public class TaskController
{
	/**
	 * Task repository
	 */
	@Autowired
	TaskDB taskDB;
	
	/**
	 * Gets a Task by the given ID
	 * @param id - repository ID of the Task
	 * @return Task with the given ID
	 */
	@GetMapping("/task/{id}")
	Task getTask(@PathVariable Integer id)
	{
		return taskDB.findOne(id);
	}
	
	/**
	 * Gets a task by the given name
	 * @param tn - name of the desired Task
	 * @return Task with the given name, null if not found
	 */
	@GetMapping("/task/getbyTitle")
	Task getTaskByName(@RequestParam String title) 
	{
		for(Task t : taskDB.findAll()) 
		{
			if(t.getTitle().equalsIgnoreCase(title))
				return t;
		}
		
		return null;
	}
	
	/**
	 * Gets a task by the given non-auto-generated ID
	 * @param tn - name of the desired Task
	 * @return Task with the given non-auto-generated, null if not found
	 */
	@GetMapping("/task/getbyID")
	Task getTaskByID(@RequestParam String tid) 
	{
		for(Task t : taskDB.findAll()) 
		{
			if(t.taskID.equalsIgnoreCase(tid))
				return t;
		}
		
		return null;
	}
	
	/**
	 * Gets all tasks stored in the repository
	 * @return List of all Task objects in the repository
	 */
	@RequestMapping("/tasks")
	List<Task> getTasks()
	{
		return taskDB.findAll();
	}
	
	/**
	 * Saves a new task to the repository
	 * @param t - the Task to be saved
	 * @return the added Task
	 */
	@PostMapping("/task")
	Task createTask(@RequestBody Task t) 
	{
		taskDB.save(t);
		return t;
	}
	
	/**
	 * Updates the name of a task with the repository ID
	 * @param t - updated Task
	 * @param id - repository ID of the Task to update
	 * @return updated Task
	 */
	@PutMapping("/task/updateTaskName/{id}")
	Task updateTaskName(@RequestBody Task t, @PathVariable Integer id)
	{
		Task old_T = taskDB.findOne(id);
		old_T.setTaskTitle(t.getTitle());
		taskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Updates the given of a task with the repository ID
	 * @param t - updated Task
	 * @param id - repository ID of the Task to update
	 * @return updated Task
	 */
	@PutMapping("/task/updateTaskID/{id}")
	Task updateTaskID(@RequestBody Task t, @PathVariable Integer id)
	{
		Task old_T = taskDB.findOne(id);
		old_T.setTaskID(t.taskID);;
		taskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Updates the assign date of a task with the repository ID
	 * @param t - updated Task
	 * @param id - repository ID of the Task to update
	 * @return updated Task
	 */
	@PutMapping("/task/updateAssignDate/{id}")
	Task updateAssignDate(@RequestBody Task t, @PathVariable Integer id)
	{
		Task old_T = taskDB.findOne(id);
		old_T.setAssignDate(t.assignDate);;
		taskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Updates the due date of a task with the repository ID
	 * @param t - updated Task
	 * @param id - repository ID of the Task to update
	 * @return updated Task
	 */
	@PutMapping("/task/updateDueDate/{id}")
	Task updateDueDate(@RequestBody Task t, @PathVariable Integer id)
	{
		Task old_T = taskDB.findOne(id);
		old_T.setDueDate(t.dueDate);
		taskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Updates the tag of task with the repository ID
	 * @param id - repository ID of the Task to update
	 * @param t - updated Task
	 * @return updated Task
	 */
	@PutMapping("/task/updateTag/{id}")
	Task updateTag(@PathVariable Integer id, @RequestBody Task t)
	{
		Task old_T = taskDB.findOne(id);
		old_T.setTag(t.getTag());
		taskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Updates the completion status of a task with the repository ID
	 * @param t - updated Task
	 * @param id - repository ID of the Task to update
	 * @return updated Task
	 */
	@PutMapping("/task/updateCompletion/{id}")
	Task updateCompletion(@RequestBody Task t, @PathVariable Integer id)
	{
		Task old_T = taskDB.findOne(id);
		old_T.setCompletion(t.completed);
		taskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Updates the archive status of a task with the repository ID
	 * @param t - updated Task
	 * @param id - repository ID of the Task to update
	 * @return updated Task
	 */
	@PutMapping("/task/updateArchived/{id}")
	Task updateArchived(@RequestBody Task t, @PathVariable Integer id)
	{
		Task old_T = taskDB.findOne(id);
		old_T.setArchived(t.archived);
		taskDB.save(old_T);
		return old_T;
	}
	
	/**
	 * Deletes a task with the given repository ID
	 * @param id - repository ID of the task to be deleted
	 * @return deleted ID message
	 */
	@DeleteMapping("/task/{id}")
	String deleteTask(@PathVariable Integer id)
	{
		Task toRemove = taskDB.findOne(id);
		
		toRemove.getUser().getTasks().remove(toRemove);
		
		taskDB.delete(toRemove);
		return "Deleted " + id;
	}
	
	/**
	 * PRECONDITION: startID < endID
	 * Deletes Tasks in the range between two given IDs, if they exist
	 * and using the deleteUser() method
	 * @param startID - starting repository ID of the range to be deleted
	 * @param endID - ending repository ID of the range to be deleted 
	 * @return message with the deleted range
	 */
	@DeleteMapping("/task/{startID}/{endID}")
	String bulkDelete(@PathVariable Integer startID, @PathVariable Integer endID)
	{
		for(int i = startID; i <= endID; i++)
			if(taskDB.exists(i))
				this.deleteTask(i);
		
		return "Deleted ID range: "+startID+"-"+endID;
	}
}