package com.shikhar.marvelpedia.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.shikhar.marvelpedia.Activity.Adapter.SimpleFragmentPagerAdapter;
import com.shikhar.marvelpedia.BuildConfig;
import com.shikhar.marvelpedia.R;

//TODO
// no net(nodataornet vala hee)
// no data found(when wrong text entered for search na)(nodataornet vala hee JUST CHANGE THE TEXT BELOW IT)
// network_error(toast)


// picasso - error,placeholder(nodataornet,marvel_logo)

//see glide notepad

//see retorofit me 100 limit dalne se data aa raha hai na??othewise 90,80 ....

public class MainActivity extends AppCompatActivity {

    private static String API_KEY = BuildConfig.API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //attach adapter to viewpager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.tab_viewpager);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //attach viewpager to tablayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //when a tab is clicked or selected via scrolling horizontally
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
    }
}
