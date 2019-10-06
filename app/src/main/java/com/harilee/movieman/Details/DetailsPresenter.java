package com.harilee.movieman.Details;

import android.util.Log;

import androidx.annotation.NonNull;

import com.harilee.movieman.Config;
import com.harilee.movieman.NetworkClient;
import com.harilee.movieman.NetworkInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailsPresenter implements DetailsPresenterInterface {

    private DetailsViewInterface detailsViewInterface;
    String movieId;
    String type;

    public DetailsPresenter(DetailsViewInterface detailsViewInterface) {
        this.detailsViewInterface = detailsViewInterface;

    }

    @Override
    public void getMovieId(String id) {
        movieId = id;
    }

    @Override
    public void getMovieData(String tag) {
        type = tag;
        getMOvieDetails().subscribe(getObserver());
    }

    public Observable<DetailsResponse> getMOvieDetails() {

            return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                    .movieDetails(movieId, Config.TMDB_API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<DetailsResponse> getObserver() {
        return new DisposableObserver<DetailsResponse>() {

            @Override
            public void onNext(@NonNull DetailsResponse detailsResponse) {
                detailsViewInterface.getMovieData(detailsResponse);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                detailsViewInterface.displayError("Some error occurred please try after some time");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
    }

    @Override
    public void getMovieReview(String tag) {
        getReviewDetails().subscribe(getReviewObserver());

    }

    public Observable<ReviewResponse> getReviewDetails() {

            return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                    .reviewDetails(movieId, Config.TMDB_API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    }

    public DisposableObserver<ReviewResponse> getReviewObserver() {
        return new DisposableObserver<ReviewResponse>() {

            @Override
            public void onNext(@NonNull ReviewResponse reviewResponse) {
                detailsViewInterface.getMovieReview(reviewResponse);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                detailsViewInterface.displayError("Some error occurred please try after some time");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
    }

}
