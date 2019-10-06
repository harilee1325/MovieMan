package com.harilee.movieman.MovieList;

import com.harilee.movieman.Model.MovieModel;
import com.harilee.movieman.Model.TvModel;

import java.util.List;

public interface MovieViewInterface {

    void displayError(String error);
    void getMovieList(List<MovieModel> movieResponse);
    void setView();
    void callApi();
    void getTvList(List<TvModel> tvResponse);
}
