package com.harilee.movieman.MovieList;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.harilee.movieman.Home.HomeFragment;
import com.harilee.movieman.Model.MovieModel;
import com.harilee.movieman.Model.TvModel;
import com.harilee.movieman.R;
import com.harilee.movieman.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MovieFragment extends Fragment implements MovieViewInterface {


    @BindView(R.id.filter_back)
    ImageView filterBack;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.coordinator_layout)
    ConstraintLayout coordinatorLayout;
    private ShimmerFrameLayout shimmerViewContainer;
    private View view;
    private List<MovieModel> movieModels = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private MoviePresenter moviePresenter;
    private String TAG = "MovieList";
    private Unbinder unbinder;
    private Dialog dialog;
    private String tag;
    private List<TvModel> tvModels = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        setView();
        return view;
    }

    public void setView() {
        shimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.purple));

        tag = getTag();
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    displayError("Loading.....");
                    moviePresenter.getPageNumber(tag);
                }
            }
        });

        //setting the recycler view
        list.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(getContext(), getActivity(), movieModels, tvModels,new MovieFragment(), tag);
        list.setAdapter(movieAdapter);
        callApi();
        dialog = new Dialog(getContext());
    }

    public void callApi() {
        moviePresenter = new MoviePresenter(this);
        shimmerViewContainer.startShimmerAnimation();
        shimmerViewContainer.setVisibility(View.VISIBLE);

        if (tag.equalsIgnoreCase("movie")) {
            Log.e(TAG, "callApi: "+tag );
            moviePresenter.getMovieList();
        }else {
            Log.e(TAG, "callApi: "+tag );
            moviePresenter.getTvList();
        }

    }

    @Override
    public void getTvList(List<TvModel> tvResponse) {
        shimmerViewContainer.stopShimmerAnimation();
        shimmerViewContainer.setVisibility(View.GONE);
        if (tvResponse != null) {
            Log.e(TAG, "getMovieList: " + tvResponse.size());

            this.tvModels.addAll(tvResponse);
            movieAdapter.notifyDataSetChanged();
        } else {
            displayError("Could not get the Tv shows please check your internet connection and try again");
        }
    }

    @Override
    public void displayError(String error) {
        shimmerViewContainer.stopShimmerAnimation();
        shimmerViewContainer.setVisibility(View.GONE);
        // Utility.getUtilityInstance().showGifPopup(getContext(), false, dialog, "");
        Utility.getUtilityInstance().snackBar(error, list);
    }


    @Override
    public void getMovieList(List<MovieModel> movieResponse) {
        shimmerViewContainer.stopShimmerAnimation();
        shimmerViewContainer.setVisibility(View.GONE);
        if (movieResponse != null) {
            Log.e(TAG, "getMovieList: " + movieResponse.size());

            this.movieModels.addAll(movieResponse);
            movieAdapter.notifyDataSetChanged();
        } else {
            displayError("Could not get the movies please check your internet connection and try again");
        }


    }

    @OnClick(R.id.filter_back)
    public void onViewClicked() {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame
                , new HomeFragment(), "Movie").addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerViewContainer.stopShimmerAnimation();
        shimmerViewContainer.setVisibility(View.GONE);
    }


}
