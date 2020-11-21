package myProject.Tasks;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import myProject.Groups.Group;

/**
 * GroupTask model for DB use, specifically for groups
 * @author Jacob Benton (jsbenton), Noah Cantrell (nbc)
 *
 */
@Entity
@Table(name = "GROUP_TASKS")
public class GroupTask
{
	/**
	 * Auto-generated DB use ID
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	/**
	 * Name of the task
	 */
	@Column(name = "name")
	String name;
	
	/**
	 * Given ID of the task
	 */
	@Column(name = "groupTaskID")
	String groupTaskID;
	
	/**
	 * Completion status of the task
	 */
	@Column(name = "completed")
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
	 * Group this task belongs to (many-to-one relation)
	 */
	@ManyToOne
	@JoinColumn(name = "groupid")
	@JsonIgnore
	Group group;
	
	/**
	 * Gets the auto-generated DB id
	 * @return - the auto-generated id
	 */
	public Integer getID() {return id;}
	
	/**
	 * Gets the name of the task
	 * @return - task name
	 */
	public String getName() {return name;}
	
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
	public String getGroupTaskID() {return groupTaskID;}
	
	/**
	 * Gets the group this task belongs to
	 * @return - group this tasks belongs to
	 */
	public Group getGroup() {return group;}	
	
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
	 * Sets the DB use id of the task (not used in practice)
	 * @param id - the DB use id
	 */
	public void setID(Integer id) {this.id = id;}
	
	/**
	 * Sets the name of the task
	 * @param groupTaskName - new task name
	 */
	public void setGroupTaskName(String name) {this.name = name;}
	
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
	 * @param groupTaskID - new given ID
	 */
	public void setGroupTaskID(String groupTaskID) {this.groupTaskID = groupTaskID;}
	
	
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
	 * Sets the group this task belongs to
	 * @param g - group this task belongs to
	 */
	public void setGroup(Group g) {this.group = g;}
}
