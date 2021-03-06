package com.bettadapur.ruseandroid.ui.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bettadapur.ruseandroid.R;
import com.bettadapur.ruseandroid.dagger.ApplicationComponent;
import com.bettadapur.ruseandroid.dagger.DaggerApplicationComponent;
import com.bettadapur.ruseandroid.dagger.RuseModule;
import com.bettadapur.ruseandroid.eventing.OpenAlbumRequest;
import com.bettadapur.ruseandroid.eventing.OpenArtistRequest;
import com.bettadapur.ruseandroid.ui.fragments.AlbumDetailFragment;
import com.bettadapur.ruseandroid.ui.fragments.ArtistDetailFragment;
import com.bettadapur.ruseandroid.ui.fragments.NowPlayingFragment;
import com.bettadapur.ruseandroid.ui.fragments.SearchFragment;
import com.hannesdorfmann.mosby.MosbyActivity;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends MosbyActivity implements SearchView.OnQueryTextListener {

    @Bind(R.id.slidingLayout)
    SlidingUpPanelLayout slidingLayout;

    @Inject
    Bus bus;

    private SearchFragment searchFragment;
    private NowPlayingFragment nowPlayingFragment;
    private boolean isPanelOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ApplicationComponent component = DaggerApplicationComponent.builder().ruseModule(new RuseModule(this)).build();
        component.inject(this);
        bus.register(this);

        if(savedInstanceState == null)
        {
            searchFragment = new SearchFragment();
            nowPlayingFragment = new NowPlayingFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, searchFragment)
                    .add(R.id.dragView, nowPlayingFragment)
                    .commit();
        }

        slidingLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {

            }

            @Override
            public void onPanelCollapsed(View view) {
                nowPlayingFragment.setExpanded(false);
                isPanelOpen = false;
            }

            @Override
            public void onPanelExpanded(View view)
            {
                isPanelOpen = true;
                nowPlayingFragment.setExpanded(true);
            }

            @Override
            public void onPanelAnchored(View view) {

            }

            @Override
            public void onPanelHidden(View view) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        InputMethodManager inputManager =
                (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
        searchFragment.performSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void setSlideView(View v)
    {
        slidingLayout.setDragView(v);
    }

    @Subscribe public void onOpenAlbum(OpenAlbumRequest request)
    {
        /*if(slidingLayout.isActivated())
            slidingLayout.setActivated(false);*/

        if(slidingLayout.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED)
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        runOnUiThread(()-> {
            request.getDialog().hide();
            Log.i("MainActivity", "Opening album " + request.getAlbum().getName());
            AlbumDetailFragment albumDetailFragment = new AlbumDetailFragment();
            albumDetailFragment.updateAlbum(request.getAlbum()); //updated album

            //set fragment to main
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_out, R.anim.fade_in)
                    .replace(R.id.fragmentContainer, albumDetailFragment)
                    .addToBackStack("")
                    .commitAllowingStateLoss();
            //toolbar.setVisibility(View.GONE);

        });
    }

    @Subscribe public void onOpenArtist(OpenArtistRequest request)
    {
        if(slidingLayout.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED)
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        runOnUiThread(()->{
            request.getDialog().hide();
            Log.i("MainActivity", "Opening artist " + request.getArtist().getName());
            ArtistDetailFragment artistDetailFragment = new ArtistDetailFragment();
            artistDetailFragment.updateArtist(request.getArtist());

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.fragmentContainer, artistDetailFragment)
                    .addToBackStack("")
                    .commitAllowingStateLoss();
        });
    }

    @Override
    public void onBackPressed() {
        if(slidingLayout.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED)
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        else {
            super.onBackPressed();
        }
    }
}
