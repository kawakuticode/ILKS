package kawakuticode.com.ilks.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kawakuticode.com.ilks.Beans.FacebookEvent;
import kawakuticode.com.ilks.Beans.OptionEvent;
import kawakuticode.com.ilks.Beans.Place;
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

    private OnFragmentInteractionListener mListener;
    private static final String KEY_EVENT = "event";
    private static final String KEY_ARTISTS = "artists";
    private static final String KEY_LOCATION = "location";

    private static final String MAPS_PACKAGE = "com.google.android.apps.maps";

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
        tv_years = (TextView) detailsView.findViewById(R.id.tv_n_years);
        tv_months = (TextView) detailsView.findViewById(R.id.tv_n_months);
        tv_days = (TextView) detailsView.findViewById(R.id.tv_n_days);
        event_cover = (ImageView) detailsView.findViewById(R.id.event_cover_img);

        tv_years.setText(dateContent[0]);
        tv_months.setText(dateContent[1]);
        tv_days.setText(dateContent[2]);

        Picasso.with(getContext()).load(mFbEvent.getCover().getSource()).into(event_cover);

        RecyclerView recyclerView = (RecyclerView) detailsView.findViewById(R.id.rvGridOptions);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));

        optionsData = ContentDisplayUtilities.optionContent();

        optionsAdapter = new OptionEventAdapter(this, optionsData);
        recyclerView.setAdapter(optionsAdapter);

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
        Log.d(" axd", mOption);
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
                    Toast.makeText(getContext(), " Line up to be defined", Toast.LENGTH_SHORT).show();

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
                    //Todo
                    Toast.makeText(getContext(), " No Location available", Toast.LENGTH_SHORT).show();

                }


                break;

            case "News":

                Toast.makeText(getContext(), "To be implemented", Toast.LENGTH_SHORT).show();
                break;

            case "Photos":
                Toast.makeText(getContext(), "To be implemented", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "To be implemented", Toast.LENGTH_SHORT).show();
                break;

            case "Sponsors":
                Toast.makeText(getContext(), "To be implemented", Toast.LENGTH_SHORT).show();
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