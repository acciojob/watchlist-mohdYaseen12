package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    public void addMovie(Movie movie) {
        movieRepository.addMovie(movie);
    }

    public void addDirector(Director director) {
        movieRepository.addDirector(director);
    }

    public void addMovieDirectorPair(String movie, String director) throws NotFoundException {
        Optional<Movie> movieFound = movieRepository.findMovie(movie);
        Optional<Director> directorFound = movieRepository.findDirector(director);

        if(movieFound.isEmpty() || directorFound.isEmpty()){
            throw new NotFoundException("Entities Not Present");
        }
        List<String> list = movieRepository.getAllMoviesByDirector(director);
        list.add(movie);
        movieRepository.addDirectorMovie(director,list);
        Director dir = directorFound.get();
        dir.setNumberOfMovies(dir.getNumberOfMovies()+1);
//        movieRepository.addMovieDirectorPair(movie,director);
    }

    public Movie getMovieByName(String name) throws NotFoundException {
        Optional<Movie> movie = movieRepository.getMovieByName(name);
        if(movie.isEmpty())
            throw new NotFoundException("The requested movie is not Present");
        return movie.get();
    }

    public Director getDirectorByName(String name) throws NotFoundException {
        Optional<Director> director = movieRepository.getDirectorByName(name);
        if(director.isEmpty())
            throw new NotFoundException("The requested director is NOT PRESENT");
        return director.get();
    }

    public List<String> getMoviesByDirectorName(String director) throws NotFoundException {
        List<String> list = movieRepository.getMoviesByDirector(director);
        if(list.isEmpty())
            throw new NotFoundException("NOT PRESENT");
        return list;
    }

    public List<String> findAllMovies() {
        return movieRepository.findAllMovies();
    }

    public void deleteDirectorByName(String name) {
        List<String> movie = getMoviesByDirectorName(name);

        for(String mov : movie){
            movieRepository.deleteMovie(mov);
        }
        movieRepository.deleteDirector(name);
    }

    public void deleteAllDirectors() {
        List<String> directors = movieRepository.findAllDirector();
        for(String director : directors){
            this.deleteDirectorByName(director);
        }

    }
}
