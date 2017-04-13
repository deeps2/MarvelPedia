package com.shikhar.marvelpedia.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shikhar.marvelpedia.Activity.ComicsDetailsActivity;
import com.shikhar.marvelpedia.Activity.ModelComics.Result;
import com.shikhar.marvelpedia.R;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//Adapter for returning list of Characters
public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder> {

    List<Result> listOfComics;

    public ComicsAdapter(List<Result> setOfComics){
        listOfComics = setOfComics;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final Context context;
        @BindView(R.id.poster) ImageView poster;
        @BindView(R.id.name) TextView name;

        public ViewHolder(final Context context, View v) {
            super(v);
            ButterKnife.bind(this, v);
            this.context = context;
            v.setOnClickListener(this);
        }

        //when a card gets clicked in the recycler view
        @Override
        public void onClick(View v) {
            //retrieve the character information which was clicked in Result set, and pass it to ComicsDetailsActivity
            int position = getAdapterPosition();

            Result comicDetails = listOfComics.get(position);
            Intent mainIntent = new Intent(context, ComicsDetailsActivity.class);
            mainIntent.putExtra("CLICKED_COMIC", comicDetails);
            context.startActivity(mainIntent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(context, v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //set thumbnail associated with the Comics
        Glide.with(holder.context)
                .load(listOfComics.get(position).getThumbnail().getPath()+"/standard_fantastic.jpg")
                .placeholder(R.drawable.marvellogo)
                .error(R.drawable.marvellogo)
                .crossFade() //animation
                .into(holder.poster);

        //set name of the Comics
        holder.name.setText(listOfComics.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return listOfComics.size();
    }

    //update the adapter source of data when response is returned by the API.
    public void setDataAdapter(List<Result> listOfComics){
        this.listOfComics = listOfComics;
    }
}