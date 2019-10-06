package com.harilee.movieman.ForgotPassword;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.harilee.movieman.Login.LoginFragment;
import com.harilee.movieman.R;
import com.harilee.movieman.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswotdFragment extends Fragment implements ForgotPasswordViewInterface {

    @BindView(R.id.back_forgot_pass)
    ImageView backForgotPass;
    @BindView(R.id.email_forgot_pass)
    EditText emailForgotPass;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.confirm_new_password)
    EditText confirmNewPassword;
    @BindView(R.id.card_1)
    CardView card1;
    @BindView(R.id.verify_mail)
    ImageView verifyMail;
    @BindView(R.id.coordinator_layout)
    ConstraintLayout coordinatorLayout;
    @BindView(R.id.set_password)
    ImageView setPassword;
    @BindView(R.id.inputLayout0)
    TextInputLayout inputLayout0;
    @BindView(R.id.inputLayout1)
    TextInputLayout inputLayout1;
    @BindView(R.id.inputLayout2)
    TextInputLayout inputLayout2;
    private View view;
    private ForgotPasswordPresenter forgotPasswordPresenter;
    private ForgotPasswordData forgotPasswordData;
    private String emailMain;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgot_password, container, false);
        ButterKnife.bind(this, view);

        setView();

        return view;
    }

    private void setView() {

        dialog = new Dialog(getContext());
        forgotPasswordData = new ForgotPasswordData();
        forgotPasswordPresenter = new ForgotPasswordPresenter(this, forgotPasswordData);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.purple));
    }

    @OnClick({R.id.back_forgot_pass, R.id.email_forgot_pass, R.id.new_password, R.id.confirm_new_password
            , R.id.verify_mail, R.id.set_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_forgot_pass:
                break;
            case R.id.email_forgot_pass:
                break;
            case R.id.new_password:
                break;
            case R.id.confirm_new_password:
                break;
            case R.id.verify_mail:
                verifyEmail();
                break;
            case R.id.set_password:
                setPassword();
                break;
        }
    }

    @Override
    public void showToast(String s) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, s, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void verifyEmailResponse(VerifyMailResponse verifyMailResponse) {
        showLoader(getContext(), false, dialog, " ");
        if (verifyMailResponse != null) {
            if (verifyMailResponse.getSuccess().equalsIgnoreCase("yes")) {
                verifyMail.setVisibility(View.GONE);
                emailForgotPass.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                setPassword.setVisibility(View.VISIBLE);
                inputLayout0.setVisibility(View.GONE);
                showToast("Enter new password");
            }else {
                showToast(verifyMailResponse.getMsg());
            }


        }

    }

    @Override
    public void setPasswordResposne(NewPasswordResponse newPasswordResponse) {
        showLoader(getContext(), false, dialog, " ");
        if (newPasswordResponse != null) {
            showToast(newPasswordResponse.getMsg());
            if (newPasswordResponse.getSuccess().equalsIgnoreCase("yes")) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame
                        , new LoginFragment(), "forgotpassword").addToBackStack(null).commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void displayError(String error) {
        showLoader(getContext(), false, dialog, " ");
        showToast(error);

    }

    @Override
    public void verifyEmail() {

        String email = emailForgotPass.getText().toString().trim();
        if (email.isEmpty()) {
            emailForgotPass.setError("Enter email");
        } else {
            emailMain = email;
            forgotPasswordData.setEmail(email);
            showLoader(getContext(), true, dialog, " ");
            forgotPasswordPresenter.verifyEmail();
        }

    }

    @Override
    public void setPassword() {
        String password = newPassword.getText().toString().trim();
        if (password.isEmpty()) {
            newPassword.setError("Enter password");
        } else {
            newPassword.setVisibility(View.GONE);
            inputLayout1.setVisibility(View.GONE);
            confirmNewPassword.setVisibility(View.VISIBLE);
            inputLayout2.setVisibility(View.VISIBLE);
            String confirmPassword = confirmNewPassword.getText().toString().trim();
            if (confirmPassword.isEmpty()) {
                confirmNewPassword.setError("Enter password again");
            } else if (!password.equals(confirmPassword)) {
                confirmNewPassword.setError("Enter same password");
            } else {
                forgotPasswordData.setEmail(emailMain);
                forgotPasswordData.setPassword(confirmPassword);
                showLoader(getContext(), true, dialog, " ");
                forgotPasswordPresenter.newPassword();
            }
        }


    }

    @Override
    public void showLoader(Context context, boolean b, Dialog dialog, String msg) {

        Utility.getUtilityInstance().showGifPopup(context, b, dialog, msg);
    }

}
