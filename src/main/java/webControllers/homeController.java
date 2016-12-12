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

import server.Tweet;
import server.User;

@Controller
public class homeController {
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "home", method = RequestMethod.GET)
		String getLatest(Model model) {
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
					tweetCode +="<a class=\"post-category post-category-js\" href=\"hashtag?tag="+String.valueOf(tags.get(i).get("text"))+"\">"+String.valueOf(tags.get(i).get("text"))+"</a>";
				}
				tweetCode+="</p></section>";
				LinkedHashMap<String,String> t = new LinkedHashMap<>();
				t.put("tweet", tweetCode);
				tweetsCode.add(t);
			}
			model.addAttribute("tweets",tweetsCode);		
			
		return "home";
	}
	
	@RequestMapping(value = "home", method = RequestMethod.POST)
	String postTweet(@RequestParam String text, @RequestParam String user, @RequestParam(required=false) int postinf, @RequestParam(required=false) int postint, Model model) {
		Date cd = new Date();
		String current_date = cd.getYear() +","+ cd.getMonth() +","+ cd.getDate() +","+ cd.getHours() +","+ cd.getMinutes() +","+ cd.getSeconds();
		
		Tweet newtweet = new Tweet(user, text, current_date, postinf, postint);
		try {
			restTemplate.postForObject("http://localhost:8080/tweets/create", newtweet, Tweet.class);
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
				tweetCode +="<a class=\"post-category post-category-js\" href=\"hashtag?tag=/"+String.valueOf(tags.get(i).get("text"))+"\">"+String.valueOf(tags.get(i).get("text"))+"</a>";
			}
			tweetCode+="</p></section>";
			LinkedHashMap<String,String> t = new LinkedHashMap<>();
			t.put("tweet", tweetCode);
			tweetsCode.add(t);
		}
		model.addAttribute("tweets",tweetsCode);		
		
//
		
		
		
		model.addAttribute("username",user);
		return "home";
	}
	
	

}
