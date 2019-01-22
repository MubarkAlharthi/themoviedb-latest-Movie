package application;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MovieInfo {

	private String title, poster_Path, over_View, imdb_id;
	private LinkedList<Genre> kind_of_the_movie;
	private int id;
	private double vote_average;
	
	public static List<Genre> genres = new LinkedList<Genre>();
	
	// this method will help to find the name of given id from JESON
	public static String find_genre_Type(int id) {
		
		for(Genre g: genres)
			if(g.getId()==id)
				return g.getName();
		
		return "UnKnown";
	
	}
	
	public MovieInfo(String title, String poster_Path, String over_View, LinkedList<Genre> kind_of_the_movie, int id,
			double vote_average) {
		this.title = title;
		this.poster_Path = poster_Path;
		this.over_View = over_View;
		this.kind_of_the_movie = kind_of_the_movie;
		this.id = id;
		this.vote_average = vote_average;
	}
	
	

	public MovieInfo(String title, String poster_Path, String over_View, String imdb_id,
			LinkedList<Genre> kind_of_the_movie, int id, double vote_average) {
		super();
		this.title = title;
		this.poster_Path = poster_Path;
		this.over_View = over_View;
		this.imdb_id = imdb_id;
		this.kind_of_the_movie = kind_of_the_movie;
		this.id = id;
		this.vote_average = vote_average;
	}

	/*Setters*/
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPoster_Path(String poster_Path) {
		this.poster_Path = poster_Path;
	}
	public void setOver_View(String over_View) {
		this.over_View = over_View;
	}
	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}
	public void setKind_of_the_movie(LinkedList<Genre> kind_of_the_movie) {
		this.kind_of_the_movie = kind_of_the_movie;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setVote_average(double vote_average) {
		this.vote_average = vote_average;
	}
	/*Getters*/
	public String getTitle() {
		return title;
	}
	public String getPoster_Path() {
		return poster_Path;
	}
	public String getOver_View() {
		return over_View;
	}
	public String getImdb_id() {
		return imdb_id;
	}
	public LinkedList<Genre> getKind_of_the_movie() {
		return kind_of_the_movie;
	}
	public int getId() {
		return id;
	}
	public double getVote_average() {
		return vote_average;
	}


	
	
	



}