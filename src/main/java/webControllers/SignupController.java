package webControllers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.User;

@Controller
public class SignupController {
	@RequestMapping("/")
	@ResponseBody
	String emptypage(Model model) {
		// RestTemplate restTemplate = new RestTemplate();
		// Map<String, String> params = new HashMap<>();
		// params.put("email", "wwww");
		// List<User> users = (List<User>)
		// restTemplate.getForObject("http://localhost:8080/users", List.class);
		//
		//
		// model.addAttribute("users",users);
		return "enpty page";
	}

	@RequestMapping(value = "signup", method = RequestMethod.GET)
	String addUser() {
		return "signup";
	}

	@RequestMapping(value = "signup", method = RequestMethod.POST)
	String addUser(@RequestParam("photo") MultipartFile photo, @RequestParam String username, @RequestParam String password, @RequestParam String email,
			@RequestParam String facebookid, @RequestParam String twitterid, Model model) {
		User user = new User(username, password, email, facebookid, twitterid);
		RestTemplate restTemplate = new RestTemplate();
		
        try {
            String uploadingdir = System.getProperty("user.dir") + "/target/classes/static/photos/";

    		File file = new File(uploadingdir + username+".jpg");
//        	System.out.println("upp"+file.getAbsolutePath());
			photo.transferTo(file);

		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			restTemplate.postForObject("http://localhost:8080/users/create", user, User.class);
		} catch (HttpMessageNotReadableException e) {
			// e.printStackTrace();
		}
		
		
		return "login";

	}

}
