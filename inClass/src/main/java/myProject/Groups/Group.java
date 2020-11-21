package myProject.Groups;

import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import myProject.Tasks.GroupTask;
import myProject.Users.User;
/**
 * Group model for DB use
 * @author Jacob Benton (jsbenton), Noah Cantrell (nbc)
 *
 */
@Entity
@Table(name = "GROUVENTS")
public class Group implements Comparable<Group>
{
	/**
	 * Auto-generated DB use ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	/**
	 * Name of the group
	 */
	@Column(name = "group_name")
	String groupName;
	
	/**
	 * Given ID of the group
	 */
	@Column(name = "groupid")
	String groupID;
	
	/**
	 * Denotes if this group is an event or not (null if NOT an event)
	 * If not null, stores start date of the event
	 */
	@Column(name = "start_date", nullable = true)
	String startDate; //MM-DD-YYYY
	
	/**
	 * Denotes if this group is an event or not (null if NOT an event)
	 * If not null, stores the end date of the event
	 */
	@Column(name = "end_date", nullable = true)
	String endDate; //MM-DD-YYYY
	
	/**
	 * List of users belonging to this group (many-to-many relation)
	 */
	@ManyToMany
	@JoinTable(
		name = "user_groups",
		joinColumns = @JoinColumn(name = "groupID"),
		inverseJoinColumns = @JoinColumn(name = "userID"))
	@JsonIgnore
	List<User> users;
	
	/**
	 * List of tasks belong to this group (one-to-many relation)
	 */
	@OneToMany(mappedBy = "group")
	@JsonIgnore
	List<GroupTask> groupTasks;
	
	/**
	 * Gets the auto-generated DB ID
	 * @return - the auto-generated ID
	 */
	public Integer getID() {return id;}
	
	/**
	 * Gets the name of the group
	 * @return - the name of the group
	 */
	public String getName() {return groupName;}
	
	/**
	 * Gets the given ID of the group
	 * @return - the given ID of the group
	 */
	public String getGroupID() {return groupID;}
	
	/**
	 * Gets the start date of the group if it is an event
	 * @return - null if not an event, otherwise the start date of the event
	 */
	public String getStartDate() {return startDate;}
	
	/**
	 * Gets the end date of the group if it is an event
	 * @return - null if not an event, otherwise the end date of the event
	 */
	public String getEndDate() {return endDate;}
	
	/**
	 * Gets the list of users belonging to the group
	 * @return - list of users
	 */
	public List<User> getUsers() {return users;}
	
	/**
	 * Gets the list of tasks belonging to the group
	 * @return - list of GroupTask objects belonging to the group
	 */
	public List<GroupTask> getTasks() {return groupTasks;}
	
	/**
	 * Sets the DB use ID of the group (not used in practice)
	 * @param id - new ID
	 */
	public void setID(Integer id) {this.id = id;}
	
	/**
	 * Sets the name of the group
	 * @param groupName - new group name
	 */
	public void setGroupName(String groupName) {this.groupName = groupName;}
	
	/**
	 * Sets the given ID of the group
	 * @param groupID - new given ID
	 */
	public void setGroupID(String groupID) {this.groupID = groupID;}
	
	/**
	 * Sets the start date, turns group into an event
	 * @param startDate - new start date
	 */
	public void setStartDate(String startDate) {this.startDate = startDate;}
	
	/**
	 * Sets the end date, turns group into an event
	 * @param endDate - new end date of the event
	 */
	public void setEndDate(String endDate) {this.endDate = endDate;}
	
	/**
	 * Sets the list of users belonging to the group
	 * @param users - new list of users
	 */
	public void setUsers(List<User> users) {this.users = users;}
	
	/**
	 * Sets the list of tasks belonging to the group
	 * @param tasks - new list of GroupTask objects
	 */
	public void setTasks(List<GroupTask> tasks) {this.groupTasks = tasks;}

	/**
	 * Compares groups by given ID and name, used in group authentication
	 * @param comp - the group to compare to
	 * @return -1 if the groups are the same by comparison criteria, 0 otherwise
	 */
	@Override
	public int compareTo(Group comp)
	{
		if(this.getGroupID().equalsIgnoreCase(comp.getGroupID()) ||
		   this.getName().equalsIgnoreCase(comp.getName()))
			return -1;
		return 0;
	}
	
}