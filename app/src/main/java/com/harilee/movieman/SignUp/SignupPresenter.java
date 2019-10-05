package com.harilee.movieman.SignUp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.harilee.movieman.Model.SignupModel;
import com.harilee.movieman.NetworkClient;
import com.harilee.movieman.NetworkInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SignupPresenter implements SignupPresenterInterface {


    private SignupData signupData;
    private SignupViewInterface signupViewInterface;

    public SignupPresenter(SignupData signupData, SignupViewInterface signupViewInterface){
        this.signupData = signupData;
        this.signupViewInterface = signupViewInterface;

    }


    @Override
    public void signupUser() {
        getObservable().subscribe(getObserver());

    }

    public Observable<SignupResponse> getObservable() {
        return NetworkClient.getRetrofitMain().create(NetworkInterface.class)
                .signupUser(signupData.getUsername(), signupData.getPassword(), signupData.getEmail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<SignupResponse> getObserver() {
        return new DisposableObserver<SignupResponse>() {

            @Override
            public void onNext(@NonNull SignupResponse signupResponse) {
                SignupModel signupModel = signupResponse.getResult();
                signupViewInterface.getResponse(signupResponse);
                signupViewInterface.getNewuserData(signupModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                signupViewInterface.displayError("Some error occurred please try after some time");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        };
    }

    @Override
    public void setUsername(String username) {
        signupData.setUsername(username);
    }

    @Override
    public void setPassword(String password) {
        signupData.setPassword(password);
    }

    @Override
    public void setEmail(String email) {
        signupData.setEmail(email);
    }


}
