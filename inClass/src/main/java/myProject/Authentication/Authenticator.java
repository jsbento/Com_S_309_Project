package myProject.Authentication;

import myProject.Groups.*;

import java.util.List;

import myProject.Users.User;

 /*
  *  Authentication tool to prevent duplicate users/groups
  *  @author Jacob Benton (jsbenton), Noah Cantrell (nbc)
  */
public class Authenticator
{
	/**
	 * Authenticates a user
	 * @param u - The user who needs authentication
	 * @param list - A List of all current users in DB
	 */
	public static boolean userAuthenticate(User u, List<User> list)
	{
		for(User stored : list)
			if(u.compareTo(stored) == -1)
				return false;
		return true;
	}
	
	/**
	 * Authenticates a group
	 * @param g - The group which needs authentication
	 * @param list - A List of all current groups in DB
	 */
	public static boolean groupAuthenticate(Group g, List<Group> list)
	{
		for(Group stored : list)
			if(g.compareTo(stored) == -1)
				return false;
		return true;
	}
}