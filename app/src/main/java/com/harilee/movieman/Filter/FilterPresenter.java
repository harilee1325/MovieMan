package com.harilee.movieman.Filter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.harilee.movieman.Config;
import com.harilee.movieman.MovieList.MovieResponse;
import com.harilee.movieman.MovieList.TvResponse;
import com.harilee.movieman.NetworkClient;
import com.harilee.movieman.NetworkInterface;
import com.harilee.movieman.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Filter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FilterPresenter implements FilterPresnterInterface {

    private FilterViewInterface filterViewInterface;
    private int pageNumber = 1;
    private String type;
    private String mostPopular;
    private String highestPaid;
    private String newest;
    private Context context;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public FilterPresenter(FilterViewInterface filterViewInterface, Context context) {
        this.filterViewInterface = filterViewInterface;
        this.context = context;
        this.mostPopular = Utility.getUtilityInstance().getPreference(context, Config.MOST_POPULAR);
        this.highestPaid = Utility.getUtilityInstance().getPreference(context, Config.HIGHEST_PAID);
        this.newest = Utility.getUtilityInstance().getPreference(context, Config.NEWEST);

        Log.e(TAG, "FilterPresenter: " + mostPopular);
        Log.e(TAG, "FilterPresenter: " + highestPaid);
        Log.e(TAG, "FilterPresenter: " + newest);
    }

    @Override
    public void getFilterType(String filter) {
        this.type = filter;

    }

    @Override
    public void getPageNumber(String tag) {
        pageNumber++;
        if (tag.equalsIgnoreCase("movie")) {
            getFilteredData();
        } else {
            getTvFilterData();
        }
    }

    public void getTvFilterData() {
        getFilterTvObservable().subscribe(getObserverTv());

    }

    public Observable<TvResponse> getFilterTvObservable() {

        if (mostPopular.equalsIgnoreCase("yes")) {
            return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                    .tvPopular(Config.TMDB_API_KEY, 50, pageNumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else if(highestPaid.equalsIgnoreCase("yes")) {
            return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                    .tvRated(Config.TMDB_API_KEY,  pageNumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        else {
            return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                    .tvNew(Config.TMDB_API_KEY, 50, pageNumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }


    }

    public DisposableObserver<TvResponse> getObserverTv() {
        return new DisposableObserver<TvResponse>() {

            @Override
            public void onNext(@NonNull TvResponse tvResponse) {
                filterViewInterface.getTvList(tvResponse.getResults());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                filterViewInterface.displayError("Some error occurred please try after some time");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
    }

    @Override
    public void getFilteredData() {

        getFilterObservable().subscribe(getObserver());
    }

    public Observable<MovieResponse> getFilterObservable() {

         if (mostPopular.equalsIgnoreCase("yes")) {
            Log.e(TAG, "getFilterObservable: 1" + mostPopular);
            return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                    .moviePopular(Config.TMDB_API_KEY, pageNumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else if (highestPaid.equalsIgnoreCase("yes")) {
            Log.e(TAG, "getFilterObservable: 1" + mostPopular);
            return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                    .movieHighestPaid(Config.TMDB_API_KEY, pageNumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            Log.e(TAG, "getFilterObservable: 3" + mostPopular);
            Calendar cal = Calendar.getInstance();
            String maxReleaseDate = dateFormat.format(cal.getTime());
            return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                    .newMovies(Config.TMDB_API_KEY, maxReleaseDate, 50, pageNumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    public DisposableObserver<MovieResponse> getObserver() {
        return new DisposableObserver<MovieResponse>() {

            @Override
            public void onNext(@NonNull MovieResponse movieResponse) {
                Log.e(TAG, "onNext: " + movieResponse.getResults().size());
                filterViewInterface.getMovieList(movieResponse.getResults());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                Log.e(TAG, "onError: "+e.getMessage() );
                filterViewInterface.displayError("Some error occurred please try after some time");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
    }
}
