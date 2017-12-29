package kawakuticode.com.ilks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kawakuticode.com.ilks.Model.Music;
import kawakuticode.com.ilks.R;

/**
 * Created by russeliusernestius on 27/12/17.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicViewHolder> {


    final private ListItemClickListener mOnClickListener;
    private List<Music> musicList;

    private Context mContext;


    public MusicListAdapter(List<Music> list_of_musics, Context mContext, ListItemClickListener listener) {

        mOnClickListener = listener;
        musicList = list_of_musics;
        this.mContext = mContext;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MusicListAdapter.MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.music_row;
        LayoutInflater inflater = LayoutInflater.from(getmContext());

        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);


        MusicViewHolder musicHolder = new MusicViewHolder(view);

        return musicHolder;
    }

    @Override
    public void onBindViewHolder(MusicListAdapter.MusicViewHolder holder, int position) {

        Music music = musicList.get(position);


        holder.tv_music_title.setText(music.getName());
        holder.tv_artist.setText(music.getArtist());
        holder.tv_music_length.setText(music.getLength());

        if (music.isIconChanged()) {
            holder.play_icon.setImageResource(R.drawable.sound);
        } else {
            holder.play_icon.setImageResource(android.R.drawable.ic_media_play);
        }

    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public void changeImage(int index) {
        musicList.get(index).setIconChanged(true);
        notifyItemChanged(index);

    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView play_icon;
        TextView tv_music_title, tv_artist, tv_music_length;

        public MusicViewHolder(View itemView) {

            super(itemView);
            play_icon = itemView.findViewById(R.id.ic_play_icon);
            tv_music_title = itemView.findViewById(R.id.tv_music_title);
            tv_artist = itemView.findViewById(R.id.tv_artist);
            tv_music_length = itemView.findViewById(R.id.tv_music_length);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
            //.changeImage(position)
        }
    }
}
