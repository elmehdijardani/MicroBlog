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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweetRestController {
	@Autowired
	private TweetDao tweetDao;
	@Autowired
	private HashtagDao hashtagDao;

	@RequestMapping(path = "/tweets", method = RequestMethod.GET)
	public List<Tweet> getAll() {
		return (List<Tweet>) tweetDao.findAll();
	}

	@RequestMapping(path = "/tweets/create", method = RequestMethod.POST)
	public String create(@RequestBody Tweet tweet) {
		try {

			tweetDao.save(tweet);
			for (String tag : tweet.getHashtags()) {
				Hashtag h = new Hashtag(tweet.getUser(), tweet.getId(), tag);
				hashtagDao.save(h);
			}
		} catch (Exception ex) {
			return "Error creating the tweet: " + ex.toString();
		}
		return "tweet succesfully created! (id = " + tweet.getId() + ")";
	}

	@RequestMapping(path = "/tweets/{username}", method = RequestMethod.GET)
	public List<Tweet> getByUser(@PathVariable("username") String username) {
		List<Tweet> tweets = new ArrayList<>();
		try {
			tweets = tweetDao.findByUser(username);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return tweets;
	}

}