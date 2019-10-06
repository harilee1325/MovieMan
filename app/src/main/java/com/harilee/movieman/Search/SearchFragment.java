package com.harilee.movieman.Search;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.harilee.movieman.Config;
import com.harilee.movieman.Model.EpisodeModel;
import com.harilee.movieman.Model.MovieModel;
import com.harilee.movieman.Model.TvModel;
import com.harilee.movieman.R;
import com.harilee.movieman.Seasons.SeasonViewInterface;
import com.harilee.movieman.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchFragment extends Fragment  implements SearchViewInterface {

    @BindView(R.id.filter_back)
    ImageView filterBack;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.list)
    RecyclerView list;
    private View view;
    private String tag;
    private SearchPresenter searchPresenter;
    private List<MovieModel> movieModels = new ArrayList<>();
    private List<TvModel> tvModels = new ArrayList<>();
    private SearchAdapter searchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    public void setView() {
        
        tag = getArguments().getString(Config.SEARCH_TAG);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.purple));
        searchPresenter = new SearchPresenter(this);
        searchAdapter = new SearchAdapter(getContext(), getActivity(),movieModels,tvModels,tag );
        list.setHasFixedSize(true);
        list.setAdapter(searchAdapter);
        searchEt.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        searchEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.e(TAG, "setView: "+"here 1" );
                if (tag.equalsIgnoreCase("movie")) {
                    Log.e(TAG, "setView: "+"here 2" );
                    String query = searchEt.getText().toString().trim();
                    if (query.isEmpty()) {
                        searchEt.setError("Search something");
                    } else {
                        Log.e(TAG, "setView: "+"here 3" );
                        searchPresenter.getSearchText(query);
                        this.movieModels.clear();
                        searchPresenter.getMovieSearch();
                    }

                } else {
                    String query = searchEt.getText().toString().trim();
                    if (query.isEmpty()) {
                        searchEt.setError("Search something");
                    } else {
                        searchPresenter.getSearchText(query);
                        this.tvModels.clear();
                        searchPresenter.getTvSearch();
                    }
                }
            }
            return false;
        });


        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    displayError("Loading.....");
                    searchPresenter.getPageNumber(tag);
                }
            }
        });
    }

    @Override
    public void callApi() {

    }

    @Override
    public void getTvList(List<TvModel> tvResponse) {
        if (tvResponse != null) {
            Log.e(TAG, "getMovieList: " + tvResponse.size());
            this.tvModels.addAll(tvResponse);
            searchAdapter.notifyDataSetChanged();
        } else {
            displayError("Could not get the Tv shows please check your internet connection and try again");
        }
    }

    @Override
    public void displayError(String error) {
        Utility.getUtilityInstance().snackBar(error, view);
    }

    @Override
    public void getMovieList(List<MovieModel> movieResponse) {
        if (movieResponse != null) {
            Log.e(TAG, "getMovieList: " + movieResponse.size());
            this.movieModels.addAll(movieResponse);
            searchAdapter.notifyDataSetChanged();
        } else {
            displayError("Could not get the Tv shows please check your internet connection and try again");
        }
    }


}
