package kawakuticode.com.ilks.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import kawakuticode.com.ilks.Beans.FacebookEvent;
import kawakuticode.com.ilks.R;
import kawakuticode.com.ilks.adapters.FacebookEventAdapterX;
import kawakuticode.com.ilks.network.NetworkUtils;
import kawakuticode.com.ilks.utilities.JsonUtilities;


public class EventFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, FacebookEventAdapterX.ListItemClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;


    private static final int GETEVENTS_LOADER = 22;
    private static final String GET_ALL_EVENTS_WEBSERVICE_QUERY = "allevents";
    private static final String EVENTS_BUNDLE_KEY = "events";
    private JsonUtilities mJsonUtilities;

    // private FacebookEventAdapter mAdapter;
    private FacebookEventAdapterX mAdapter;
    private RecyclerView mFacebookEvents;

    private String mUrlInstance;
    private ProgressBar mLoadingIndicator;
    private List<FacebookEvent> mListEvents = new ArrayList<>();
    private Toast mToast;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EventFragment newInstance(int columnCount) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

            mUrlInstance = savedInstanceState.getString(EVENTS_BUNDLE_KEY);

        }

        getLoaderManager().initLoader(GETEVENTS_LOADER, null, this);
        getFacebookEvents();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        mLoadingIndicator = (ProgressBar) view.findViewById(R.id.pgb_event);
        mFacebookEvents = (RecyclerView) view.findViewById(R.id.list_events);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mFacebookEvents.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mFacebookEvents.setHasFixedSize(true);

        return view;
    }

    private void getFacebookEvents() {

        URL getAllEventsUrl = NetworkUtils.buildUrl(GET_ALL_EVENTS_WEBSERVICE_QUERY);
        Log.d("My Url ", getAllEventsUrl.toString());

        mUrlInstance = getAllEventsUrl.toString();

        Bundle queryBundle = new Bundle();
        queryBundle.putString(EVENTS_BUNDLE_KEY, mUrlInstance);


        Loader<String> githubSearchLoader = getLoaderManager().getLoader(GETEVENTS_LOADER);
        if (githubSearchLoader == null) {
            getLoaderManager().initLoader(GETEVENTS_LOADER, queryBundle, this);
        } else {
            getLoaderManager().restartLoader(GETEVENTS_LOADER, queryBundle, this);
        }

    }

    private void showJsonDataView(String data) {

        try {
            mListEvents = JsonUtilities.parserJsonResponseFacebookEvents(data);
            if (mListEvents != null  && mListEvents.size()!=0  ) {
                mAdapter = new FacebookEventAdapterX(mListEvents, this, this.getContext());
                mFacebookEvents.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            } else {
                Snackbar.make(getView(), "No Events to Display", Snackbar.LENGTH_LONG).show();
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    private void showErrorMessage() {
        Toast.makeText(this.getContext(), "Nothing Events to Display", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {


     /*   if (mToast != null) {
            mToast.cancel();
        }


        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this.getContext(), toastMessage, Toast.LENGTH_LONG);*/
        Fragment fragmentEventDetails = new EventOptionsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("event", mListEvents.get(clickedItemIndex));
        fragmentEventDetails.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container,fragmentEventDetails)
                .commit();
//        mToast.show();
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
                String searchQueryUrlString = args.getString(EVENTS_BUNDLE_KEY);
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

        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (null == data) {
            showErrorMessage();
        } else {
            showJsonDataView(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EVENTS_BUNDLE_KEY, mUrlInstance);
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
