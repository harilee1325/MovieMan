package com.harilee.movieman.Details;

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
import com.harilee.movieman.Model.ReviewModel;
import com.harilee.movieman.R;
import com.harilee.movieman.Utility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailsFragment extends Fragment implements DetailsViewInterface {

    @BindView(R.id.movie_poster)
    ImageView moviePoster;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.scrolling_container)
    NestedScrollView scrollingContainer;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerViewContainer;
    @BindView(R.id.review_list)
    RecyclerView reviewList;
    @BindView(R.id.no_review_text)
    TextView noReviewText;
    private View view;
    private DetailsPresenter detailsPresenter;
    private String movieId;
    private ReviewAdapter reviewAdapter;
    private List<ReviewModel> reviewModelList;
    private String tag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fulldetails_page, container, false);
        ButterKnife.bind(this, view);
        setView();
        callApi();
        return view;
    }

    private void callApi() {

        movieId = getArguments().getString(Config.MOVIE_ID, "hello");
        tag = getArguments().getString(Config.TAG, "hello");
        Log.e(TAG, "callApi: " + movieId);
        detailsPresenter = new DetailsPresenter(this);
        detailsPresenter.getMovieId(movieId);
//        shimmerViewContainer.setVisibility(View.VISIBLE);
//        shimmerViewContainer.startShimmerAnimation();
        detailsPresenter.getMovieData(tag);
        detailsPresenter.getMovieReview(tag);

    }


    private void setView() {

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.theme_black));

        //setting up collapsing toolbar
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
    }

    @Override
    public void showToast(String message) {
        Utility.getUtilityInstance().snackBar(message, view);
    }

    @Override
    public void getMovieData(DetailsResponse detailsResponse) {
        /*shimmerViewContainer.stopShimmerAnimation();
        shimmerViewContainer.setVisibility(View.GONE);
*/
        Glide.with(getContext()).load("https://image.tmdb.org/t/p/w500/" + detailsResponse.getPosterPath())
                .into(moviePoster);
        movieName.setText(detailsResponse.getOriginalTitle());
        movieYear.setText(detailsResponse.getReleaseDate());
        movieRating.setText(detailsResponse.getVoteAverage() + " / " + "10.0");
        movieDescription.setText(detailsResponse.getOverview());
    }

    @Override
    public void getMovieReview(ReviewResponse reviewResponse) {
            Log.e(TAG, "callApi: "+tag );

        reviewModelList = reviewResponse.getResults();
        if (reviewModelList.size() > 0) {
            reviewAdapter = new ReviewAdapter(getContext(), getActivity(), reviewModelList);
            reviewList.setHasFixedSize(true);
            reviewList.setAdapter(reviewAdapter);
        } else {
            noReviewText.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void displayError(String error) {
        showToast(error);
    }

    @Override
    public void onPause() {
        super.onPause();
        /*shimmerViewContainer.stopShimmerAnimation();
        shimmerViewContainer.setVisibility(View.GONE);*/
    }
}
