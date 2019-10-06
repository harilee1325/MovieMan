package com.harilee.movieman.Seasons;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.harilee.movieman.Config;
import com.harilee.movieman.Model.EpisodeModel;
import com.harilee.movieman.Model.SeasonModel;
import com.harilee.movieman.R;
import com.harilee.movieman.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SeasonFragment extends Fragment implements SeasonViewInterface {

    private static final String TAG = "Season" ;
    @BindView(R.id.filter_back)
    ImageView filterBack;
    @BindView(R.id.toolbar_list)
    Toolbar toolbarList;
    @BindView(R.id.season_details_list)
    RecyclerView seasonDetailsList;
    private View view;
    private EpisodeAdapter episodeAdapter;
    private SeasonPresenter seasonPresenter;
    private String tvId, seasonNum;
    private List<EpisodeModel> seasonModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.season_details, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    private void setView() {

        tvId = getArguments().getString(Config.TV_ID, "hello");
        seasonNum = getArguments().getString(Config.SEASON_NUM, "hello");
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.purple));
        seasonDetailsList.setHasFixedSize(true);
        episodeAdapter = new EpisodeAdapter(getContext(), getActivity(), seasonModels);
        seasonDetailsList.setAdapter(episodeAdapter);
        seasonPresenter = new SeasonPresenter(this);
        seasonPresenter.getSeasonData(tvId, seasonNum);
        seasonPresenter.getEpisodes();


    }

    @OnClick(R.id.filter_back)
    public void onViewClicked() {
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void displayError(String error) {
        showToast(error);
    }

    @Override
    public void showToast(String msg) {
        Utility.getUtilityInstance().snackBar(msg, view);
    }

    @Override
    public void getEpisodeData(List<EpisodeModel> seasonResponse) {

        if (seasonResponse.size()>0){
            Log.e(TAG, "getEpisodeData: "+seasonResponse.size() );
            this.seasonModels.addAll(seasonResponse);
            episodeAdapter.notifyDataSetChanged();

        }

    }
}
