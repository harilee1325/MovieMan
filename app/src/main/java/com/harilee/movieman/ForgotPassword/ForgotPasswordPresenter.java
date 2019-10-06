package com.harilee.movieman.ForgotPassword;

import android.util.Log;

import androidx.annotation.NonNull;

import com.harilee.movieman.Login.LoginResponse;
import com.harilee.movieman.NetworkClient;
import com.harilee.movieman.NetworkInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ForgotPasswordPresenter implements ForgotPasswordPresenterInterface {


    ForgotPasswordViewInterface forgotPasswordViewInterface ;
    ForgotPasswordData forgotPasswordData ;

    public ForgotPasswordPresenter(ForgotPasswordViewInterface forgotPasswordViewInterface, ForgotPasswordData forgotPasswordData){

        this.forgotPasswordData = forgotPasswordData;
        this.forgotPasswordViewInterface = forgotPasswordViewInterface;

    }



    @Override
    public void setEmail(String email) {
        forgotPasswordData.setEmail(email);

    }

    @Override
    public void setPassword(String password) {
        forgotPasswordData.setPassword(password);
    }

    @Override
    public void verifyEmail() {
        getObservableVerifyMail().subscribe(getObserverMail());

    }

    public Observable<VerifyMailResponse> getObservableVerifyMail() {
        return NetworkClient.getRetrofitMain().create(NetworkInterface.class)
                .verifyMailidOfUser(forgotPasswordData.getEmail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<VerifyMailResponse> getObserverMail() {

        return new DisposableObserver<VerifyMailResponse>() {

            @Override
            public void onNext(@NonNull VerifyMailResponse verifyMailResponse) {
                forgotPasswordViewInterface.verifyEmailResponse(verifyMailResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                forgotPasswordViewInterface.displayError("Some error occurred please try after some time.");
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        };
    }
    @Override
    public void newPassword() {
        getObservableNewPassword().subscribe(getObserverPass());

    }

    public Observable<NewPasswordResponse> getObservableNewPassword() {
        return NetworkClient.getRetrofitMain().create(NetworkInterface.class)
                .setNewPassword(forgotPasswordData.getEmail(), forgotPasswordData.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public DisposableObserver<NewPasswordResponse> getObserverPass() {

        return new DisposableObserver<NewPasswordResponse>() {

            @Override
            public void onNext(@NonNull NewPasswordResponse newPasswordResponse) {
                forgotPasswordViewInterface.setPasswordResposne(newPasswordResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                forgotPasswordViewInterface.displayError("Some error occurred please try after some time.");
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        };
    }
}
