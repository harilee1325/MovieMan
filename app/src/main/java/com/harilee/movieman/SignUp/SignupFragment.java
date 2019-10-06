package com.harilee.movieman.SignUp;

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
import com.google.android.material.textfield.TextInputLayout;
import com.harilee.movieman.Config;
import com.harilee.movieman.Login.LoginFragment;
import com.harilee.movieman.Model.SignupModel;
import com.harilee.movieman.R;
import com.harilee.movieman.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupFragment extends Fragment implements SignupViewInterface {

    @BindView(R.id.email_signup)
    EditText emailSignup;
    @BindView(R.id.username_signup)
    EditText usernameSignup;
    @BindView(R.id.password_signup)
    EditText passwordSignup;
    @BindView(R.id.confirm_password_signup)
    EditText confirmPasswordSignup;
    @BindView(R.id.signup_bt)
    Button signupBt;
    @BindView(R.id.login_tv)
    TextView loginTv;
    @BindView(R.id.inputLayout3)
    TextInputLayout inputLayout3;
    @BindView(R.id.coordinator_layout)
    ConstraintLayout coordinatorLayout;
    private View view;
    private SignupPresenter signupPresenter;
    private SignupData signupData;
    private Dialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_signup, container, false);
        ButterKnife.bind(this, view);
        setView();

        return view;
    }

    private void setView() {

        signupData = new SignupData();
        dialog = new Dialog(getContext());
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.purple));
    }


    @OnClick({R.id.signup_bt, R.id.login_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signup_bt:
                signupUser();
                break;
            case R.id.login_tv:
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
    public void getNewuserData(SignupModel signupModel) {

        if (signupModel != null){
            showToast(signupModel.getUsername()+" is the new user");
            Utility.getUtilityInstance().setPreference(getContext(), Config.USERNAME, signupModel.getUsername());
        }
    }

    @Override
    public void displayError(String s) {
        Utility.getUtilityInstance().showGifPopup(getContext(), false, dialog, "");
        showToast(s);
    }

    @Override
    public void signupUser() {

        String username = usernameSignup.getText().toString().trim();
        String password = passwordSignup.getText().toString().trim();
        String confirmPass = confirmPasswordSignup.getText().toString().trim();
        String email = emailSignup.getText().toString().trim();

        if (username.isEmpty()){
            usernameSignup.setError("Enter username");
        }else if (password.isEmpty()){
            passwordSignup.setError("Enter password");
        }else if (confirmPass.isEmpty()){
            confirmPasswordSignup.setError("Enter password again to check");
        }else if (email.isEmpty()){
            emailSignup.setError("Enter email");
        }else if (!password.equalsIgnoreCase(confirmPass)){
            confirmPasswordSignup.setError("Wrong password");
        }else {
            signupPresenter = new SignupPresenter(signupData, this);
            signupPresenter.setUsername(username);
            signupPresenter.setEmail(email);
            signupPresenter.setPassword(password);
            Utility.getUtilityInstance().showGifPopup(getContext(), true, dialog, "");
            signupPresenter.signupUser();
        }
    }

    @Override
    public void getResponse(SignupResponse signupResponse) {
        Utility.getUtilityInstance().showGifPopup(getContext(), false, dialog, "");
        String success = signupResponse.getSuccess();
        if (success.equalsIgnoreCase("yes")){
            showToast(signupResponse.getMsg());
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame
            , new LoginFragment(), "signup").addToBackStack(null).commitAllowingStateLoss();

        }else {
            displayError(signupResponse.getMsg());
        }
    }
}
