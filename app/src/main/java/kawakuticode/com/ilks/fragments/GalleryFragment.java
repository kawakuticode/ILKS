package kawakuticode.com.ilks.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kawakuticode.com.ilks.Model.EventAlbum;
import kawakuticode.com.ilks.R;
import kawakuticode.com.ilks.adapters.GalleryAdapter;
import kawakuticode.com.ilks.network.NetworkUtils;
import kawakuticode.com.ilks.utilities.JsonUtilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GalleryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class GalleryFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, GalleryAdapter.ListClickListener {


    private static final int GETALBUMS_LOADER = 0;
    private static final String GET_ALL_ALBUMS_WEBSERVICE_QUERY = "allalbums";
    private static final String ALBUMS_BUNDLE_KEY = "albums";

    private static final String EVENT_ALBUM_KEY = "album";

    private RecyclerView mAlbumsRecyclerView;
    private String mUrlInstance;

    private List<EventAlbum> mListAlbums = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private Toast mToast;
    private GalleryAdapter mGalleryAdapter;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mUrlInstance = savedInstanceState.getString(ALBUMS_BUNDLE_KEY);
        }

        getLoaderManager().initLoader(GETALBUMS_LOADER, null, this);
        getAlbumsEvent();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        mAlbumsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_grid_gallery);
        mAlbumsRecyclerView.setHasFixedSize(true);


        int numberOfColumns = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), numberOfColumns);
        mAlbumsRecyclerView.setLayoutManager(gridLayoutManager);

        return view;
    }


    private void getAlbumsEvent() {

        URL getAllEventsUrl = NetworkUtils.buildUrl(GET_ALL_ALBUMS_WEBSERVICE_QUERY);
        Log.d("My album ", getAllEventsUrl.toString());

        mUrlInstance = getAllEventsUrl.toString();

        Bundle queryBundle = new Bundle();
        queryBundle.putString(ALBUMS_BUNDLE_KEY, mUrlInstance);

        Loader<String> githubSearchLoader = getLoaderManager().getLoader(GETALBUMS_LOADER);
        if (githubSearchLoader == null) {
            getLoaderManager().initLoader(GETALBUMS_LOADER, queryBundle, this);
        } else {
            getLoaderManager().restartLoader(GETALBUMS_LOADER, queryBundle, this);
        }

    }

    private void showJsonDataView(String data) {

        try {
            mListAlbums = JsonUtilities.parserJsonResponseEventAlbums(data);

            if (mListAlbums != null && mListAlbums.size() != 0) {
                mGalleryAdapter = new GalleryAdapter(mListAlbums, this, this.getContext());
                mAlbumsRecyclerView.setAdapter(mGalleryAdapter);
                mGalleryAdapter.notifyDataSetChanged();


            } else {
                Snackbar.make(getView(), R.string.no_albums, Snackbar.LENGTH_LONG).show();
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<String>(this.getContext()) {
            String mEventsJson;

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }
                //    mLoadingIndicator.setVisibility(View.VISIBLE);
                if (mEventsJson != null) {
                    deliverResult(mEventsJson);
                } else {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {

                /* Extract the search query from the args using our constant */
                String searchQueryUrlString = args.getString(ALBUMS_BUNDLE_KEY);
                try {
                    URL webserviceUrl = new URL(searchQueryUrlString);
                    String eventsSearchResults = NetworkUtils.getResponseFromHttpUrl(webserviceUrl);
                    return eventsSearchResults;

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(String webserviceJson) {
                mEventsJson = webserviceJson;
                super.deliverResult(webserviceJson);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        //mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (null == data) {
            showErrorMessage();
        } else {
            showJsonDataView(data);
        }
    }

    private void showErrorMessage() {
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ALBUMS_BUNDLE_KEY, mUrlInstance);
    }






    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {


        SlideShowGallery slideFrag = SlideShowGallery.newInstance();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EVENT_ALBUM_KEY, mListAlbums.get(clickedItemIndex));
        slideFrag.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, slideFrag)
                .commit();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
