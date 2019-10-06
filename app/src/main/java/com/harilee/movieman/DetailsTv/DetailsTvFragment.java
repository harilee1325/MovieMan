package com.harilee.movieman.DetailsTv;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.harilee.movieman.Config;
import com.harilee.movieman.Details.ReviewAdapter;
import com.harilee.movieman.Details.ReviewResponse;
import com.harilee.movieman.Model.ReviewModel;
import com.harilee.movieman.Model.SeasonModel;
import com.harilee.movieman.R;
import com.harilee.movieman.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailsTvFragment extends Fragment implements DetailsTvViewInterface {

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerViewContainer;
    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.movie_name)
    TextView movieName;
    @BindView(R.id.movie_year)
    TextView movieYear;
    @BindView(R.id.movie_rating)
    TextView movieRating;
    @BindView(R.id.movie_description)
    TextView movieDescription;
    @BindView(R.id.no_review_text)
    TextView noReviewText;
    @BindView(R.id.season_list)
    RecyclerView seasonList;
    @BindView(R.id.scrolling_container)
    NestedScrollView scrollingContainer;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.review_list)
    RecyclerView reviewList;
    private View view;
    private DetailsTvPresenter detailsTvPresenter;
    private SeasonAdapter seasonAdapter;
    private List<SeasonModel> seasonModels = new ArrayList<>();
    private ReviewAdapter reviewAdapter;
    private List<ReviewModel> reviewModelList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tv_details_page, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    private void setView() {

        String tvId = getArguments().getString(Config.MOVIE_ID, "hello");
        Log.e(TAG, "setView: " + tvId);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.theme_black));

        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        collapsingToolbar.setTitle("MovieMan");
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);

        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } else {
            // Don't inflate. Tablet is in landscape mode.
        }

        seasonList.setHasFixedSize(true);
        seasonAdapter = new SeasonAdapter(getContext(), getActivity(), seasonModels);
        seasonList.setAdapter(seasonAdapter);
        reviewList.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(getContext(), getActivity(), reviewModelList);
        reviewList.setAdapter(reviewAdapter);
        detailsTvPresenter = new DetailsTvPresenter(this);
        detailsTvPresenter.getTvId(tvId);
        detailsTvPresenter.getTvData();
        detailsTvPresenter.getReview();

    }

    @Override
    public void showToast(String message) {
        Utility.getUtilityInstance().snackBar(message, view);
    }

    @Override
    public void getTvData(TvDetails tvDetails) {

        if (tvDetails != null) {
            Glide.with(getContext()).load("https://image.tmdb.org/t/p/w500/" + tvDetails.getPosterPath())
                    .into(moviePoster);
            movieName.setText(tvDetails.getOriginalName());
            movieDescription.setText(tvDetails.getOverview());
            movieRating.setText(tvDetails.getVoteAverage() + " / " + "10.0");
            movieYear.setText(tvDetails.getFirstAirDate());
        }

    }

    @Override
    public void getSeason(List<SeasonModel> seasonModel) {

        if (seasonModel.size() > 0) {
            this.seasonModels.addAll(seasonModel);
            seasonAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void displayError(String error) {
        showToast(error);
    }

    @Override
    public void getReview(ReviewResponse reviewResponse) {

        if (reviewResponse != null){

            this.reviewModelList.addAll(reviewResponse.getResults());
            if (reviewModelList.size()>0){
                reviewAdapter.notifyDataSetChanged();
            }else {
                reviewList.setVisibility(View.GONE);
                noReviewText.setVisibility(View.VISIBLE);
            }
        }
    }
}
