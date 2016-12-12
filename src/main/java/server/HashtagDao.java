package server;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
public interface HashtagDao extends CrudRepository<Hashtag, Long> {

	public Hashtag findById(long id);

	public List<Hashtag> findByUser(String user);

	public List<Hashtag> findByTweetid(long tweetid);
	
	public List<Hashtag> findByText(String text);


}