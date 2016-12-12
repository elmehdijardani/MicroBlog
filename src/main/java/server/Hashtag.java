package server;

import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.jmx.export.annotation.ManagedResource;

@Entity
@Table(name = "hashtags")
public class Hashtag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private String user;
	@NotNull
	private long tweetid;
	@NotNull
	private String text;

	public Hashtag() {
	}

	public Hashtag(long id) {
		this.id = id;
	}

	public Hashtag(String user, long tweetid, String text) {
		this.user = user;
		this.tweetid = tweetid;
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getTweetid() {
		return tweetid;
	}

	public void setTweetid(long tweetid) {
		this.tweetid = tweetid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}