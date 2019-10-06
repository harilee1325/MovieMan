package com.harilee.movieman.DetailsTv;

import com.harilee.movieman.Details.DetailsResponse;
import com.harilee.movieman.Details.ReviewResponse;
import com.harilee.movieman.Model.SeasonModel;

import java.util.List;

public interface DetailsTvViewInterface {

    void showToast(String message);
    void getTvData(TvDetails tvDetails);
    void getSeason(List<SeasonModel> seasonModel);
    void displayError(String error);
    void getReview(ReviewResponse reviewResponse);
}
