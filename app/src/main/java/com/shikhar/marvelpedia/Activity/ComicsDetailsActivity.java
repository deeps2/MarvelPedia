package com.shikhar.marvelpedia.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shikhar.marvelpedia.Activity.ModelComics.Item;
import com.shikhar.marvelpedia.Activity.ModelComics.Result;
import com.shikhar.marvelpedia.R;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComicsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.comic_cover)
    ImageView cover;
    @BindView(R.id.comic_title)
    TextView title;
    @BindView(R.id.comic_page)
    TextView pages;
    @BindView(R.id.comic_saledate)
    TextView saleDate;
    @BindView(R.id.comic_printprice)
    TextView printPrice;
    @BindView(R.id.comic_ebook_price)
    TextView eBookPrice;
    @BindView(R.id.comic_creators)
    TextView Creators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_details);
        ButterKnife.bind(this);

        Result clickedComic = (Result) getIntent().getSerializableExtra("CLICKED_COMIC");

        //set image
        Glide.with(this)
                .load(clickedComic.getThumbnail().getPath() + "/landscape_incredible.jpg")
                .placeholder(R.drawable.marvellogo)
                .error(R.drawable.marvellogo)
                .crossFade() //animation
                .into(cover);

        //set title
        if (clickedComic.getTitle() != null) {
            title.setText(clickedComic.getTitle());
            setTitle(clickedComic.getTitle());
        }

        //set No of pages
        if (clickedComic.getPageCount() != null)
            pages.setText(clickedComic.getPageCount().toString());

        //set saleDate
        try {
            if (clickedComic.getDates().get(0).getDate() != null) {

                String fullDateString = clickedComic.getDates().get(0).getDate();
                String date = fullDateString.substring(0, fullDateString.indexOf('T'));
                saleDate.setText(date);
            }
        } catch (NullPointerException n) {
            Log.e("NullException",n.toString());
        }

        //set Print Price
        try {
            if (clickedComic.getPrices().get(0).getPrice() != null)
                printPrice.setText(clickedComic.getPrices().get(0).getPrice().toString());
        } catch (NullPointerException n) {
            Log.e("NullException",n.toString());
        }


        //set Digital Price
        try {
            if (clickedComic.getPrices().get(1).getPrice() != null)
                eBookPrice.setText(clickedComic.getPrices().get(1).getPrice().toString());
        } catch (NullPointerException n) {
            Log.e("NullExc", n.toString());
        } catch (IndexOutOfBoundsException i) {
            Log.e("IndexExc",i.toString());
        }

        //get creators list
        try {

            if (clickedComic.getCreators().getItems() != null) {

                String creatorsNames = "";
                List<Item> list = clickedComic.getCreators().getItems();

                for (Item curInstance : list) {
                    String name = curInstance.getName();
                    creatorsNames = creatorsNames + name + ", ";
                }

                if(!creatorsNames.equals("")) {
                    String listOfCreatorsNames = creatorsNames.substring(0, creatorsNames.length() - 2);
                    Creators.setText(listOfCreatorsNames);
                }
            }

        } catch (NullPointerException n) {
            Log.e("NullError", n.toString());
        } catch (IndexOutOfBoundsException i) {
            Log.e("IndexError",i.toString());
        }
    }
}
