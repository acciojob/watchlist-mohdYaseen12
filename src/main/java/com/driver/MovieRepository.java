package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MovieRepository {
    private Map<String,Movie> movies = new HashMap<>();
    private Map<String,Director> directors = new HashMap<>();
    private Map<String, List<String>> connections = new HashMap<>();
    public void addMovie(Movie movie) {
        movies.put(movie.getName(),movie);
    }

    public void addDirector(Director director) {
        directors.put(director.getName(),director);
    }


    public Optional<Movie> findMovie(String movie) {
        return movies.containsKey(movie) ? Optional.of(movies.get(movie)) : Optional.empty();
    }

    public Optional<Director> findDirector(String director) {
        return directors.containsKey(director) ? Optional.of(directors.get(director)) : Optional.empty();
    }

    public List<String> getAllMoviesByDirector(String director) {
        return connections.getOrDefault(director, new ArrayList<String>());
    }

    public void addDirectorMovie(String director, List<String> list) {
        connections.put(director,list);
    }

    public Optional<Movie> getMovieByName(String name) {
        return movies.containsKey(name) ? Optional.of(movies.get(name)) : Optional.empty();
    }

    public Optional<Director> getDirectorByName(String name) {
        return directors.containsKey(name) ? Optional.of(directors.get(name)) : Optional.empty();

    }

    public List<String> getMoviesByDirector(String director) {
        return connections.getOrDefault(director,new ArrayList<String>());
    }

    public List<String> findAllMovies() {
        List<String> allMovie = new ArrayList<>();
        for(String key : movies.keySet()){
            allMovie.add(key);
        }
        return allMovie;
    }

    public void deleteMovie(String mov) {
        movies.remove(mov);
    }

    public void deleteDirector(String name) {
        directors.remove(name);
        connections.remove(name);
    }

    public List<String> findAllDirector() {
        return new ArrayList<>(directors.keySet());
    }
}
