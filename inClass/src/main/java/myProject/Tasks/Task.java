package myProject.Tasks;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import myProject.Users.User;

/**
 * Task model for DB use
 * @author Jacob Benton (jsbenton), Noah Cantrell (nbc)
 *
 */
@Entity
@Table(name = "TASKS")
public class Task
{
	/**
	 * Auto-generated ID for database use
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	/**
	 * Name of the task
	 */
	@Column(name = "title")
	String title;
	
	/**
	 * Given ID of the task
	 */
	@Column(name = "taskID")
	String taskID;
	
	/**
	 * Completion status of the task
	 */
	@Column(name = "completed", nullable = true)
	boolean completed;
	
	/**
	 * Archived status of the task
	 */
	@Column(name = "archived", nullable = true)
	boolean archived;
	
	/**
	 * Due date of the task
	 */
	@Column(name = "dueDate")
	String dueDate; //YYYY-MM-DD
	
	/**
	 * Date task was/is assigned
	 */
	@Column(name = "assignDate")
	String assignDate; //YYYY-MM-DD
	
	/**
	 * Tag for a task, i.e. "Important" or "Not Important"
	 */
	@Column(name = "tag")
	String tag;
	
	/**
	 * User a task belongs to (many-to-one relation)
	 */
	@ManyToOne
	@JoinColumn(name = "userid")
	@JsonIgnore
	User user;
	
	/**
	 * Gets the auto-generated DB id
	 * @return - the auto-generated id
	 */
	public Integer getID() {return id;}
	
	/**
	 * Gets the name of the task
	 * @return - task name
	 */
	public String getTitle() {return title;}
	
	/**
	 * Gets completion status of the task
	 * @return - true if task completed, false otherwise
	 */
	public boolean isCompleted() {return completed;}
	
	/**
	 * Gets archive status of task
	 * @return - true if task archived, false otherwise
	 */
	public boolean isArchived() {return archived;}
	
	/**
	 * Gets the given ID of the task
	 * @return - given ID of the task
	 */
	public String getTaskID() {return taskID;}
	
	/**
	 * Gets the user this task belongs to
	 * @return - user the task belongs to
	 */
	public User getUser() {return user;}
	
	/**
	 * Gets the assign date of the task
	 * @return - assign date of the task
	 */
	public String getAssignDate() {return assignDate;}
	
	/**
	 * Gets the due date of the task
	 * @return - due date of the task
	 */
	public String getDueDate() {return dueDate;}
	
	/**
	 * Gets the tag of the task
	 * @return - the tag of the task
	 */
	public String getTag() {return tag;}
	
	/**
	 * Sets the DB use id of the task (not used in practice)
	 * @param id - the DB use id
	 */
	public void setID(Integer id) {this.id = id;}
	
	/**
	 * Sets the name of the task
	 * @param taskName - new task name
	 */
	public void setTaskTitle(String title) {this.title = title;}
	
	/**
	 * Sets the assign date of the task
	 * @param assignDate - new assign date
	 */
	public void setAssignDate(String assignDate) {this.assignDate = assignDate;}
	
	/**
	 * Sets the due date of the task
	 * @param dueDate - new due date
	 */
	public void setDueDate(String dueDate) {this.dueDate = dueDate;}
	
	/**
	 * Sets the given ID of the task
	 * @param taskID - new given ID
	 */
	public void setTaskID(String taskID) {this.taskID = taskID;}
	
	/**
	 * Sets the completion status of the task
	 * @param isCompleted - new completion status
	 */
	public void setCompletion(boolean completed) {this.completed = completed;}
	
	/**
	 * Sets the archived status of the task
	 * @param archived - new archived status
	 */
	public void setArchived(boolean archived) {this.archived = archived;}
	
	/**
	 * Sets the user this task belongs to
	 * @param u - user this task belongs to
	 */
	public void setUser(User u) {user = u;}
	
	/**
	 * Sets the tag of the task
	 * @param tag - new tag
	 */
	public void setTag(String tag) {this.tag = tag;}
}