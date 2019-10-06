package com.harilee.movieman.Login;

import android.app.Dialog;
import android.content.Context;

import com.harilee.movieman.Model.LoginModel;

public interface LoginViewInterface {

    void showToast(String s);
    void getLoginResponse(LoginResponse loginResponse);
    void displayError(String s);
    void loginUser();
    void getLoginData(LoginModel loginModel);
    void showLoader(Context context, boolean b, Dialog dialog, String msg);


}
