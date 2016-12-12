package webControllers;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.User;

@Controller
public class ProfileController {
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "profile", method = RequestMethod.GET)
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
			//
			Map<String, String> param = new HashMap<>();
			param.put("username", username);
			List<LinkedHashMap<String, String>> tweets = restTemplate.getForObject("http://localhost:8080/tweets/{username}", List.class, param);
		
			List<LinkedHashMap<String, String>> tweetsCode = new ArrayList<>();
			
			
			for (LinkedHashMap<String, String> tweet : tweets) {
				String tweetCode = " <section class=\"post\"><img class=\"post-avatar\" alt=\"Yoda&#x27;s avatar\" height=\"48\" width=\"48\" src=\"http://localhost:8080/photos/"+String.valueOf(tweet.get("user"))+".jpg\"/><div class=\"post-description\"><p>";
				Map<String, Long> params2 = new HashMap<>();
				params2.put("tweetid", Long.parseLong(String.valueOf(tweet.get("id"))));
				tweetCode+=String.valueOf(tweet.get("text"))+"</p></div><p class=\"post-meta\">"+String.valueOf(tweet.get("date"))+
						" By <a class=\"post-author\" href=\"profile?username="+String.valueOf(tweet.get("user"))+"\">"+String.valueOf(tweet.get("user"))+"</a> under ";
				List<LinkedHashMap<String, String>> tags = restTemplate.getForObject("http://localhost:8080/hashtags/{tweetid}", List.class, params2);
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
			model.addAttribute("user", user);
			return "profile";
		}
		return "home";
	}

//	@RequestMapping(value = "profile", method = RequestMethod.GET)
//	String showuser() {
//
//		return "profile";
//	}

}
