package kawakuticode.com.ilks.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


    String Application_name = "ilksplayer";
    String API_key = "034afb48c5b1e487940e2403e450b957";
    String Shared_secret = "f12e855881fd7fb7231d21a995866d1b";

    String username = "kawakuticode";
    String password = "k4w4kuti#";

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

        textView = (TextView) view.findViewById(R.id.tv_deezer);


        connectToLastFm();

        // getSessionBtn.setOnClickListener(mOnGetSessionKey);

        AccountManager am = AccountManager.get(this.getContext());
        Account[] accounts = am.getAccountsByType(username);
        if (accounts.length > 0) {
            textView.setText("Last.fm account: " + accounts[0].name);
        } else {
            textView.setText("No Last.fm account configured!");
        }

        return view;
    }

    private void connectToLastFm() {

        AccountManager am = AccountManager.get(getContext());
        Account[] accounts = am.getAccountsByType(username);
        if (accounts.length > 0) {
            Bundle options = new Bundle();
            //This is a test key. Register your own at http://www.last.fm/api
            options.putString("api_key", API_key);
            options.putString("api_secret", Shared_secret);
            am.getAuthToken(accounts[0], "", options, getActivity(), new AccountManagerCallback<Bundle>() {
                public void run(AccountManagerFuture<Bundle> arg0) {
                    try {
                        String key = arg0.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                        Log.d("key ", key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        }
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
