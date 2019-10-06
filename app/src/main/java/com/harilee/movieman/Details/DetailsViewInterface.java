package com.harilee.movieman.Details;

public interface DetailsViewInterface {

    void showToast(String message);
    void getMovieData(DetailsResponse detailsResponse);
    void getMovieReview(ReviewResponse reviewResponse);
    void displayError(String error);
}
