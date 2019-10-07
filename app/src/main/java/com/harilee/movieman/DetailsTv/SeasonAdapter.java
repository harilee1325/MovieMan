package com.harilee.movieman.DetailsTv;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.harilee.movieman.Config;
import com.harilee.movieman.Details.ReviewAdapter;
import com.harilee.movieman.Model.SeasonModel;
import com.harilee.movieman.R;
import com.harilee.movieman.Seasons.SeasonFragment;

import org.w3c.dom.Text;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder>   {

    private Context context;
    private FragmentActivity activity;
    private List<SeasonModel> seasonModels;
    private String TAG = "season";

    public SeasonAdapter(Context context, FragmentActivity activity, List<SeasonModel> seasonModels) {
        this.context = context;
        this.activity = activity;
        this.seasonModels = seasonModels;
    }

    @NonNull
    @Override
    public SeasonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.season_list, parent, false);
        return new SeasonAdapter.ViewHolder(view);      }

    @Override
    public void onBindViewHolder(@NonNull SeasonAdapter.ViewHolder holder, int position) {

        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + seasonModels.get(position).getPosterPath())
                .into(holder.seasonPoster);
        Log.e(TAG, "onBindViewHolder: "+ seasonModels.get(position).getOverview());
        holder.seasonNumber.setText(("Season "+(seasonModels.get(position).getSeasonNumber())));
        holder.overview.setText("Episodes "+seasonModels.get(position).getEpisodeCount());


    }

    @Override
    public int getItemCount() {
        return seasonModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView seasonNumber;
        public TextView overview;
        public ImageView seasonPoster;
        public CardView seasonCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            seasonNumber = itemView.findViewById(R.id.season_number);
            overview = itemView.findViewById(R.id.overview);
            seasonPoster = itemView.findViewById(R.id.season_poster);
            seasonCard = itemView.findViewById(R.id.season_card);
        }
    }
}
