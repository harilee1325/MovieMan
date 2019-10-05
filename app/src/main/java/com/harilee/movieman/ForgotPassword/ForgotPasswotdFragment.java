package com.harilee.movieman.ForgotPassword;

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
import androidx.fragment.app.Fragment;

import com.harilee.movieman.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswotdFragment extends Fragment {

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
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgot_password, container, false);
        ButterKnife.bind(this, view);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.purple));
        return view;
    }

    @OnClick({R.id.back_forgot_pass, R.id.email_forgot_pass, R.id.new_password, R.id.confirm_new_password, R.id.verify_mail})
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
                checkMail();
                break;
        }
    }

    private void checkMail() {
    }
}
