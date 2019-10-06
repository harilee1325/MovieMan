package com.harilee.movieman.Seasons;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.harilee.movieman.Model.EpisodeModel;
import com.harilee.movieman.Model.SeasonModel;
import com.harilee.movieman.MovieList.MovieAdapter;
import com.harilee.movieman.R;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder>  {

    private static final String TAG = "Season";
    private Context context;
    private FragmentActivity activity;
    private List<EpisodeModel> seasonModels;
    public EpisodeAdapter(Context context, FragmentActivity activity, List<EpisodeModel> seasonModels) {

        this.context = context;
        this.activity = activity;
        this.seasonModels = seasonModels;
    }

    @NonNull
    @Override
    public EpisodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.episode_list, parent, false);
        return new EpisodeAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.ViewHolder holder, int position) {

        Log.e(TAG, "onBindViewHolder: "+seasonModels.get(position).getName() );
        holder.seasonName.setText(seasonModels.get(position).getName());
        holder.seasonOverview.setText(seasonModels.get(position).getOverview());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + seasonModels.get(position)
                .getStillPath()).into(holder.seasonImage);
    }

    @Override
    public int getItemCount() {
        return seasonModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView seasonImage;
        public TextView seasonName;
        public TextView seasonOverview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            seasonImage = itemView.findViewById(R.id.season_poster);
            seasonName = itemView.findViewById(R.id.episode_name);
            seasonOverview = itemView.findViewById(R.id.overview);
        }
    }
}
