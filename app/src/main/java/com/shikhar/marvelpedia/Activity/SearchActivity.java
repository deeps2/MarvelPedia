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

        //get the bundle and getString("Fragment"). It will tell whether to add CharsFragment or ComicsFragment
        Bundle bundle = getIntent().getExtras();
        String whichFragment = bundle.getString("Fragment");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //add CharsFragment
        if (whichFragment.equals("Chars")) {
            charsFragment = new CharsFragment();
            fragmentTransaction.add(R.id.search, charsFragment);
            charsFragment.setArguments(bundle); //pass bundle from activity to CharsFragment so that search string can be extracted inside CharsFragment
        } else {
            //add ComicsFragment
            comicsFragment = new ComicsFragment();
            fragmentTransaction.add(R.id.search, comicsFragment);
            comicsFragment.setArguments(bundle); ////pass bundle from activity to ComicsFragment so that search string can be extracted inside ComicsFragment
        }

        fragmentTransaction.commit();
    }
}
