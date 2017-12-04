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
 * {@link EventDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EventDetailsFragment extends android.app.Fragment implements OptionEventAdapter.ListOptionItemClickListener {

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


    public EventDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mFbEvent = bundle.getParcelable("event");
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
                    artistBundle.putStringArrayList("artists", (ArrayList) mFbEvent.getArtists());
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


                 /*
                    StringBuilder builder = new StringBuilder();
                    builder.append("geo:");
                    builder.append(placeEvent.getLocation().getLatitude());
                    builder.append(",");
                    builder.append(placeEvent.getLocation().getLongitude());
                    builder.append("?z=");
                    builder.append(10);

                    Bundle placeBundle = new Bundle();
                    android.app.Fragment festivalMapFragment = new MapsActivity();
                    placeBundle.putString("location",builder.toString());

                    getFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.container, festivalMapFragment)
                            .commit();

                   Uri gmmIntentUri = Uri.parse(builder.toString());
                     Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri); */

                    Intent mapIntent = new Intent(this.getActivity(), MapsActivity.class);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    mapIntent.putExtra("location", placeEvent);
                    if (mapIntent.resolveActivity(this.getActivity().getPackageManager()) != null) {
                        startActivity(mapIntent);

                        // Toast.makeText(getContext(), builder.toString(), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getContext(), " No Location ", Toast.LENGTH_SHORT).show();

                }


                break;

            case "News":

                Toast.makeText(getContext(), "To be implemented", Toast.LENGTH_SHORT).show();
                break;

            case "Photos":
                Toast.makeText(getContext(), "To be implemented", Toast.LENGTH_SHORT).show();
                break;

            case "Festival Info":
                Toast.makeText(getContext(), "To be implemented", Toast.LENGTH_SHORT).show();
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
