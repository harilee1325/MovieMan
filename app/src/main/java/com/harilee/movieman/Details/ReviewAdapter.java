package com.harilee.movieman.Details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.harilee.movieman.Model.ReviewModel;
import com.harilee.movieman.MovieList.MovieAdapter;
import com.harilee.movieman.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>  {



    private Context context;
    private FragmentActivity activity;
    private List<ReviewModel> reviewResponse;

    public ReviewAdapter(Context context, FragmentActivity activity, List<ReviewModel> reviewResponse) {
        this.context = context;
        this.activity = activity;
        this.reviewResponse = reviewResponse;

    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_list, parent, false);
        return new ReviewAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {

        holder.authorName.setText(reviewResponse.get(position).getAuthor());
        holder.review.setText(reviewResponse.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return reviewResponse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView authorName;
        public TextView review;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            authorName = itemView.findViewById(R.id.author_name);
            review = itemView.findViewById(R.id.review);
        }
    }
}
