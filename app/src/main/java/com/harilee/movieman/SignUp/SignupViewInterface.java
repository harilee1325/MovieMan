package com.harilee.movieman.SignUp;


import com.harilee.movieman.Model.SignupModel;

public interface SignupViewInterface {

    void showToast(String s);
    void getNewuserData(SignupModel signupModel);
    void displayError(String s);
    void signupUser();
    void getResponse(SignupResponse signupResponse);
}
