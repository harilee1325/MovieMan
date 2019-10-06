package com.harilee.movieman.Seasons;

import android.util.Log;

import androidx.annotation.NonNull;

import com.harilee.movieman.Config;
import com.harilee.movieman.MovieList.MovieResponse;
import com.harilee.movieman.NetworkClient;
import com.harilee.movieman.NetworkInterface;

import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SeasonPresenter implements SeasonPresnterInterface {



   private SeasonViewInterface seasonViewInterface ;
   private String sId , sNumber;
   public SeasonPresenter(SeasonViewInterface seasonViewInterface){
       this.seasonViewInterface = seasonViewInterface;

   }


    @Override
    public void getSeasonData(String sId, String sNumber) {
        this.sId = sId;
        this.sNumber = sNumber;
    }

    @Override
    public void getEpisodes() {
            getObservable().subscribe(getObserver());
    }


    public Observable<SeasonResponse> getObservable() {
        Log.e(TAG, "getObservable: "+sId+" "+sNumber);
        return NetworkClient.getRetrofitTMBD().create(NetworkInterface.class)
                .getEpisodes(sId, sNumber,Config.TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public DisposableObserver<SeasonResponse> getObserver() {
        return new DisposableObserver<SeasonResponse>() {

            @Override
            public void onNext(@NonNull SeasonResponse seasonResponse) {

                seasonViewInterface.getEpisodeData(seasonResponse.getEpisodes());

            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                seasonViewInterface.displayError("Some error occurred please try after some time");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
    }

}
