package application;

import java.net.URL; 
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.security.auth.callback.Callback;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;

public class MainViewController  implements Initializable {
	
		private List<MovieInfo> movies;
		private Http_Request http_Request= new Http_Request();

	 @FXML
	    private AnchorPane MovieDetails;
	 
	   @FXML
	    private AnchorPane MovieD2;

	    @FXML
	    private Button Delete_from_My_Favorites;

	    @FXML
	    private Button Show_Search_Pane_Button;

	    @FXML
	    private Button Show_Latest_Movie_Button;

	    @FXML
	    private Button Add_To_My_Favorites;

	    @FXML
	    private AnchorPane work_pane;

	    @FXML
	    private Text Latest_Movie_Lable;
	    
	    @FXML
	    private ListView<MovieInfo> movielist1;

	    @FXML
	    private ListView<MovieInfo> movielist;

	    @FXML
	    private AnchorPane Search_Pane;

	    @FXML
	    private TextField Name_text_Field;

	    @FXML
	    private Button Search_Api;

	    @FXML
	    private SplitPane Favorite_And_Details;

	    @FXML
	    private Text MyFavoritesLable;

	    @FXML
	    private ListView<MovieInfo> favorites_movies;

	    @FXML
	    private Text MovieDetailsLable;

	    @FXML
	    private Text Title;

	    @FXML
	    private Text overview;

	    @FXML
	    private Text type;

	    @FXML
	    private ImageView imageView;
	    
	    @FXML
	    private TextArea textArea;
	    
	    @FXML
	    private AnchorPane Datails2;

	    @FXML
	    private Text MovieDetailsLable2;

	    @FXML
	    private Text Title2;

	    @FXML
	    private Text overview2;

	    @FXML
	    private Text type2;

	    @FXML
	    private ImageView imageView2;

	    @FXML
	    private TextArea textArea2;

	    
	    
	    @FXML
	    void Add_My_Favorites(ActionEvent event) {
	    	
            ObservableList<MovieInfo> selectedIndices = movielist.getSelectionModel().getSelectedItems();
            		//.getSelectionModel().getSelectedIndices();
             
            for(MovieInfo m :selectedIndices) {
            
            	if(!favorites_movies.getItems().contains(m))
            		favorites_movies.getItems().add(m);
            }
            favorites_movies.setCellFactory(param -> new ListCell<MovieInfo>() {
                private ImageView imageView = new ImageView();
                @Override
                public void updateItem(MovieInfo movie, boolean empty) {
                    super.updateItem(movie, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                    	//System.out.println(movie.getTitle());
                    	if(movie.getPoster_Path()!=null) {
                    		imageView.setImage(new Image("https://image.tmdb.org/t/p/w200/"+movie.getPoster_Path(),150,200,false,false));
       	                 	//imageView.resize(100, 100);

                    	}
                        setGraphic(imageView);
                    }
                } 
            });
   
            	
            

	     }

	    @FXML
	    void Delete_My_Favorites(ActionEvent event) {
	    	
            int selectedIndex = favorites_movies.getSelectionModel().getSelectedIndex();
            if(selectedIndex!=-1)
            	favorites_movies.getItems().remove(selectedIndex);
	    }

	    @FXML
	    void Latest_Movie_Action(ActionEvent event) {
	    	work_pane.setVisible(true);
	    	movielist.setVisible(true);
			Favorite_And_Details.setVisible(true);
			Show_Search_Pane_Button.setVisible(true);
	    	Latest_Movie_Lable.setVisible(true);
			Add_To_My_Favorites.setVisible(true);
			Delete_from_My_Favorites.setVisible(true);
	    	Search_Pane.setVisible(false);
	    	Datails2.setVisible(false);
	    	movielist1.setVisible(false);
			Show_Latest_Movie_Button.setVisible(false);

			prepareMyList();
	    }

	    @FXML
	    void Search_in_API(ActionEvent event) {
			movielist1.setVisible(true);
			Datails2.setVisible(false);
	    	if(!Name_text_Field.getText().isEmpty()) {
	    		prepareMyList(Name_text_Field.getText());
	    	
	    	}
	    	
	    }

	    @FXML
	    void Show_Search_Pane(ActionEvent event) {
	    	work_pane.setVisible(true);
			Show_Latest_Movie_Button.setVisible(true);
	    	Datails2.setVisible(false);
	    	Search_Pane.setVisible(true);
	    	Add_To_My_Favorites.setVisible(false);
			Delete_from_My_Favorites.setVisible(false);
			Favorite_And_Details.setVisible(false);
			movielist.setVisible(false);
			movielist1.setVisible(false);
			Show_Search_Pane_Button.setVisible(false);
			Latest_Movie_Lable.setVisible(false);

    	  }
	    

	    // this method when Mouse pressed on latest movies is clicked 

