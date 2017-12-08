package kawakuticode.com.ilks.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import kawakuticode.com.ilks.Beans.FacebookEvent;
import kawakuticode.com.ilks.R;
import kawakuticode.com.ilks.network.NetworkUtils;
import kawakuticode.com.ilks.utilities.JsonUtilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private static final int GETEVENTS_LOADER = 23;
    private static final String GETALLEVENTS = "allevents";
    private static final String SEARCH_WEBSERVICE_URL = "events";

    private String mUrlInstance;
    private ProgressBar mLoadingIndicator;
    private List<FacebookEvent> mListEvents = new ArrayList<>();
    private JsonUtilities mJsonUtilities;

    private ImageView img_event_banner;
    private TextView tv_event_name, tv_people_going, tv_date_time, tv_location_event, tv_np_going, tv_people_interested,
            tv_np_interested, tv_more_events, tv_line_up, tv_buy_now;

    private FacebookEvent fb_event;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_WEBSERVICE_URL, mUrlInstance);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(GETEVENTS_LOADER, null, this);
        getFacebookEvents();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        tv_event_name = (TextView) view.findViewById(R.id.tv_event_name);
        tv_people_going = (TextView) view.findViewById(R.id.tv_people_going);
        tv_np_going = (TextView) view.findViewById(R.id.tv_np_going);
        tv_people_interested = (TextView) view.findViewById(R.id.tv_people_going);
        tv_np_interested = (TextView) view.findViewById(R.id.tv_np_interested);
        tv_more_events = (TextView) view.findViewById(R.id.tv_more_events);
        tv_line_up = (TextView) view.findViewById(R.id.tv_line_up);
        tv_buy_now = (TextView) view.findViewById(R.id.tv_buy_now);
        tv_date_time = (TextView) view.findViewById(R.id.tv_date_time_content);
        tv_location_event = (TextView) view.findViewById(R.id.tv_location_content);
        img_event_banner = (ImageView) view.findViewById(R.id.img_event_banner);


        tv_more_events.setOnClickListener(this);
        tv_buy_now.setOnClickListener(this);
        tv_line_up.setOnClickListener(this);

        //mLoadingIndicator = (ProgressBar) view.findViewById(R.id.pgb_event);

        return view;

    }

    private void getFacebookEvents() {

        URL getAllEventsUrl = NetworkUtils.buildUrl(GETALLEVENTS);
        mUrlInstance = getAllEventsUrl.toString();

        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_WEBSERVICE_URL, mUrlInstance);
        Loader<String> githubSearchLoader = getLoaderManager().getLoader(GETEVENTS_LOADER);
        if (githubSearchLoader == null) {
            getLoaderManager().initLoader(GETEVENTS_LOADER, queryBundle, this);
        } else {
            getLoaderManager().restartLoader(GETEVENTS_LOADER, queryBundle, this);
        }
    }

    public void loadViewsContent(String data) {

        try {
            mListEvents = JsonUtilities.parserJsonResponseFacebookEvents(data);
            if (mListEvents != null && mListEvents.size() != 0) {
                Log.d("size ", mListEvents.size() + "");
                fb_event = mListEvents.get(0);
                Picasso.with(getContext()).load(fb_event.getCover().getSource()).into(img_event_banner);
                tv_event_name.setText(fb_event.getName());
                tv_np_going.setText(String.valueOf(fb_event.getAttending_count()));
                tv_np_interested.setText(String.valueOf(fb_event.getInterested_count()));
                tv_date_time.setText(fb_event.getStart_time().toString() + "\n" + fb_event.getEnd_time());
                tv_location_event.setText(fb_event.getPlace().getLocation().getCountry() + "\n" + fb_event.getPlace().getLocation().getCity());

            } else {

                Toast.makeText(this.getContext(), "No Events to Display", Toast.LENGTH_LONG).show();
            }
/*
    img_event_banner = (ImageView) view.findViewById(R.id.img_event_banner);*/


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
                String searchQueryUrlString = args.getString(SEARCH_WEBSERVICE_URL);
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
//        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (null == data) {
            showErrorMessage();
        } else {

            loadViewsContent(data);

        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

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
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.tv_buy_now:
                if (fb_event.getTicket_uri().toString() != null) {


                    Uri ticketUri = Uri.parse(fb_event.getTicket_uri());
                    Intent buy_ticket_intent = new Intent(Intent.ACTION_VIEW, ticketUri);


                    if (buy_ticket_intent.resolveActivity(this.getActivity().getPackageManager()) != null) {
                        startActivity(buy_ticket_intent);

                    } else {

                        Toast.makeText(getContext(), " Install web browser on your cellphone ", Toast.LENGTH_SHORT).show();

                    }
                }
                break;
            case R.id.tv_line_up:

                if (fb_event.getArtists() != null && fb_event.getArtists().size() != 0) {
                    Bundle artistBundle = new Bundle();
                    android.app.Fragment ArtistFragment = new ArtistFragment();
                    artistBundle.putStringArrayList("artists", (ArrayList) fb_event.getArtists());
                    ArtistFragment.setArguments(artistBundle);
                    getFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.container, ArtistFragment)
                            .commit();
                } else {
                    Toast.makeText(getContext(), " Line up to be defined", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.tv_more_events:

                Fragment events_frag = new EventFragment();
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, events_frag)
                        .commit();

                break;


        }


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
