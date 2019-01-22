package application;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Http_Request {

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
 * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
          System.out.println("Error with creating URL ");
          
        }
        return url;
    }
	
    
    
    
	/**
	 * Make an HTTP request to the given URL and return a String as the response.
	 */
	public  String makeHttpRequest(URL url) throws IOException {
	    String jsonResponse = "";

	    // If the URL is null, then return early.
	    if (url == null) {
	        return jsonResponse;
	    }

	    HttpURLConnection urlConnection = null;
	    InputStream inputStream = null;
	    try {
	        urlConnection = (HttpURLConnection) url.openConnection();
	        urlConnection.setReadTimeout(10000 /* milliseconds */);
	        urlConnection.setConnectTimeout(15000 /* milliseconds */);
	        urlConnection.setRequestMethod("GET");
	        urlConnection.connect();
	        
	        // If the request was successful (response code 200),
	        // then read the input stream and parse the response.
	        if (urlConnection.getResponseCode() == 200) {
	            inputStream = urlConnection.getInputStream();
	            jsonResponse = readFromStream(inputStream);
	        } else {
	            System.out.println("There was an Error");
	        }
	    } catch (IOException e) {
            System.out.println("There was an Error in file");
	    } finally {
	        if (urlConnection != null) {
	            urlConnection.disconnect();
	        }
	        if (inputStream != null) {
	            inputStream.close();
	        }
	    }
	    return jsonResponse;
	}
	
	/**
	 * Get the Genre id from genres_id JSON and map it to its corresponding name
	 * @param genres_id
	 * @return List of Genres that describe the movie 
	 */
	public  LinkedList<Genre> genres_for_Movie(JSONArray genres_id){
	    	
	    	//in case the movie does not has type from the API
	    	if(genres_id.length()==0)
	    		return null;
	    	
	    	LinkedList<Genre> genres = new LinkedList<Genre>();
	    	
	    	//after this loop will have the id of the type of the movie
	    	for (int i = 0; i < genres_id.length(); i++) {
	    		Genre genre =new Genre();
	    		int genre_id=genres_id.getInt(i);
	    		genre.setId(genre_id);
	    		genres.add(genre);		
	    	}
	    	
	      	// Now we try to find the name of the type that corresponding to the id we got from JSON 
	    	for (int i = 0; i < genres.size(); i++) {
	    		String type = MovieInfo.find_genre_Type(genres.get(i).getId());
	    		genres.get(i).setName(type);
			}
	    	
	    	
	    	return genres;
	      
	    	
	    }
	    		
	 
	 /**
	  * Get all available Genres From API and add it to Genres list in MovieInfo Class
	  * @param genres_JSON
	  */
    private   void extract_genres_FromJson() throws Exception {
    	
    	URL url_genres = createUrl("http://api.themoviedb.org/3/genre/movie/list?api_key=19bc4424ccaa754c5d149f7fcb22630b");
    	String genres_JSON = makeHttpRequest(url_genres);
        // If the JSON string is empty or null, then return early.
        if (genres_JSON.isEmpty()) {
        	return;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(genres_JSON);
            JSONArray genres = baseJsonResponse.getJSONArray("genres");
            
            for(int i=0;i<genres.length();i++) {
            	JSONObject genre_JSONObject = genres.getJSONObject(i);
            	MovieInfo.genres.add(new Genre(genre_JSONObject.getInt("id"), genre_JSONObject.getString("name")));
            }    
            
        } catch (JSONException e) {
            System.out.println("Problem parsing the earthquake JSON results");
        }
    }   
    
    
    /**
     * Extract the movies Details JSON_Response  
     * @param Movie_JSON
     * @return The list of movies in the JSON_Response
     */
    public  LinkedList<MovieInfo> extract_searched_Movie_FromJson(String name) {
        // If the JSON string is empty or null, then return early.
    	LinkedList<MovieInfo> extracted_Movies = new LinkedList<MovieInfo>();
    	String Movie_JSON="";
  
     
        try {
        	URL url_Movie = createUrl("https://api.themoviedb.org/3/search/movie?api_key=19bc4424ccaa754c5d149f7fcb22630b&query="+name);
        	
        	Movie_JSON = makeHttpRequest(url_Movie);
        	  if (Movie_JSON.isEmpty()) {
                  return null;
              }
            JSONObject baseJsonResponse = new JSONObject(Movie_JSON);
            JSONArray movies = baseJsonResponse.getJSONArray("results");
            String poster_Path;// poster_Path  declare hare because sometimes the poster comes from the API equal null  
            	for (int i = 0; i < movies.length(); i++) {
                    JSONObject movie_JSONObject = movies.getJSONObject(i);
                    String title=movie_JSONObject.getString("title");
                    if(movie_JSONObject.isNull("poster_path"))
                        poster_Path="N/A";
                    else
                    	poster_Path=movie_JSONObject.getString("poster_path");
                    String over_View = movie_JSONObject.getString("overview");
                    int id= movie_JSONObject.getInt("id");
                    double vote_average = movie_JSONObject.getDouble("vote_average");
                    //try to get type of the movie if it is exiset
                    LinkedList<Genre> kind_of_the_movie = genres_for_Movie(movie_JSONObject.getJSONArray("genre_ids"));
                    extracted_Movies.add(new MovieInfo(title, poster_Path, over_View, kind_of_the_movie, id, vote_average));       
            		}
                     
            	return extracted_Movies;
            
        } catch (JSONException e) {
            
        	System.out.println(e.getMessage());
        }catch (IOException e) {
			// TODO: handle exception
            System.out.println(e.getMessage());

		}
        return null;
    }
    
   
    /**
     * Extract the movies Details JSON_Response  
     * @param Movie_JSON
     * @return The list of movies in the JSON_Response
     */
    public  LinkedList<MovieInfo> extract_latest_Movie_FromJson() {
        // If the JSON string is empty or null, then return early.
    	LinkedList<MovieInfo> extracted_Movies = new LinkedList<MovieInfo>();
    	String Movie_JSON="";
  
     
        try {
        	URL url_Movie = createUrl("https://api.themoviedb.org/3/discover/movie?api_key=19bc4424ccaa754c5d149f7fcb22630b&page=1&sort_by=release_date.desc&year=2018");
        	Movie_JSON = makeHttpRequest(url_Movie);
        	  if (Movie_JSON.isEmpty()) {
                  return null;
              }
            JSONObject baseJsonResponse = new JSONObject(Movie_JSON);
            JSONArray movies = baseJsonResponse.getJSONArray("results");
            String poster_Path;// poster_Path  declare hare because sometimes the poster comes from the API equal null  
            	for (int i = 0; i < movies.length(); i++) {
                    JSONObject movie_JSONObject = movies.getJSONObject(i);
                    String title=movie_JSONObject.getString("title");
                    if(movie_JSONObject.isNull("poster_path"))
                        poster_Path="N/A";
                    else
                    	poster_Path=movie_JSONObject.getString("poster_path");
                    String over_View = movie_JSONObject.getString("overview");
                    int id= movie_JSONObject.getInt("id");
                    double vote_average = movie_JSONObject.getDouble("vote_average");
                    //try to get type of the movie if it is exiset
                    LinkedList<Genre> kind_of_the_movie = genres_for_Movie(movie_JSONObject.getJSONArray("genre_ids"));
                    extracted_Movies.add(new MovieInfo(title, poster_Path, over_View, kind_of_the_movie, id, vote_average));       
            		}
                     
            	return extracted_Movies;
            
        } catch (JSONException e) {
            
        	System.out.println(e.getMessage());
        }catch (IOException e) {
			// TODO: handle exception
            System.out.println(e.getMessage());

		}
        return null;
    }
    
    
    
    public Http_Request() { 
    	
    	try {
    		// prepare the list of available genres from API 
        	extract_genres_FromJson();
        	
		} catch (Exception e) {
			// TODO: handle exception
			
			System.out.println(e.getMessage());
		}
    	
    	
    	
    } 


}
