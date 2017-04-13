package com.shikhar.marvelpedia.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shikhar.marvelpedia.Activity.ModelChars.Result;
import com.shikhar.marvelpedia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.char_cover) ImageView charPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chars_details);
        ButterKnife.bind(this);

        Result clickedCharacter = (Result) getIntent().getSerializableExtra("CLICKED_CHAR");

        //set image
        Glide.with(this)
                .load(clickedCharacter.getThumbnail().getPath() + "/portrait_uncanny.jpg")
                .placeholder(R.drawable.marvellogo)
                .error(R.drawable.marvellogo)
                .crossFade() //animation
                .into(charPhoto);

        //setTile = CharacterName
        setTitle(clickedCharacter.getName());

        //Sorry...didn't get enough time to implement this fully. see the reason by running the app and opening this activity//
    }
}
