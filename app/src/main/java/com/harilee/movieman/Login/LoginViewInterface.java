package com.harilee.movieman.Login;

import com.harilee.movieman.Model.LoginModel;

public interface LoginViewInterface {

    void showToast(String s);
    void getLoginResponse(LoginResponse loginResponse);
    void displayError(String s);
    void loginUser();
    void getLoginData(LoginModel loginModel);

}
