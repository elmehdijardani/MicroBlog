package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HashtagRestController {
	@Autowired
	private HashtagDao hashtagDao;

//	@RequestMapping(path = "/hashtags", method = RequestMethod.GET)
//	public List<Hashtag> getAll() {
//		return (List<Hashtag>) hashtagDao.findAll();
//	}

	@RequestMapping(path = "/hashtags/create", method = RequestMethod.POST)
	public String create(@RequestBody Hashtag hashtag) {
		try {
			hashtag.setText(hashtag.getText().substring(1));
			hashtagDao.save(hashtag);
		} catch (Exception ex) {
			return "Error creating the tweet: " + ex.toString();
		}
		return "hashtag succesfully created! (id = " + hashtag.getId() + ")";
	}

	@RequestMapping(path = "/hashtags/{tweetid}", method = RequestMethod.GET)
	public List<Hashtag> getByUser(@PathVariable("tweetid") long tweetid) {
		List<Hashtag> hashtags = new ArrayList<>();
		try {
			hashtags = hashtagDao.findByTweetid(tweetid);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return hashtags;
	}
	
	@RequestMapping(path = "/hashtags", method = RequestMethod.GET)
	public List<Hashtag> getByText(@RequestParam("tag") String tag) {
		List<Hashtag> hashtags = new ArrayList<>();
		try {
			hashtags = hashtagDao.findByText(tag);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return hashtags;
	}
	

}