package com.shikhar.marvelpedia.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shikhar.marvelpedia.Activity.Adapter.SimpleFragmentPagerAdapter;
import com.shikhar.marvelpedia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.favourite_hero)
    ImageView favouriteHero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //attach adapter to ViewPager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.tab_viewpager);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //attach viewpager to TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //when a Tab is clicked or selected via scrolling horizontally
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                //adding animation to images corresponding to whatever category is selected
                Animation mAnim = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in);
                mAnim.setInterpolator(new DecelerateInterpolator());
                mAnim.setDuration(1100);

                ImageView topImage = (ImageView) findViewById(R.id.top_image);
                topImage.startAnimation(mAnim);

                int position = tab.getPosition();
                if (position == 0) {
                    topImage.setImageResource(R.drawable.characters);
                } else {
                    topImage.setImageResource(R.drawable.comics);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //do nothing
            }
        });

        //when heart icon is clicked(to show information about my favourite character)
        favouriteHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFavouriteHeroDialog();
            }
        });
    }

    //when heart icon is clicked(to show information about my favourite character)
    void showFavouriteHeroDialog() {

        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.spiderman);
        image.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("SPIDER MAN");
        alertDialog.setIcon(R.drawable.favourite);
        alertDialog.setMessage(getResources().getString(R.string.spider_man_description));
        alertDialog.setView(image);
        alertDialog.create().show();
    }
}
