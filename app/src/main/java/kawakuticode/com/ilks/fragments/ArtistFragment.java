package kawakuticode.com.ilks.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kawakuticode.com.ilks.Model.Artist;
import kawakuticode.com.ilks.R;
import kawakuticode.com.ilks.adapters.ArtistAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArtistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ArtistFragment extends Fragment implements ArtistAdapter.ListClickListener {

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecicleViewArtists;
    private ArtistAdapter mArtistAdapter;


    private List<Artist> mListArtists;

    public ArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mListArtists = (ArrayList) bundle.getStringArrayList("artists");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View artistView = inflater.inflate(R.layout.fragment_artists, container, false);
        RecyclerView recyclerView = (RecyclerView) artistView.findViewById(R.id.rv_artist_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


         mArtistAdapter = new ArtistAdapter(mListArtists, this);
        recyclerView.setAdapter(mArtistAdapter);
        if(mListArtists.size() == 0 ) {
            Toast.makeText(this.getContext(), " Line up Defined Yet " , Toast.LENGTH_SHORT).show();
        }
        return artistView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

 /*   @Override
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
    public void onListItemClick(int clickedItemIndex) {
        Log.d("artist", mListArtists.get(clickedItemIndex).getName());
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
