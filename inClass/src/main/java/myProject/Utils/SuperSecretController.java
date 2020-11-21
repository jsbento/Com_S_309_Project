package myProject.Utils;

import org.springframework.web.bind.annotation.*;

/**
 * Super secret easter egg
 * @author Jacob Benton (jsbenton)
 *
 */

@RestController
public class SuperSecretController
{
	/**
	 * Get the secret :')
	 * @return a special message
	 */
	@GetMapping("/secret")
	String getSecret()
	{
		return "We did it! I couldn't be happier with the project!\n"
			   +"Thanks Ethan, Noah, and Beattie for being awesome teammates.\n"
			   +"Good luck on your future endeavors guys!\n"
			   +"-Benton";
	}
}