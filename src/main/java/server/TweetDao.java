package server;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
public interface TweetDao extends CrudRepository<Tweet, Long> {

	public Tweet findById(long id);

	public List<Tweet> findByUser(String user);

}