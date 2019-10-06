package com.harilee.movieman.Search;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.harilee.movieman.Config;
import com.harilee.movieman.Details.DetailsFragment;
import com.harilee.movieman.DetailsTv.DetailsTvFragment;
import com.harilee.movieman.Model.MovieModel;
import com.harilee.movieman.Model.TvModel;
import com.harilee.movieman.MovieList.MovieAdapter;
import com.harilee.movieman.MovieList.MovieFragment;
import com.harilee.movieman.R;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchAdapter  extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private FragmentActivity activity;
    private Context context;
    private List<MovieModel> movieModels;
    private String tag;
    private List<TvModel> tvModels;

    public SearchAdapter(Context context, FragmentActivity activity, List<MovieModel> movieModels, List<TvModel> tvModels,  String tag) {
        this.activity = activity;
        this.context = context;
        this.movieModels = movieModels;
        this.tag = tag;
        this.tvModels = tvModels;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poster_card, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {

        if (movieModels.size() > 0) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + movieModels.get(position)
                    .getPosterPath()).into(holder.posterImage);
            holder.posterImage.setOnClickListener(v -> {

                String movieId = String.valueOf(movieModels.get(position).getId());
                Bundle bundle = new Bundle();
                bundle.putString(Config.MOVIE_ID, movieId);
                bundle.putString(Config.TAG, tag);
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame
                        , detailsFragment, "list").addToBackStack("list").commitAllowingStateLoss();
            });
        } else {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + tvModels.get(position)
                    .getPosterPath()).into(holder.posterImage);
            holder.posterImage.setOnClickListener(v -> {
                Log.e(TAG, "onBindViewHolder: "+"tv"+tvModels.get(position).getId() );
                String movieId = String.valueOf(tvModels.get(position).getId());
                Bundle bundle = new Bundle();
                bundle.putString(Config.MOVIE_ID, movieId);
                bundle.putString(Config.TAG, tag);
                DetailsTvFragment detailsTvFragment = new DetailsTvFragment();
                detailsTvFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame
                        , detailsTvFragment, "list").addToBackStack("list").commitAllowingStateLoss();
            });
        }

    }

    @Override
    public int getItemCount() {

        if (movieModels.size() > 0) {
            return movieModels.size();
        } else {
            return tvModels.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView posterImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.poster_image);
        }
    }
}
