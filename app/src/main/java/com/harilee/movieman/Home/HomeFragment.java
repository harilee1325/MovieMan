package com.harilee.movieman.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.harilee.movieman.Config;
import com.harilee.movieman.MovieList.MovieFragment;
import com.harilee.movieman.R;
import com.harilee.movieman.Utility;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {


    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_page, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;

    }

    private void setView() {

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.purple));

        Utility.getUtilityInstance().setPreference(getContext(), Config.MOST_POPULAR, "no");
        Utility.getUtilityInstance().setPreference(getContext(), Config.HIGHEST_PAID, "no");
        Utility.getUtilityInstance().setPreference(getContext(), Config.NEWEST, "no");


    }


    @OnClick({R.id.movie_card, R.id.tvshow_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.movie_card:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame
                , new MovieFragment(), "movie").addToBackStack(null).commitAllowingStateLoss();
                break;
            case R.id.tvshow_card:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame
                        , new MovieFragment(), "tv").addToBackStack(null).commitAllowingStateLoss();
                break;
        }
    }
}
