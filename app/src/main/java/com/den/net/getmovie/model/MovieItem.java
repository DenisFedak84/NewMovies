package com.den.net.getmovie.model;

public class MovieItem {

    public String poster_path;
    public  int photoId;
    public static final String WIDTH_500 = "w342";
    private static final String URL_IMAGE_TMDB_DEFAULT = "http://image.tmdb.org/t/p/";

    public MovieItem(int photoId) {
        this.photoId = photoId;
    }

    public String getFullPosterPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(URL_IMAGE_TMDB_DEFAULT);
        sb.append(WIDTH_500);
        sb.append(poster_path);

        return sb.toString();
    }
}
