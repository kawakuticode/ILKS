package kawakuticode.com.ilks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kawakuticode.com.ilks.Model.EventAlbum;
import kawakuticode.com.ilks.Model.PictureSource;
import kawakuticode.com.ilks.R;

/**
 * Created by russeliusernestius on 08/12/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {

    final private GalleryAdapter.ListClickListener mOnClickListener;
    List<EventAlbum> event_albums;
    private Context mContext;


    public GalleryAdapter(List<EventAlbum> event_albums, GalleryAdapter.ListClickListener mOnClickListener, Context mContext) {

        this.event_albums = event_albums;
        this.mOnClickListener = mOnClickListener;
        this.mContext = mContext;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public GalleryAdapter.GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        int layoutIdForListItem = R.layout.album_item;
        LayoutInflater inflater = LayoutInflater.from(getmContext());

        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        GalleryAdapter.GalleryHolder galleryHolder = new GalleryAdapter.GalleryHolder(view);
        return galleryHolder;

    }

    @Override
    public void onBindViewHolder(GalleryAdapter.GalleryHolder holder, int position) {

        EventAlbum album = event_albums.get(position);
        PictureSource source_cover = album.getSources().get(0);
        Log.d("album", album.getName());
        Log.d("source", source_cover.getSource());
        Picasso.with(getmContext()).load(source_cover.getSource()).into(holder.albumCover);
        holder.nameAlbum.setText(album.getName());


    }

    @Override
    public int getItemCount() {
        return event_albums.size();
    }

    public interface ListClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class GalleryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView albumCover;
        private TextView nameAlbum;


        public GalleryHolder(View view) {
            super(view);
            albumCover = view.findViewById(R.id.img_album_cover);
            nameAlbum = view.findViewById(R.id.tv_album_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
