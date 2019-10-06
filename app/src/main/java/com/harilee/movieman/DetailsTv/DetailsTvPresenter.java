package com.harilee.movieman.DetailsTv;

import android.util.Log;

import androidx.annotation.NonNull;

import com.harilee.movieman.Config;
import com.harilee.movieman.Details.DetailsPresenterInterface;
import com.harilee.movieman.Details.DetailsResponse;
import com.harilee.movieman.Details.DetailsViewInterface;
import com.harilee.movieman.Details.ReviewResponse;
import com.harilee.movieman.NetworkClient;
import com.harilee.movieman.NetworkInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailsTvPresenter implements DetailsTvPresenterInterface {

    private DetailsTvViewInterface detailsTvViewInterface;
    private String tvId;
    public DetailsTvPresenter (DetailsTvViewInterface detailsTvViewInterface){
        this.detailsTvViewInterface = detailsTvViewInterface;
    }

    @Override
    public void getTvId(String id) {
        this.tvId = id;
    }

    @Override
    public void getTvData() {
        getMovieDetails().subscribe(getObserver());
    }
    public Observable<TvDetails> getMovieDetails() {

        return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                .tvDetails(tvId, Config.TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<TvDetails> getObserver() {
        return new DisposableObserver<TvDetails>() {

            @Override
            public void onNext(@NonNull TvDetails tvDetails) {
                detailsTvViewInterface.getTvData(tvDetails);
                detailsTvViewInterface.getSeason(tvDetails.getSeasons());

            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                detailsTvViewInterface.displayError("Some error occurred please try after some time");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
    }

    @Override
    public void getReview() {
        getReviewDetails().subscribe(getObserverReview());

    }

    public Observable<ReviewResponse> getReviewDetails() {

        return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                .reviewDetailsTv(tvId, Config.TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<ReviewResponse> getObserverReview() {
        return new DisposableObserver<ReviewResponse>() {

            @Override
            public void onNext(@NonNull ReviewResponse reviewResponse) {
                detailsTvViewInterface.getReview(reviewResponse);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                detailsTvViewInterface.displayError("Some error occurred please try after some time");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
    }
}