	    @FXML
	    void getDetails(MouseEvent event) {
	    	System.out.println("is cliced");
	    	   ObservableList<MovieInfo> selectedIndices = movielist.getSelectionModel().getSelectedItems();
           	
	            for(MovieInfo m :selectedIndices) {
	                 Title.setText(m.getTitle());
	                 if(!m.getOver_View().isEmpty())
	                	 textArea.setText(m.getOver_View());
	                 else 
	                	 textArea.setText("Not Available.");
					
	                 String types="";
	                 
	                 if(m.getKind_of_the_movie()==null)
		                 type.setText("Not Available.");
	                 else {
	                	 for(Genre g: m.getKind_of_the_movie())
	                		 types+=g.getName()+", ";
                		types=types.substring(0, types.length()-2);
	                 	type.setText(types+".");

	                 }
	                 imageView.setImage(new Image("https://image.tmdb.org/t/p/w200/"+m.getPoster_Path()));

	                 
	            }
	    }
	    
	    // this method when Mouse pressed on Button Search is clicked 
	    @FXML
	    void getDetails2(MouseEvent event) {
	    	System.out.println("is cliced");
	    	Datails2.setVisible(true);

	    	   ObservableList<MovieInfo> selectedIndices = movielist1.getSelectionModel().getSelectedItems();
           	
	            for(MovieInfo m :selectedIndices) {
	                 Title2.setText(m.getTitle());	                 
	                 if(!m.getOver_View().isEmpty())
	                	 textArea2.setText(m.getOver_View());
	                 else 
	                	 textArea2.setText("Not Available.");
	                 String types="";
	                 
	                 if(m.getKind_of_the_movie()==null)
		                 type2.setText("Not Available.");
	                 else {
	                	 for(Genre g: m.getKind_of_the_movie())
	                		 types+=g.getName()+", ";
                		types=types.substring(0, types.length()-2);
	                 	type2.setText(types+".");

	                 }
	                 imageView2.setImage(new Image("https://image.tmdb.org/t/p/w200/"+m.getPoster_Path()));

	                 
	            }
	    }
	    
	    // this method when Mouse pressed on favorites movies is clicked 

	    @FXML
	    void getDetails3(MouseEvent event) {
	    	System.out.println("is cliced2");
	    	Datails2.setVisible(true);

	    	   ObservableList<MovieInfo> selectedIndices = favorites_movies.getSelectionModel().getSelectedItems();
           	
	    	   
	            for(MovieInfo m :selectedIndices) {
	                 Title.setText(m.getTitle());
	                 if(!m.getOver_View().isEmpty())
	                	 textArea.setText(m.getOver_View());
	                 else 
	                	 textArea.setText("Not Available.");
	                 String types="";
	                 if(m.getKind_of_the_movie()==null)
		                 type.setText("Not Available.");
	                 else {
	                	 for(Genre g: m.getKind_of_the_movie())
	                		 types+=g.getName()+", ";
                		types=types.substring(0, types.length()-2);
	                 	type.setText(types+".");

	                 }
	                 imageView.setImage(new Image("https://image.tmdb.org/t/p/w200/"+m.getPoster_Path()));

	                 
	            }
	    }
	    


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		work_pane.setVisible(false);
		Add_To_My_Favorites.setVisible(false);
		Delete_from_My_Favorites.setVisible(false);		
    	movielist.setVisible(false);
		movielist1.setVisible(false);
		favorites_movies.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    	

	}
    
    private void prepareMyList(){
    	
    	movielist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	movies = new ArrayList<MovieInfo>(30);
    	movies.addAll(http_Request.extract_latest_Movie_FromJson());
    	ObservableList<MovieInfo> myObservableList = FXCollections.observableList(movies);
    	movielist.setItems(myObservableList);
    	movielist.setCellFactory(param -> new ListCell<MovieInfo>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(MovieInfo movie, boolean empty) {
                super.updateItem(movie, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                	//System.out.println(movie.getTitle());
                	if(movie.getPoster_Path()!=null)
                		imageView.setImage(new Image("https://image.tmdb.org/t/p/w200/"+movie.getPoster_Path()));
                    setGraphic(imageView);
                }
            }
        });
    }    
//    	
        private void prepareMyList(String name){
        	
        	movielist1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        	movies = new ArrayList<MovieInfo>(30);
        	movies.addAll(http_Request.extract_searched_Movie_FromJson(name));
        	ObservableList<MovieInfo> myObservableList = FXCollections.observableList(movies);
        	movielist1.setItems(myObservableList);
        	movielist1.setCellFactory(param -> new ListCell<MovieInfo>() {
                private ImageView imageView = new ImageView();
                @Override
                public void updateItem(MovieInfo movie, boolean empty) {
                    super.updateItem(movie, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                    	//System.out.println(movie.getTitle());
                    	if(movie.getPoster_Path()!=null)
                    		imageView.setImage(new Image("https://image.tmdb.org/t/p/w200/"+movie.getPoster_Path()));
                        setGraphic(imageView);
                    }
                }
            });
    	
    }



}
