package kawakuticode.com.ilks.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kawakuticode.com.ilks.Decoration.MusicListItemDecoration;
import kawakuticode.com.ilks.Model.Music;
import kawakuticode.com.ilks.R;
import kawakuticode.com.ilks.adapters.MusicListAdapter;
import kawakuticode.com.ilks.network.NetworkUtils;
import kawakuticode.com.ilks.utilities.JsonUtilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListenFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<String>, MusicListAdapter.ListItemClickListener, View.OnClickListener {

    private static final int GETMUSIC_LOADER = 24;
    private static final String GET_LIST_MUSIC = "musiclist";
    private static final String SEARCH_WEBSERVICE_URL = "music";

    private static final String MUSIC_BUNDLE_KEY = "musics";
    private static final String CURRENT_MUSIC_BUNDLE_KEY = "current_music";
    Music current_music = new Music();
    private RecyclerView rv_music_list;
    private MusicListAdapter musicListAdapter;
    private String mUrlInstance;
    private ProgressBar mLoadingIndicator;
    private List<Music> list_of_musics = new ArrayList<>();
    private ImageButton skip_left, skip_right, play, pause;
    private ProgressBar music_progress;
    private TextView current_music_playing, current_artist_playing;


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

        if (savedInstanceState != null) {
            mUrlInstance = savedInstanceState.getString(MUSIC_BUNDLE_KEY);
            current_music = savedInstanceState.getParcelable(CURRENT_MUSIC_BUNDLE_KEY);
        }

        getLoaderManager().initLoader(GETMUSIC_LOADER, null, this);
        getMusicList();

    }

    private void getMusicList() {

        URL getMusicListUrl = NetworkUtils.buildUrl(GET_LIST_MUSIC);

        mUrlInstance = getMusicListUrl.toString();

        Bundle queryBundle = new Bundle();
        queryBundle.putString(MUSIC_BUNDLE_KEY, mUrlInstance);


        Loader<String> githubSearchLoader = getLoaderManager().getLoader(GETMUSIC_LOADER);
        if (githubSearchLoader == null) {
            getLoaderManager().initLoader(GETMUSIC_LOADER, queryBundle, this);
        } else {
            getLoaderManager().restartLoader(GETMUSIC_LOADER, queryBundle, this);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.listen_fragment, container, false);
        mLoadingIndicator = view.findViewById(R.id.music_download_progress_bar);
        mLoadingIndicator.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);

        play = view.findViewById(R.id.bt_play_);
        play.setOnClickListener(this);


        pause = view.findViewById(R.id.bt_pause);
        pause.setOnClickListener(this);

        skip_left = view.findViewById(R.id.bt_skip_left);
        skip_left.setOnClickListener(this);
        skip_right = view.findViewById(R.id.bt_skip_right);
        skip_right.setOnClickListener(this);


        music_progress = view.findViewById(R.id.pg_music_progress);
        music_progress.setProgressTintList(ColorStateList.valueOf(Color.RED));

        current_music_playing = view.findViewById(R.id.tv_music_playing_title);
        current_artist_playing = view.findViewById(R.id.tv_music_playing_artist);

        Drawable dividerDrawable = ContextCompat.getDrawable(this.getContext(), R.drawable.music_list_divider);

        rv_music_list = view.findViewById(R.id.rv_music_list);

        rv_music_list.addItemDecoration(
                new MusicListItemDecoration(this.getContext(), dividerDrawable));


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rv_music_list.setLayoutManager(layoutManager);
        return view;
    }


    private void showJsonDataView(String data) {

        try {
            list_of_musics = JsonUtilities.parserJsonResponseMusic(data);


            if (list_of_musics != null && list_of_musics.size() != 0) {

                musicListAdapter = new MusicListAdapter(list_of_musics, getContext(), this);
                rv_music_list.setAdapter(musicListAdapter);
                musicListAdapter.notifyDataSetChanged();

                current_music = list_of_musics.get(0);
                current_music_playing.setText(current_music.getName());
                current_artist_playing.setText(current_music.getArtist());

            } else {

                Snackbar.make(getView(), "No Music to Display", Snackbar.LENGTH_LONG).show();
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
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

    @Override
    public Loader<String> onCreateLoader(int i, final Bundle bundle) {
        return new AsyncTaskLoader<String>(this.getContext()) {

            String mMusicJson;

            @Override
            protected void onStartLoading() {
                if (bundle == null) {
                    return;
                }
                mLoadingIndicator.setVisibility(View.VISIBLE);
                if (mMusicJson != null) {
                    deliverResult(mMusicJson);
                } else {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {

                /* Extract the search query from the args using our constant */
                String searchQueryUrlString = bundle.getString(MUSIC_BUNDLE_KEY);
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
                mMusicJson = webserviceJson;
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
    public void onListItemClick(int clickedItemIndex) {
        loadMusic(current_music, clickedItemIndex);
    }


    private void tToast(String s) {
        Context context = this.getContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }


    private void loadMusic(Music music, int position) {

        int previous_loaded = list_of_musics.indexOf(music);

        current_music = list_of_musics.get(position);

        current_music_playing.setText(current_music.getName());

        current_artist_playing.setText(current_music.getArtist());

        list_of_musics.get(previous_loaded).setIconChanged(false);

        musicListAdapter.notifyDataSetChanged();

        musicListAdapter.changeImage(position);

    }


    @Override
    public void onClick(View view) {

        int current_music_position;

        switch (view.getId()) {


            case R.id.bt_play_:


                // play.setImageResource(android.R.drawable.ic_media_pause);
                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);

                break;


            case R.id.bt_pause:

                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);


                break;
            case R.id.bt_skip_left:

                current_music_position = list_of_musics.indexOf(current_music);
                current_music_position--;
                if (current_music_position >= 0) {
                    loadMusic(current_music, current_music_position);
                }
                break;

            case R.id.bt_skip_right:

                current_music_position = list_of_musics.indexOf(current_music);
                current_music_position++;
                if (current_music_position <= list_of_musics.size() - 1) {
                    loadMusic(current_music, current_music_position);
                }

                break;

        }


    }

    private void showErrorMessage() {
        Toast.makeText(this.getContext(), "No Music to Display", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MUSIC_BUNDLE_KEY, mUrlInstance);
        outState.putParcelable(CURRENT_MUSIC_BUNDLE_KEY, current_music);

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
