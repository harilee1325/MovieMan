package com.harilee.movieman.Search;

import com.harilee.movieman.Model.MovieModel;
import com.harilee.movieman.Model.TvModel;

import java.util.List;

public interface SearchViewInterface {

    void displayError(String error);
    void getMovieList(List<MovieModel> movieResponse);
    void setView();
    void callApi();
    void getTvList(List<TvModel> tvResponse);
}
