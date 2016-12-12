package webControllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.Tweet;
import server.User;

@Controller
public class editprofileControler {
	RestTemplate restTemplate = new RestTemplate();


	@RequestMapping(value = "editprofile", method = RequestMethod.GET)
	String getUser(@RequestParam String username, Model model) {

		User user = new User();
		Map<String, String> params = new HashMap<>();
		params.put("username", username);
		try {
			user = restTemplate.getForObject("http://localhost:8080/users/{username}", User.class, params);
		} catch (HttpMessageNotReadableException e) {
			// e.printStackTrace();
		}
		if(user!=null){
			model.addAttribute("user", user);
			return "editprofile";
		}
		return "login";
	}

	@RequestMapping(value = "editprofile", method = RequestMethod.POST)
	String addUser(@RequestParam("photo") MultipartFile photo,@RequestParam String username, @RequestParam String password, @RequestParam String email,
			@RequestParam String facebookid, @RequestParam String twitterid,Model model) {
		User user = new User(username, password, email, facebookid, twitterid);
		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.postForObject("http://localhost:8080/users/update", user, User.class);
		} catch (HttpMessageNotReadableException e) {
			// e.printStackTrace();
		}
		//
		List<LinkedHashMap<String, String>> tweets = restTemplate.getForObject("http://localhost:8080/tweets", List.class);
	
		List<LinkedHashMap<String, String>> tweetsCode = new ArrayList<>();
		
		
		for (LinkedHashMap<String, String> tweet : tweets) {
			String tweetCode = " <section class=\"post\"><img class=\"post-avatar\" alt=\"Yoda&#x27;s avatar\" height=\"48\" width=\"48\" src=\"http://localhost:8080/photos/"+String.valueOf(tweet.get("user"))+".jpg\"/><div class=\"post-description\"><p>";
			Map<String, Long> params = new HashMap<>();
			params.put("tweetid", Long.parseLong(String.valueOf(tweet.get("id"))));
			tweetCode+=String.valueOf(tweet.get("text"))+"</p></div><p class=\"post-meta\">"+String.valueOf(tweet.get("date"))+
					" By <a class=\"post-author\" href=\"profile?username="+String.valueOf(tweet.get("user"))+"\">"+String.valueOf(tweet.get("user"))+"</a> under ";
			List<LinkedHashMap<String, String>> tags = restTemplate.getForObject("http://localhost:8080/hashtags/{tweetid}", List.class, params);
			for (int i = 0; i < tags.size(); i++) {
				System.out.println(String.valueOf(tags.get(i).get("text")));
				tweetCode +="<a class=\"post-category post-category-js\" href=\"hashtags/"+String.valueOf(tags.get(i).get("text"))+"\">"+String.valueOf(tags.get(i).get("text"))+"</a>";
			}
			tweetCode+="</p></section>";
			LinkedHashMap<String,String> t = new LinkedHashMap<>();
			t.put("tweet", tweetCode);
			tweetsCode.add(t);
		}
		model.addAttribute("tweets",tweetsCode);		
		
//
		 try {
	            String uploadingdir = System.getProperty("user.dir") + "/target/classes/static/photos/";

	    		File file = new File(uploadingdir + username+".jpg");
//	        	System.out.println("upp"+file.getAbsolutePath());
	    		file.delete();
				photo.transferTo(file);

			} catch (IllegalStateException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 
		model.addAttribute("username",user.getUsername());
		return "home";

	}

	

}
