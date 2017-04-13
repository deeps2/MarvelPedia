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
import com.shikhar.marvelpedia.Activity.CharsDetailsActivity;
import com.shikhar.marvelpedia.Activity.ModelChars.Result;
import com.shikhar.marvelpedia.R;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//Adapter for returning list of Characters
public class CharsAdapter extends RecyclerView.Adapter<CharsAdapter.ViewHolder> {

    List<Result> listOfCharacters;

    public CharsAdapter(List<Result> listOfChars){
        listOfCharacters = listOfChars;
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
            //retrieve the character information which was clicked in Result set, and pass it to CharsDetailsActivity
            int position = getAdapterPosition();

            Result charsDetails = listOfCharacters.get(position);
            Intent mainIntent = new Intent(context, CharsDetailsActivity.class);
            mainIntent.putExtra("CLICKED_CHAR", charsDetails);
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

        //set thumbnail associated with the character
        Glide.with(holder.context)
                .load(listOfCharacters.get(position).getThumbnail().getPath()+"/standard_fantastic.jpg")
                .placeholder(R.drawable.marvellogo)
                .error(R.drawable.marvellogo)
                .crossFade() //animation
                .into(holder.poster);

        //set name of the character
        holder.name.setText(listOfCharacters.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listOfCharacters.size();
    }

    //update the adapter source of data when response is returned by the API.
    public void setDataAdapter(List<Result> listOfChars){
        this.listOfCharacters = listOfChars;
    }
}