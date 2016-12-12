package webControllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.Hashtag;
import server.Tweet;
import server.User;

@Controller
public class indexController {
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "index", method = RequestMethod.GET)
	String getLatestTweets(Model model) {
		List<LinkedHashMap<String, String>> tweets = restTemplate.getForObject("http://localhost:8080/tweets", List.class);
//		model.addAttribute("tweets", tweets);
	
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

		return "index";
	}
	
	
	
	

}
