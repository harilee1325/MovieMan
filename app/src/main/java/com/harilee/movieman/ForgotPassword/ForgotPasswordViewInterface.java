package com.harilee.movieman.ForgotPassword;

import android.app.Dialog;
import android.content.Context;

public interface ForgotPasswordViewInterface {

    void showToast(String s);
    void verifyEmailResponse(VerifyMailResponse verifyMailResponse);
    void setPasswordResposne(NewPasswordResponse newPasswordResponse);
    void displayError(String error);
    void verifyEmail();
    void setPassword();
    void showLoader(Context context, boolean b, Dialog dialog, String msg);
}
