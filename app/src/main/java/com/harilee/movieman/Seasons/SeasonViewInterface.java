package com.harilee.movieman.Seasons;

import com.harilee.movieman.Model.EpisodeModel;

import java.util.List;

public interface SeasonViewInterface {

    void displayError(String error);
    void showToast(String msg);
    void getEpisodeData(List<EpisodeModel> seasonResponse);

}
