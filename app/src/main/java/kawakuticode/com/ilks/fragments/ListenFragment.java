package kawakuticode.com.ilks.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.deezer.sdk.model.Permissions;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.network.connect.event.DialogListener;

import kawakuticode.com.ilks.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView textView;
    String applicationID = "263482";

    String Name = "ilksplayer";
    String Secret_Key = "3c2d67bab0cf5a6cdc424de177d3fd5e";
    // The set of Deezer Permissions needed by the app
    String[] permissions = new String[]{
            Permissions.BASIC_ACCESS,
            Permissions.OFFLINE_ACCESS};

    DeezerConnect deezerConnect;


    private OnFragmentInteractionListener mListener;

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
        deezerConnect = new DeezerConnect(this.getContext(), applicationID);
        connectToDeezer();
        textView = (TextView) view.findViewById(R.id.tv_deezer);

        return view;


    }

    private void connectToDeezer() {
        deezerConnect.authorize(this.getActivity(), permissions, mDeezerDialogListener);
    }

    /**
     * A listener for the Deezer Login Dialog
     */
    private DialogListener mDeezerDialogListener = new DialogListener() {

        @Override
        public void onComplete(final Bundle values) {
            // store the current authentication info
            SessionStore sessionStore = new SessionStore();
            sessionStore.save(deezerConnect, getContext());

            textView.setText(deezerConnect.getAccessToken());
           /* // Launch the Home activity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);*/
        }

        @Override
        public void onException(final Exception exception) {
            Toast.makeText(getContext(), R.string.deezer_error_during_login,
                    Toast.LENGTH_LONG).show();
        }


        @Override
        public void onCancel() {
            Toast.makeText(getContext(), R.string.login_cancelled, Toast.LENGTH_LONG).show();
        }


    };

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
