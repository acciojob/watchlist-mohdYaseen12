package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie){
        movieService.addMovie(movie);
        return new ResponseEntity<>("Movie Added Successfully", HttpStatus.CREATED);
    }

    @PostMapping("/add-director")
    public ResponseEntity<String> addDirector(@RequestBody Director director){
        movieService.addDirector(director);
        return new ResponseEntity<>("Director Added Successfully",HttpStatus.CREATED);
    }

    @PutMapping("/add-movie-director-pair")
    public ResponseEntity<String> addMovieDirectorPair(@RequestParam String movie, @RequestParam String director) throws NotFoundException{
        try{
        movieService.addMovieDirectorPair(movie,director);
        return new ResponseEntity<>("Pair Added Successfully", HttpStatus.CREATED);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-movie-by-name/{name}")
    public ResponseEntity<Movie> getMovieByName(@PathVariable String name){
        try{
            Movie movie = movieService.getMovieByName(name);
            return new ResponseEntity<>(movie,HttpStatus.OK);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-director-by-name/{name}")
    public ResponseEntity<Director> getDirectorByName(@PathVariable String name){
        try{
            Director director = movieService.getDirectorByName(name);
            return new ResponseEntity<>(director,HttpStatus.OK);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-movies-by-director-name/{director}")
    public ResponseEntity<List<String>> getMoviesByDirectorName(@PathVariable String director){
        try{
            List<String> movieByDirector = movieService.getMoviesByDirectorName(director);
            return new ResponseEntity<>(movieByDirector,HttpStatus.OK);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-movies")
    public ResponseEntity<List<String>> findAllMovies(){
        List<String> allMovie = movieService.findAllMovies();
        if(allMovie.isEmpty())
            throw new NotFoundException("No Movies are PRESENT");
        return new ResponseEntity<>(allMovie, HttpStatus.OK);
    }

    @DeleteMapping("/delete-director-by-name")
    public ResponseEntity<String> deleteDirectorByName(@RequestParam String name){
       movieService.deleteDirectorByName(name);
       return new ResponseEntity<>(name + "And his all movies deleted", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-all-directors")
    public ResponseEntity<String> deleteAllDirectors(){
        movieService.deleteAllDirectors();
        return new ResponseEntity<>("All directors and their movies have been deletd ",HttpStatus.ACCEPTED);
    }

}
