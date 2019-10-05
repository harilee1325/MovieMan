package com.harilee.movieman.Login;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.harilee.movieman.Config;
import com.harilee.movieman.ForgotPassword.ForgotPasswotdFragment;
import com.harilee.movieman.Model.LoginModel;
import com.harilee.movieman.R;
import com.harilee.movieman.SignUp.SignupFragment;
import com.harilee.movieman.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements LoginViewInterface {

    @BindView(R.id.username_login)
    EditText usernameLogin;
    @BindView(R.id.password_login)
    EditText passwordLogin;
    @BindView(R.id.login_bt)
    Button loginBt;
    @BindView(R.id.forgot_pass_tv)
    TextView forgotPassTv;
    @BindView(R.id.signup_tv)
    TextView signupTv;
    @BindView(R.id.coordinator_layout)
    ConstraintLayout coordinatorLayout;
    private View view;
    private LoginPresenter loginPresenter;
    private String TAG = "Login";
    private LoginData loginData = new LoginData();
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        setView();

        return view;
    }

    private void setView() {
        dialog = new Dialog(getContext());
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.purple));
        loginPresenter = new LoginPresenter(this, loginData);
    }


    @OnClick({R.id.username_login, R.id.password_login, R.id.login_bt, R.id.forgot_pass_tv, R.id.signup_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.username_login:
                break;
            case R.id.password_login:
                break;
            case R.id.login_bt:
                loginUser();
                break;
            case R.id.forgot_pass_tv:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame
                , new ForgotPasswotdFragment(), "login").addToBackStack(null).commitAllowingStateLoss();
                break;
            case R.id.signup_tv:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame
                        , new SignupFragment(), "login").addToBackStack(null).commitAllowingStateLoss();
                break;
        }
    }

    @Override
    public void loginUser() {

        String username = usernameLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();
        if (username.isEmpty()) {
            usernameLogin.setError("Enter username");
        } else if (password.isEmpty()) {
            passwordLogin.setError("Enter password");
        } else {
            // constructor to pass data the presenter class
            LoginPresenter loginPresenterData = new LoginPresenter(loginData);
            loginPresenterData.enterUsername(username);
            loginPresenterData.enterPassword(password);

            //object that specifies the view interface
            Utility.getUtilityInstance().showGifPopup(getContext(), true, dialog, "Checking user");
            loginPresenter.loginUser();
        }
    }

    @Override
    public void getLoginData(LoginModel loginModel) {
        Utility.getUtilityInstance().setPreference(getContext(), Config.USERNAME, loginModel.getUsername());
    }

    @Override
    public void showToast(String s) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, s, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void getLoginResponse(LoginResponse loginResponse) {
        Utility.getUtilityInstance().showGifPopup(getContext(), false, dialog, "Checking user");
        showToast(loginResponse.getMsg());


    }


    @Override
    public void displayError(String s) {
        Utility.getUtilityInstance().showGifPopup(getContext(), false, dialog, "Checking user");
        showToast(s);
    }
}
