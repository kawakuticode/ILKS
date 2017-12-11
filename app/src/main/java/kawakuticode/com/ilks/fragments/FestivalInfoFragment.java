package kawakuticode.com.ilks.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kawakuticode.com.ilks.Model.FacebookEvent;
import kawakuticode.com.ilks.R;
import kawakuticode.com.ilks.utilities.DateUtilities;
import kawakuticode.com.ilks.utilities.JsonUtilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FestivalInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FestivalInfoFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private static final String KEY_EVENT = "event";
    private static final String KEY_ARTISTS = "artists";
    private static final String KEY_LOCATION = "location";

    private String mUrlInstance;
    private ProgressBar mLoadingIndicator;
    private List<FacebookEvent> mListEvents = new ArrayList<>();
    private JsonUtilities mJsonUtilities;

    private ImageView img_event_banner;
    private TextView tv_event_name, tv_people_going, tv_date_time, tv_location_event, tv_np_going, tv_people_interested,
            tv_np_interested, tv_more_events, tv_line_up, tv_buy_now;

    private FacebookEvent fb_event;
    private String[] dateContent;


    public FestivalInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fb_event = outState.getParcelable(KEY_EVENT);
        dateContent = DateUtilities.timeLeft(fb_event.getStart_time()).split("//");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            fb_event = bundle.getParcelable(KEY_EVENT);
            dateContent = DateUtilities.timeLeft(fb_event.getStart_time()).split("//");
        }

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
        loadViewsContent(fb_event);
        return view;

    }


    public void loadViewsContent(FacebookEvent event) {

        Picasso.with(getContext()).load(event.getCover().getSource()).into(img_event_banner);
        tv_event_name.setText(event.getName());
        tv_np_going.setText(String.valueOf(event.getAttending_count()));
        tv_np_interested.setText(String.valueOf(event.getInterested_count()));
        tv_date_time.setText(event.getStart_time().toString() + "\n" + event.getEnd_time());
        tv_location_event.setText(event.getPlace().getLocation().getCountry() + "\n" + event.getPlace().getLocation().getCity());

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

/*    @Override
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
                    Fragment ArtistFragment = new ArtistFragment();
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
