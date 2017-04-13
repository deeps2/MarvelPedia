package com.shikhar.marvelpedia.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.shikhar.marvelpedia.Activity.Fragment.CharsFragment;
import com.shikhar.marvelpedia.Activity.Fragment.ComicsFragment;
import com.shikhar.marvelpedia.R;

public class SearchActivity extends AppCompatActivity {

    CharsFragment charsFragment;
    ComicsFragment comicsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle bundle = getIntent().getExtras();
        String search = bundle.getString("Search");
        String whichFragment = bundle.getString("Fragment");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (whichFragment.equals("Chars")) {
            charsFragment = new CharsFragment();
            fragmentTransaction.add(R.id.search, charsFragment);
            charsFragment.setArguments(bundle);
        } else {
            comicsFragment = new ComicsFragment();
            fragmentTransaction.add(R.id.search, comicsFragment);
            comicsFragment.setArguments(bundle);
        }

        fragmentTransaction.commit();
    }
}
