package kawakuticode.com.ilks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kawakuticode.com.ilks.Beans.Artist;
import kawakuticode.com.ilks.R;

/**
 * Created by russeliusernestius on 03/11/17.
 */

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistHolder> {

    private List<Artist> mListArtists;
    final private ListClickListener mOnClickListener;


    public interface ListClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public ArtistAdapter(List<Artist> mListArtists, ListClickListener mOnClickListener) {
        this.mListArtists = mListArtists;
        this.mOnClickListener = mOnClickListener;
    }


    @Override
    public ArtistAdapter.ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.artist_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);


        ArtistHolder artistHolder = new ArtistHolder(view);
        return artistHolder;
    }

    @Override
    public void onBindViewHolder(ArtistAdapter.ArtistHolder holder, int position) {

        Artist artist = mListArtists.get(position);
        holder.tv_name_artist.setText(artist.getName());
        holder.tv_classes.setText(artist.getList_of_classes().toString());

    }

    @Override
    public int getItemCount() {
        return mListArtists.size();
    }

    public class ArtistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView artistImage;
        private TextView tv_name_artist;
        private TextView tv_classes;

        public ArtistHolder(View itemView) {
            super(itemView);
            this.artistImage = (ImageView)itemView.findViewById(R.id.img_artist);
            this.tv_name_artist = (TextView)itemView.findViewById(R.id.tv_artist_name);
            this.tv_classes = (TextView)itemView.findViewById(R.id.tv_type_of_class);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }
    }
}
