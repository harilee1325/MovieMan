package com.harilee.movieman.Search;

import android.util.Log;

import androidx.annotation.NonNull;

import com.harilee.movieman.Config;
import com.harilee.movieman.MovieList.MovieResponse;
import com.harilee.movieman.MovieList.MovieViewInterface;
import com.harilee.movieman.MovieList.TvResponse;
import com.harilee.movieman.NetworkClient;
import com.harilee.movieman.NetworkInterface;
import com.harilee.movieman.Seasons.SeasonPresnterInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchPresenter implements SearchPresenterInterface {


    private SearchViewInterface movieViewInterface;
    private int pageNumber = 1;
    private String search;

    public SearchPresenter(SearchViewInterface movieViewInterface) {
        this.movieViewInterface = movieViewInterface;
    }

    @Override
    public void getPageNumber(String tag) {
        pageNumber++;
        if (tag.equalsIgnoreCase("movie")) {
            getMovieSearch();
        } else {
            getTvSearch();
        }
    }
    @Override
    public void getSearchText(String search) {
        this.search = search;
    }

    @Override
    public void getTvSearch() {

        getTvSearchObservable().subscribe(getObserverTvSearch());

    }

    public Observable<TvResponse> getTvSearchObservable() {
        Log.e(TAG, "getMovieObservable: " + pageNumber);
        return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                .searchTv(Config.TMDB_API_KEY, pageNumber, search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public DisposableObserver<TvResponse> getObserverTvSearch() {
        return new DisposableObserver<com.harilee.movieman.MovieList.TvResponse>() {

            @Override
            public void onNext(@NonNull com.harilee.movieman.MovieList.TvResponse tvResponse) {
                movieViewInterface.getTvList(tvResponse.getResults());
            }
            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                movieViewInterface.displayError("Some error occurred please try after some time");
            }
            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
    }


    @Override
    public void getMovieSearch() {
        getMovieSearchObservable().subscribe(getObserverMovieSearch());

    }

    public Observable<MovieResponse> getMovieSearchObservable() {
        Log.e(TAG, "getMovieObservable: " + pageNumber);
        return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                .searchMovie(Config.TMDB_API_KEY, pageNumber, search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public DisposableObserver<MovieResponse> getObserverMovieSearch() {
        return new DisposableObserver<MovieResponse>() {

            @Override
            public void onNext(@NonNull MovieResponse movieResponse) {
                movieViewInterface.getMovieList(movieResponse.getResults());
            }
            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                movieViewInterface.displayError("Some error occurred please try after some time");
            }
            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
    }

}
