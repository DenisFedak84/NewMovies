package com.den.net.getmovie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.den.net.getmovie.GridAdapter;
import com.den.net.getmovie.R;
import com.den.net.getmovie.loader.MovieAsyncLoader;
import com.den.net.getmovie.model.MovieItem;
import com.den.net.getmovie.network.MovieNetwork;

import java.util.ArrayList;
import java.util.List;

public class GridFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {
    public static final String PATH_MOVIE = "path_movie";
    private static final int LOADER_MOVIE_ID = 1;
    private static final String LOG_TAG = "myLog";

    RecyclerView recyclerView;
    List<MovieItem> movieItemList = new ArrayList<>();
    MovieNetwork movieNetwork = new MovieNetwork();
    GridAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bndl = new Bundle();
        bndl.putString(PATH_MOVIE, movieNetwork.buildUri());
        // создаем лоадер, возвращает созданный лоадер
        getLoaderManager().initLoader(LOADER_MOVIE_ID, bndl,this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
       // recyclerView.setHasFixedSize(true);
        GridLayoutManager lm = new GridLayoutManager(getActivity(), 2, OrientationHelper.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        loadData();
        adapter = new GridAdapter(movieItemList, getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Loader<String> loader = null;
        if (id == LOADER_MOVIE_ID) {
            loader = new MovieAsyncLoader(getActivity(), args);
            loader.forceLoad();
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data != null) {
//            // use library
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            Gson gson = gsonBuilder.create();
//            movieItemList = Arrays.asList(gson.fromJson(data, MovieItem.class));

            movieItemList = movieNetwork.getMovie(data);
            GridAdapter.check = true;
            adapter.setData(movieItemList);
            adapter.notifyDataSetChanged();
        }
    }

    private void  loadData(){
        for (int i=0;i<=8;i++) {
            movieItemList.add(new MovieItem(R.drawable.image_placeholder));
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }
}
