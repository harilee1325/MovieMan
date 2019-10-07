package com.harilee.movieman.Filter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.harilee.movieman.Config;
import com.harilee.movieman.Model.MovieModel;
import com.harilee.movieman.Model.TvModel;
import com.harilee.movieman.R;
import com.harilee.movieman.Search.SearchAdapter;
import com.harilee.movieman.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FilterFragment extends Fragment implements FilterViewInterface {


    @BindView(R.id.filter_back)
    ImageView filterBack;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.filter_title)
    TextView filterTitle;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerViewContainer;
    private View view;
    private FilterPresenter filterPresenter;
    private String tag;
    private SearchAdapter searchAdapter;
    private List<MovieModel> movieModels = new ArrayList<>();
    private List<TvModel> tvModels = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);
        setView();
        callApi();
        return view;
    }

    public void setView() {

        tag = getArguments().getString(Config.SEARCH_TAG);
        searchEt.setVisibility(View.GONE);
        filterTitle.setVisibility(View.VISIBLE);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.purple));

        searchAdapter = new SearchAdapter(getContext(), getActivity(), movieModels, tvModels, tag);
        list.setHasFixedSize(true);
        list.setAdapter(searchAdapter);

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    displayError("Loading.....");
                    filterPresenter.getPageNumber(tag);
                }
            }
        });



    }

    @Override
    public void displayError(String error) {
        shimmerViewContainer.setVisibility(View.GONE);
        shimmerViewContainer.startShimmerAnimation();
        Utility.getUtilityInstance().snackBar(error, view);
    }

    @Override
    public void getMovieList(List<MovieModel> movieResponse) {
        shimmerViewContainer.setVisibility(View.GONE);
        shimmerViewContainer.startShimmerAnimation();
        if (movieResponse.size() > 0) {
            Log.e(TAG, "getMovieList: " + movieResponse.size());
            this.movieModels.addAll(movieResponse);
            searchAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void callApi() {
        filterPresenter = new FilterPresenter(this, getContext());
        filterPresenter.getFilterType(tag);
        shimmerViewContainer.setVisibility(View.VISIBLE);
        shimmerViewContainer.startShimmerAnimation();
        if (tag.equalsIgnoreCase("movie")) {
            filterPresenter.getFilteredData();
        } else {
            filterPresenter.getTvFilterData();
        }
    }

    @Override
    public void getTvList(List<TvModel> tvResponse) {
        shimmerViewContainer.setVisibility(View.GONE);
        shimmerViewContainer.startShimmerAnimation();
        if (tvResponse.size() > 0) {
            this.tvModels.addAll(tvResponse);
            searchAdapter.notifyDataSetChanged();
        }

    }


}
