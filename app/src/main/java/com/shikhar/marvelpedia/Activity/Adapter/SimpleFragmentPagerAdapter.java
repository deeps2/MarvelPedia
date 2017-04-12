package com.shikhar.marvelpedia.Activity.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shikhar.marvelpedia.Activity.Fragment.CharsFragment;
import com.shikhar.marvelpedia.Activity.Fragment.ComicsFragment;
import com.shikhar.marvelpedia.R;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    // fm is the fragment manager that will keep each fragment's state in the adapter across swipes.
    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new CharsFragment();
        else
            return new ComicsFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return mContext.getString(R.string.category_characters);
        else
            return mContext.getString(R.string.category_comics);
    }
}
