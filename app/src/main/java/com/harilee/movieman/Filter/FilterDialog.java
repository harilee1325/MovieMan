package com.harilee.movieman.Filter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.harilee.movieman.Config;
import com.harilee.movieman.R;
import com.harilee.movieman.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.getDefaultSize;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class FilterDialog extends BottomSheetDialogFragment {

    @BindView(R.id.most_popular_select)
    Button mostPopularSelect;
    @BindView(R.id.most_popular_unselect)
    Button mostPopularUnselect;
    @BindView(R.id.highest_paid_select)
    Button highestPaidSelect;
    @BindView(R.id.highest_paid_unselect)
    Button highestPaidUnselect;
    @BindView(R.id.newest_select)
    Button newestSelect;
    @BindView(R.id.newest_unselect)
    Button newestUnselect;
    private View view;
    private ButtonClicked buttonClickedListener;
    private String tag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filter_selection, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    private void setView() {

        tag = getArguments().getString(Config.SEARCH_TAG, "hello");
        String mostPopular = Utility.getUtilityInstance().getPreference(getContext(), Config.MOST_POPULAR);
        String highest = Utility.getUtilityInstance().getPreference(getContext(), Config.HIGHEST_PAID);
        String newest = Utility.getUtilityInstance().getPreference(getContext(), Config.NEWEST);

        if (mostPopular.equalsIgnoreCase("yes")) {
            mostPopularSelect.setVisibility(View.GONE);
            mostPopularUnselect.setVisibility(View.VISIBLE);
            highestPaidUnselect.setVisibility(View.GONE);
            highestPaidSelect.setVisibility(View.VISIBLE);
            newestUnselect.setVisibility(View.GONE);
            newestSelect.setVisibility(View.VISIBLE);
        }
        if (highest.equalsIgnoreCase("yes")) {
            highestPaidSelect.setVisibility(View.GONE);
            highestPaidUnselect.setVisibility(View.VISIBLE);
            newestUnselect.setVisibility(View.GONE);
            newestSelect.setVisibility(View.VISIBLE);
            mostPopularSelect.setVisibility(View.VISIBLE);
            mostPopularUnselect.setVisibility(View.GONE);
        }
        if (newest.equalsIgnoreCase("yes")) {
            newestSelect.setVisibility(View.GONE);
            newestUnselect.setVisibility(View.VISIBLE);
            mostPopularSelect.setVisibility(View.VISIBLE);
            mostPopularUnselect.setVisibility(View.GONE);
            highestPaidSelect.setVisibility(View.VISIBLE);
            highestPaidUnselect.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.most_popular_select, R.id.most_popular_unselect, R.id.highest_paid_select, R.id.highest_paid_unselect, R.id.newest_select, R.id.newest_unselect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.most_popular_select:
                mostPopularSelect.setVisibility(View.GONE);
                mostPopularUnselect.setVisibility(View.VISIBLE);
                highestPaidUnselect.setVisibility(View.GONE);
                highestPaidSelect.setVisibility(View.VISIBLE);
                newestUnselect.setVisibility(View.GONE);
                newestSelect.setVisibility(View.VISIBLE);
                Utility.getUtilityInstance().setPreference(getContext(), Config.MOST_POPULAR, "yes");
                Utility.getUtilityInstance().setPreference(getContext(), Config.HIGHEST_PAID, "no");
                Utility.getUtilityInstance().setPreference(getContext(), Config.NEWEST, "no");
                callFragment();
                //buttonClickedListener.buttonClicked();
                break;
            case R.id.most_popular_unselect:
                mostPopularSelect.setVisibility(View.VISIBLE);
                mostPopularUnselect.setVisibility(View.GONE);
                Utility.getUtilityInstance().setPreference(getContext(), Config.MOST_POPULAR, "no");
                break;
            case R.id.highest_paid_select:
                highestPaidSelect.setVisibility(View.GONE);
                highestPaidUnselect.setVisibility(View.VISIBLE);
                newestUnselect.setVisibility(View.GONE);
                newestSelect.setVisibility(View.VISIBLE);
                mostPopularSelect.setVisibility(View.VISIBLE);
                mostPopularUnselect.setVisibility(View.GONE);
                Utility.getUtilityInstance().setPreference(getContext(), Config.HIGHEST_PAID, "yes");
                Utility.getUtilityInstance().setPreference(getContext(), Config.NEWEST, "no");
                Utility.getUtilityInstance().setPreference(getContext(), Config.MOST_POPULAR, "no");
                callFragment();

//                buttonClickedListener.buttonClicked();
                break;
            case R.id.highest_paid_unselect:
                highestPaidSelect.setVisibility(View.VISIBLE);
                highestPaidUnselect.setVisibility(View.GONE);
                Utility.getUtilityInstance().setPreference(getContext(), Config.HIGHEST_PAID, "no");
                break;
            case R.id.newest_select:
                newestSelect.setVisibility(View.GONE);
                newestUnselect.setVisibility(View.VISIBLE);
                mostPopularSelect.setVisibility(View.VISIBLE);
                mostPopularUnselect.setVisibility(View.GONE);
                highestPaidSelect.setVisibility(View.VISIBLE);
                highestPaidUnselect.setVisibility(View.GONE);
                Utility.getUtilityInstance().setPreference(getContext(), Config.NEWEST, "yes");
                Utility.getUtilityInstance().setPreference(getContext(), Config.MOST_POPULAR, "no");
                Utility.getUtilityInstance().setPreference(getContext(), Config.HIGHEST_PAID, "no");
                callFragment();

                //buttonClickedListener.buttonClicked();
                break;
            case R.id.newest_unselect:
                newestSelect.setVisibility(View.VISIBLE);
                newestUnselect.setVisibility(View.GONE);
                Utility.getUtilityInstance().setPreference(getContext(), Config.NEWEST, "no");
                break;
        }
    }

    private void callFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(Config.SEARCH_TAG, tag);
        FilterFragment filterFragment = new FilterFragment();
        filterFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame
                , filterFragment, "list").addToBackStack("list").commitAllowingStateLoss();
        getDialog().cancel();
    }

    public interface ButtonClicked {
        void buttonClicked();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            buttonClickedListener = (ButtonClicked) context;
        } catch (ClassCastException c) {
            Log.e(TAG, "onAttach:" + c.getMessage());
        }


    }
}
