package com.harilee.movieman.MovieList;

import android.util.Log;

import androidx.annotation.NonNull;

import com.harilee.movieman.Config;
import com.harilee.movieman.NetworkClient;
import com.harilee.movieman.NetworkInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MoviePresenter implements MoviePresenterInterface {


    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private MovieViewInterface movieViewInterface;
    private int pageNumber = 1;

    public MoviePresenter(MovieViewInterface movieViewInterface) {
        this.movieViewInterface = movieViewInterface;
    }

    @Override
    public void getMovieList() {
        getMovieObservable().subscribe(getObserver());

    }

    @Override
    public void getPageNumber(String tag) {

        pageNumber++;
        if (tag.equalsIgnoreCase("movie")) {
            getMovieList();
        } else {
            getTvList();
        }

    }

    @Override
    public void getTvList() {
        getTvObservable().subscribe(getObserverTv());

    }

    public Observable<MovieResponse> getMovieObservable() {

        Calendar cal = Calendar.getInstance();
        String maxReleaseDate = dateFormat.format(cal.getTime());
        Log.e(TAG, "getMovieObservable: "+pageNumber );
        return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                .newMovies(Config.TMDB_API_KEY, maxReleaseDate, 50, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public DisposableObserver<MovieResponse> getObserver() {
//        movieViewInterface.displayError("Loading ");
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


    public Observable<com.harilee.movieman.MovieList.TvResponse> getTvObservable() {

        Calendar cal = Calendar.getInstance();
        String maxReleaseDate = dateFormat.format(cal.getTime());

        return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                .newTvshow(Config.TMDB_API_KEY, maxReleaseDate, 50, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public DisposableObserver<com.harilee.movieman.MovieList.TvResponse> getObserverTv() {
//        movieViewInterface.displayError("Loading ");
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


}
