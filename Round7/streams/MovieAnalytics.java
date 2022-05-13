import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MovieAnalytics{

    ArrayList<Movie> movies;

    public MovieAnalytics(){
        movies = new ArrayList<Movie>();
    }

    public void populateWithData(String filename) throws FileNotFoundException, IOException{
        try(var file = new BufferedReader(new FileReader(filename))) {
            String line;

            while((line = file.readLine()) != null){
                String[]split = line.split(";");

                String title    = split[0];
                int releaseYear = Integer.parseInt(split[1]);
                int duration    = Integer.parseInt(split[2]);
                String genre    = split[3];
                double score    = Double.parseDouble(split[4]);
                String director = split[5];

                Movie movie = new Movie(title, releaseYear, duration, genre, score, director);
                movies.add(movie);
            }
        }
    }

    public static Consumer<Movie> showInfo(){
        Consumer<Movie> consumer = (Movie t) -> 
        System.out.printf("%s (By %s, %d)\n",t.getTitle(),t.getDirector(),t.getReleaseYear());
        return consumer;
    }

    public Stream<Movie> moviesAfter(int year){
        ArrayList<Movie>returnedMovies = new ArrayList<>();
        for(int i = 0; i < movies.size(); i++){
            if(movies.get(i).getReleaseYear() >= year){
                returnedMovies.add(movies.get(i));
            }
        }
        Collections.sort(returnedMovies,(o1, o2) ->{
            if(o1.getReleaseYear() ==  o2.getReleaseYear()){
                return o1.getTitle().compareTo(o2.getTitle());
            }
            else{return Integer.compare(o1.getReleaseYear(), o2.getReleaseYear());}
        });
        return returnedMovies.stream();
    }

    public Stream<Movie> moviesBefore(int year){
        ArrayList<Movie>returnedMovies = new ArrayList<>();
        for(int i = 0; i < movies.size(); i++){
            if(movies.get(i).getReleaseYear() <= year){
                returnedMovies.add(movies.get(i));
            }
        }
        Collections.sort(returnedMovies,(o1, o2) ->{
            if(o1.getReleaseYear() ==  o2.getReleaseYear()){
                return o1.getTitle().compareTo(o2.getTitle());
            }
            else{return Integer.compare(o1.getReleaseYear(), o2.getReleaseYear());}
        });
        return returnedMovies.stream();
    }

    public Stream<Movie> moviesBetween(int yearA, int yearB){
        ArrayList<Movie>returnedMovies = new ArrayList<>();
        for(int i = 0; i < movies.size(); i++){
            if(movies.get(i).getReleaseYear() <= yearB && movies.get(i).getReleaseYear() >= yearA){
                returnedMovies.add(movies.get(i));
            }
        }
        Collections.sort(returnedMovies,(o1, o2) ->{
            if(o1.getReleaseYear() ==  o2.getReleaseYear()){
                return o1.getTitle().compareTo(o2.getTitle());
            }
            else{return Integer.compare(o1.getReleaseYear(), o2.getReleaseYear());}
        });
        return returnedMovies.stream();
    }

    public Stream<Movie> moviesByDirector(String director){
        ArrayList<Movie>returnedMovies = new ArrayList<>();
        for(int i = 0; i < movies.size(); i++){
            if(movies.get(i).getDirector().equals(director)){
                returnedMovies.add(movies.get(i));
            }
        }
        Collections.sort(returnedMovies,(o1, o2) ->{
            if(o1.getReleaseYear() ==  o2.getReleaseYear()){
                return o1.getTitle().compareTo(o2.getTitle());
            }
            else{return Integer.compare(o1.getReleaseYear(), o2.getReleaseYear());}
        });
        return returnedMovies.stream();
    }

}