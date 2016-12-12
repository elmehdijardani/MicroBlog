package server;

import java.util.ArrayList;
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
@Table(name = "tweets")
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private String user;
	@NotNull
	private String text;
	@NotNull
	private String date;
	private int postinf;
	private int postint;

	public Tweet() {
	}

	public Tweet(long id) {
		this.id = id;
	}

	public Tweet(String user, String text, String date, int postinf, int postint) {
		super();
		this.user = user;
		this.text = text;
		this.date = date;
		this.postinf = postinf;
		this.postint = postint;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPostinf() {
		return postinf;
	}

	public void setPostinf(int postinf) {
		this.postinf = postinf;
	}

	public int getPostint() {
		return postint;
	}

	public void setPostint(int postint) {
		this.postint = postint;
	}

	
	
	@Override
	public String toString() {
		return "Tweet [id=" + id + ", user=" + user + ", text=" + text + ", date=" + date + ", postinf=" + postinf
				+ ", postint=" + postint + "]";
	}

	public List<String> getHashtags(){
		String[] textArray = text.split(" ");
		String word;
		List<String> hashtags = new ArrayList<>();
		for (int i = 0; i < textArray.length; i++) {
			word = textArray[i];
			if(word.startsWith("#"))
				hashtags.add(word);
		}
		return hashtags;
		
	}
	

}