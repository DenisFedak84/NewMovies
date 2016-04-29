package com.den.net.getmovie.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.den.net.getmovie.fragment.GridFragment;
import com.den.net.getmovie.network.MovieNetwork;

import java.net.MalformedURLException;

public class MovieAsyncLoader extends AsyncTaskLoader<String> {
    public String uri;
    public String movieJsonStr;
    public MovieNetwork movieNetwork = new MovieNetwork();

    public MovieAsyncLoader(Context context, Bundle args) {
        super(context);
        uri = args.getString(GridFragment.PATH_MOVIE);
    }

    @Override
    public String loadInBackground() {
        try {
            movieJsonStr = movieNetwork.requestData(uri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return movieJsonStr;
    }
}
