package com.harilee.movieman.Login;
import android.util.Log;

import androidx.annotation.NonNull;

import com.harilee.movieman.NetworkClient;
import com.harilee.movieman.NetworkInterface;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class LoginPresenter implements LoginPresenterInterface {


    LoginViewInterface loginViewInterface;
    private LoginData user;

    //constructor to parse the view data in
    public LoginPresenter(LoginData loginData) {
        this.user = loginData;

    }

    //constructor to initialize the view and parse the view data
    public LoginPresenter(LoginViewInterface loginViewInterface, LoginData loginData) {
        this.loginViewInterface = loginViewInterface;
        this.user = loginData;
    }


    //method called when user clicks login button and also if username is not empty
    //you can find the function call in the fragment class
    @Override
    public void enterUsername(String username) {
        user.setUsername(username);
    }

    //method called when user clicks login button and also if password is not empty
    //you can find the function call in the fragment class
    @Override
    public void enterPassword(String password) {
        user.setPassword(password);
    }

    @Override
    public void loginUser() {
        getObservable().subscribe(getObserver());

    }

    public Observable<LoginResponse> getObservable() {
        return NetworkClient.getRetrofitMain().create(NetworkInterface.class)
                .loginUser(user.getUsername(), user.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<LoginResponse> getObserver() {

        return new DisposableObserver<LoginResponse>() {

            @Override
            public void onNext(@NonNull LoginResponse loginResponse) {
                loginViewInterface.getLoginResponse(loginResponse);
                loginViewInterface.getLoginData(loginResponse.getResult());

            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                loginViewInterface.displayError("Some error occurred please try after some time");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        };
    }
}
