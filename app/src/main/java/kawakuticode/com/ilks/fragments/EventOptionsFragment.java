package kawakuticode.com.ilks.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kawakuticode.com.ilks.Model.FacebookEvent;
import kawakuticode.com.ilks.Model.OptionEvent;
import kawakuticode.com.ilks.Model.Place;
import kawakuticode.com.ilks.R;
import kawakuticode.com.ilks.activities.MapsActivity;
import kawakuticode.com.ilks.adapters.OptionEventAdapter;
import kawakuticode.com.ilks.utilities.ContentDisplayUtilities;
import kawakuticode.com.ilks.utilities.DateUtilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventOptionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EventOptionsFragment extends android.app.Fragment implements OptionEventAdapter.ListOptionItemClickListener {

    private static final String KEY_EVENT = "event";
    private static final String KEY_DEEZER = "deezer";
    private static final String KEY_ARTISTS = "artists";
    private static final String KEY_FEED = "feeds";
    private static final String KEY_GALLERY = "location";
    private static final String KEY_LOCATION = "location";
    private static final String MAPS_PACKAGE = "com.google.android.apps.maps";
    private OnFragmentInteractionListener mListener;
    private TextView tv_years, tv_months, tv_days;
    private ImageView event_cover;
    private FacebookEvent mFbEvent;
    private String[] dateContent;
    private RecyclerView mDetailsReclyceView;
    private DateUtilities mDateUtilities;
    private OptionEventAdapter optionsAdapter;

    private List<OptionEvent> optionsData;

    private Toast mToast;


    public EventOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mFbEvent = bundle.getParcelable(KEY_EVENT);
            dateContent = DateUtilities.timeLeft(mFbEvent.getStart_time()).split("//");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detailsView = inflater.inflate(R.layout.fragment_event_details_fragments, container, false);
        // Inflate the layout for this fragment
        tv_years = detailsView.findViewById(R.id.tv_n_years);
        tv_months = detailsView.findViewById(R.id.tv_n_months);
        tv_days = detailsView.findViewById(R.id.tv_n_days);
        event_cover = detailsView.findViewById(R.id.event_cover_img);

        tv_years.setText(dateContent[0]);
        tv_months.setText(dateContent[1]);
        tv_days.setText(dateContent[2]);

        Picasso.with(getContext()).load(mFbEvent.getCover().getSource()).into(event_cover);

        mDetailsReclyceView = detailsView.findViewById(R.id.rvGridOptions);
        int numberOfColumns = 3;
        mDetailsReclyceView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));

        optionsData = ContentDisplayUtilities.optionContent();

        optionsAdapter = new OptionEventAdapter(this, optionsData);
        mDetailsReclyceView.setAdapter(optionsAdapter);

        return detailsView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onOptionItemClick(int clickedItemIndex) {

        String mOption = optionsData.get(clickedItemIndex).getOption();
        switch (mOption) {
            case "Line-Up":

                if (mFbEvent.getArtists() != null) {
                    Bundle artistBundle = new Bundle();
                    android.app.Fragment ArtistFragment = new ArtistFragment();
                    artistBundle.putStringArrayList(KEY_ARTISTS, (ArrayList) mFbEvent.getArtists());
                    ArtistFragment.setArguments(artistBundle);
                    getFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.container, ArtistFragment)
                            .commit();
                } else {
                    Snackbar.make(this.getView(), "Line Up not defined Yet", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case "Map":


                if (mFbEvent.getPlace() != null) {

                    Place placeEvent = mFbEvent.getPlace();
                    Intent mapIntent = new Intent(this.getActivity(), MapsActivity.class);
                    mapIntent.setPackage(MAPS_PACKAGE);
                    mapIntent.putExtra(KEY_LOCATION, placeEvent);
                    if (mapIntent.resolveActivity(this.getActivity().getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }

                } else {
                    Snackbar.make(this.getView(), "Location not available Yet", Snackbar.LENGTH_SHORT).show();

                }


                break;

            case "News":

                if (mFbEvent.getFeeds() != null) {
                    Bundle feedBundle = new Bundle();
                    android.app.Fragment feedsFrag = new FeedsFragment();
                    feedBundle.putStringArrayList(KEY_FEED, (ArrayList) mFbEvent.getFeeds());
                    feedsFrag.setArguments(feedBundle);
                    getFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.container, feedsFrag)
                            .commit();

                } else {
                    Snackbar.make(this.getView(), "No Feeds on this event", Snackbar.LENGTH_SHORT).show();
                }
                break;

            case "Photos":
                android.app.Fragment gallertyFrag = new GalleryFragment();
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, gallertyFrag)
                        .commit();
                break;

            case "Festival Info":

                Bundle eventBundle = new Bundle();
                android.app.Fragment festivalInfoFrag = new FestivalInfoFragment();
                eventBundle.putParcelable(KEY_EVENT, mFbEvent);
                festivalInfoFrag.setArguments(eventBundle);
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, festivalInfoFrag)
                        .commit();


                break;

            case "Listen":

                Bundle listenBundle = new Bundle();
                ListenFragment listenFragment = new ListenFragment();
                listenBundle.putParcelable(KEY_DEEZER, mFbEvent);
                listenFragment.setArguments(listenBundle);
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, listenFragment)
                        .commit();


                //  Snackbar.make(this.getView(), "Module to be implemented", Snackbar.LENGTH_SHORT).show();

                break;

        }




   /*     if (mToast != null) {
            mToast.cancel();
        }

        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this.getContext(), toastMessage, Toast.LENGTH_LONG);
        mToast.show();*/
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
