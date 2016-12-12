package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserRestController {
	@Autowired
	private UserDao userDao;

	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> getAll() {
		System.out.println("oyeah!");
		return (List<User>) userDao.findAll();
	}

	@RequestMapping(path = "/users/{username}", method = RequestMethod.GET)
	public User getByName(@PathVariable("username") String username) {
		User user = null;
		try {
			user = userDao.findByUsername(username);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return user;
	}

	@RequestMapping(path = "/users/create", method = RequestMethod.POST)
	public String create(@RequestBody User user) {
		try {
			userDao.save(user);
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		return "User succesfully created! (id = " + user.getId() + ")";
	}

	@RequestMapping(path = "/users/delete", method = RequestMethod.POST)
	public String delete(@RequestParam long id) {
		try {
			User user = new User(id);
			userDao.delete(user);
		} catch (Exception ex) {
			return "Error deleting the user: " + ex.toString();
		}
		return "User succesfully deleted!";
	}

	@RequestMapping(path = "/users/update", method = RequestMethod.POST)
	public String updateUser(@RequestBody User useruptodate) {
		try {
			User user = userDao.findByUsername(useruptodate.getUsername());
			user.setEmail(useruptodate.getEmail());
			user.setPassword(useruptodate.getPassword());
			user.setTwitterid(useruptodate.getTwitterid());
			user.setFacebookid(useruptodate.getFacebookid());
			userDao.save(user);
		} catch (Exception ex) {
			return "Error updating the user: " + ex.toString();
		}
		return "User succesfully updated!";
	}
	
	@RequestMapping(value="/users/uploadphoto", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestBody MultipartFile photo) throws IllegalStateException, IOException{
		System.out.println("upp");
		File file = new File("/" + photo.getOriginalFilename());
        photo.transferTo(file);
		System.out.println("upp2");
        
        return "";
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}