package kawakuticode.com.ilks.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kawakuticode.com.ilks.Model.FacebookEvent;
import kawakuticode.com.ilks.R;
import kawakuticode.com.ilks.utilities.JsonUtilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListenFragment extends Fragment {

    private static final int GETLISTMUSIC_LOADER = 23;
    private static final String GETLISTMUSIC = "musicfiles";
    private static final String SEARCH_WEBSERVICE_URL = "musicfiles";

    private String mUrlInstance;
    private ProgressBar mLoadingIndicator;
    private List<String> list_music_titles = new ArrayList<>();
    private JsonUtilities mJsonUtilities;

    private ImageButton skip_left, skip_right, play;
    private ProgressBar music_progress;


    private TextView tv_music_play, tv_people_going, tv_date_time, tv_location_event, tv_np_going, tv_people_interested,
            tv_np_interested, tv_more_events, tv_line_up, tv_buy_now;

    private FacebookEvent fb_event;



    private OnFragmentInteractionListener mListener;
    private TextView textView;

    public ListenFragment() {
        // Required empty public constructor
    }


    public static ListenFragment newInstance() {
        ListenFragment fragment = new ListenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.listen_fragment, container, false);
        return view;
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
