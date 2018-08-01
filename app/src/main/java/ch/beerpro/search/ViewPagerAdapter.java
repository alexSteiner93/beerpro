package ch.beerpro.search;

import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "ViewPagerAdapter";

    private Fragment searchSuggestionsFragment;
    private Fragment searchResultFragment;
    private Fragment myBeersFragment;

    private boolean showSuggestions = true;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        searchSuggestionsFragment = new SearchSuggestionsFragment();
        searchResultFragment = new SearchResultFragment();
        myBeersFragment = new MyBeersFragment();
    }

    public void setShowSuggestions(boolean showSuggestions) {
        this.showSuggestions = showSuggestions;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (showSuggestions) {
                    Log.i(TAG, "Showing suggestions");
                    return searchSuggestionsFragment;
                } else {
                    Log.i(TAG, "Showing results");
                    return searchResultFragment;
                }
            case 1:
                Log.i(TAG, "Showing my beers");
                return myBeersFragment;
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ALLE BIERE";
            case 1:
                return "MEINE BIERE";
        }
        return null;
    }
}