package myProject.Utils;

import myProject.Users.*;
import myProject.Groups.*;
import myProject.Tasks.*;
/**
 * Various utilities add needed for project
 * 
 * @author Jacob Benton (jsbenton), Noah Cantrell (nbc)
 *
 */
public class Utilities
{
	/**
	 * Updates a task with the info of another
	 * @param t1 - task to updated
	 * @param t2 - task used to update
	 */
	public static void updateTask(Task t1, Task t2)
	{
		//t1.setUser(t2.getUser());
		t1.setTaskTitle(t2.getTitle());
		t1.setTaskID(t2.getTaskID());
		t1.setDueDate(t2.getDueDate());
		t1.setAssignDate(t2.getAssignDate());
		t1.setCompletion(t2.isCompleted());
		t1.setArchived(t2.isArchived());
		t1.setTag(t2.getTag());
	}
	
	/**
	 * Updates a user with the info of another
	 * @param u1 - user to updated
	 * @param u2 - user used to update
	 */
	public static void updateUser(User u1, User u2)
	{
		u1.setEmailVerified(u2.emailVerified());
		u1.setGroups(u2.getGroups());
		u1.setPhoneVerified(u2.phoneVerified());
		u1.setTasks(u2.getTasks());
		u1.setUserEmail(u2.getEmail());
		u1.setUserID(u2.getUserID());
		u1.setUserName(u2.getName());
		u1.setUserPassword(u2.getPassword());
		u1.setUserPhoneNum(u2.getPhoneNum());
	}
	
	/**
	 * Updates a Group with the info of another
	 * @param g1 - Group to updated
	 * @param g2 - Group used to update
	 */
	public static void updateGroup(Group g1, Group g2)
	{
		g1.setEndDate(g2.getEndDate());
		g1.setGroupID(g2.getGroupID());
		g1.setGroupName(g2.getName());
		g1.setStartDate(g2.getStartDate());
		g1.setTasks(g2.getTasks());
		g1.setUsers(g2.getUsers());
	}
	
	/**
	 * Updates a GroupTask with the info of another
	 * @param gt1 - GroupTask to updated
	 * @param gt2 - GroupTask used to update
	 */
	public static void updateGroupTask(GroupTask gt1, GroupTask gt2)
	{
		gt1.setArchived(gt2.isArchived());
		gt1.setAssignDate(gt2.getAssignDate());
		gt1.setCompletion(gt2.isCompleted());
		gt1.setDueDate(gt2.getDueDate());
		gt1.setGroup(gt2.getGroup());
		gt1.setGroupTaskID(gt2.getGroupTaskID());
		gt1.setGroupTaskName(gt2.getName());
	}
}