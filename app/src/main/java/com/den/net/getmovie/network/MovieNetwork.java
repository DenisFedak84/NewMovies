package com.den.net.getmovie.network;

import android.net.Uri;
import android.util.Log;

import com.den.net.getmovie.model.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieNetwork {
    public final String LOG_TAG = "MyLog";
    private static final String HTTP_SCHEME = "http";
    private static final String URL_AUTHORITY = "api.themoviedb.org";
    private static final String PARAM_SORT_BY = "sort_by";
    private static final String SORT_BY = "popularity";
    private static final String API_KEY = "2b38e556f2dcda690b81a61eb2725129";
    private static final String PARAM_API_KEY = "api_key";

    private static final String KEY_RESULTS = "results";
    private static final String KEY_POSTER_PATH = "poster_path";
    public static final int DEFAULT_PORTRAIT_RESULT_LIMIT = 20;

    public String buildUri() {
        Uri uri = new Uri.Builder()
                .scheme(HTTP_SCHEME)
                .authority(URL_AUTHORITY)
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter(PARAM_SORT_BY, SORT_BY + ".desc")
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        return uri.toString();
    }

    public String requestData(String uri) throws MalformedURLException {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String movieJsonStr = null;

        try {
            URL url = new URL(uri);
            Log.d(LOG_TAG, "Uri = " + uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            if (buffer.length() == 0) {
                Log.d(LOG_TAG, "Request data = 0");
                return null;
            }
            movieJsonStr = buffer.toString();
            Log.d(LOG_TAG, "Request data = " + movieJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return movieJsonStr;

    }

    public ArrayList<MovieItem> getMovie(String movieJsonStr) {

        ArrayList<MovieItem> movies = new ArrayList<>();
        try {
            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray jsonMoviesArray = movieJson.getJSONArray(KEY_RESULTS);

            for (int i = 0; i < DEFAULT_PORTRAIT_RESULT_LIMIT; i++) {
                MovieItem movie = new MovieItem(i);
                JSONObject jsonMovie = jsonMoviesArray.getJSONObject(i);
                movie.poster_path = jsonMovie.getString(KEY_POSTER_PATH);
                movies.add(movie);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return movies;
    }

}
