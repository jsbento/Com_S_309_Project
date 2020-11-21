package myProject.Users;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import myProject.Groups.Group;
import myProject.Tasks.Task;

import java.util.List;

/**
 * User class for DB use
 * @author Jacob Benton (jsbenton), Noah Cantrell (nbc)
 *
 */
@Entity
@Table(name = "USERS")
public class User implements Comparable<User>
{
	/**
	 * Stores the id of the user for database reference
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	/**
	 * Name of the user
	 */
	@Column(name = "name")
	String name;
	
	/**
	 * ID used to one-to-many and many-to-many relations
	 * between tasks and groups respectively
	 */
	@Column(name = "userid")
	String userID;
	
	/**
	 * User's password
	 */
	@Column(name = "password")
	String password;
	
	/**
	 * User's email
	 */
	@Column(name = "email")
	String email;
	
	/**
	 * User's phone number
	 */
	@Column(name = "phone_num")
	String phoneNum;
	
	/**
	 * True if email has been verified, false otherwise
	 */
	@Column(name = "email_verif")
	boolean eMailVerif;
	
	/**
	 * True if phone number has been verified, false otherwise 
	 */
	@Column(name = "phone_verif")
	boolean phoneVerif;
	
	/**
	 * List of tasks for this user (one-to-many relation)
	 */
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	List<Task> tasks;
	
	/**
	 * List of groups this user belongs to (many-to-many relation)
	 */
	@ManyToMany(mappedBy = "users")
	@JsonIgnore
	List<Group> groups;
	
	/**
	 * Gets the DB reference ID of the user
	 * @return DB auto-generated ID
	 */
	public Integer getId() {return id;}
	
	/**
	 * Gets name of the user
	 * @return name of the user
	 */
	public String getName() {return name;}
	
	/**
	 * Gets the given userID
	 * @return given ID of the user
	 */
	public String getUserID() {return userID;}
	
	/**
	 * Gets the user's password
	 * @return the user's password
	 */
	public String getPassword() {return password;}
	
	/**
	 * Gets the email of the user
	 * @return the user's email
	 */
	public String getEmail() {return email;}
	
	/**
	 * Gets the phone number of the user
	 * @return the user's phone number
	 */
	public String getPhoneNum() {return phoneNum;}
	
	/**
	 * Gets the verification status of the user's email
	 * @return true if email is verified, false otherwise
	 */
	public boolean emailVerified() {return eMailVerif;}
	
	/**
	 * Gets the verification status of the user's phone number
	 * @return true if phone number is verified, false otherwise
	 */
	public boolean phoneVerified() {return phoneVerif;}
	
	/**
	 * Gets the tasks of the user
	 * @return the list of tasks of the user
	 */
	public List<Task> getTasks() {return tasks;}
	
	/**
	 * Gets the groups of the user
	 * @return the list of groups the user belongs to
	 */
	public List<Group> getGroups() {return groups;}
	
	/**
	 * Sets the DB use id of the user (not used in practice)
	 * @param id - DB use id
	 */
	public void setID(Integer id) {this.id = id;}
	
	/**
	 * Sets user name of the user
	 * @param userName - new user name
	 */
	public void setUserName(String name) {this.name = name;}
	
	/**
	 * Sets the given ID of the user
	 * @param userID - new given ID
	 */
	public void setUserID(String userID) {this.userID = userID;}
	
	/**
	 * Sets password of the user
	 * @param password - new password
	 */
	public void setUserPassword(String password) {this.password = password;}
	
	/**
	 * Sets email of the user
	 * @param email - new email
	 */
	public void setUserEmail(String email) {this.email = email;}
	
	/**
	 * Sets phone number of the user
	 * @param phoneNum - new phone number
	 */
	public void setUserPhoneNum(String phoneNum) {this.phoneNum = phoneNum;}
	
	/**
	 * Updates verification status of the user's email
	 * @param verified - the verification status of the user's email
	 */
	public void setEmailVerified(boolean verified) {eMailVerif = verified;}
	
	/**
	 * Updates verification status of the user's phone number
	 * @param verified - the verification status of the user's phone number
	 */
	public void setPhoneVerified(boolean verified) {phoneVerif = verified;}
	
	/**
	 * Sets the task list of the user
	 * @param tasks - new list of tasks for the user
	 */
	public void setTasks(List<Task> tasks) {this.tasks = tasks;}
	
	/**
	 * Sets the groups list of the user
	 * @param groups - new list of groups for the user
	 */
	public void setGroups(List<Group> groups) {this.groups = groups;}
	
	/**
	 * Compares two users by userID, email, and phone number
	 * Used in authentication of users when adding new users
	 * @param comp - user to compare against
	 * @return -1 if users are the same by comparison criteria, 0 otherwise
	 */
	@Override
	public int compareTo(User comp)
	{		
		if(this.userID.equalsIgnoreCase(comp.userID) ||
		   this.email.equalsIgnoreCase(comp.email) ||
		   this.phoneNum.equalsIgnoreCase(comp.phoneNum))
			return -1;
		return 0;
	}
}
