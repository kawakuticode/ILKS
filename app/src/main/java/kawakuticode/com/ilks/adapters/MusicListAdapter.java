package kawakuticode.com.ilks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kawakuticode.com.ilks.Model.Music;

/**
 * Created by russeliusernestius on 27/12/17.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicViewHolder> {


    final private ListItemClickListener mOnClickListener;
    private List<Music> musicList;


    public MusicListAdapter(List<Music> list_of_musics, ListItemClickListener listener) {

        mOnClickListener = listener;
        musicList = list_of_musics;
    }

    @Override
    public MusicListAdapter.MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MusicListAdapter.MusicViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public MusicViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
