package kawakuticode.com.ilks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kawakuticode.com.ilks.Beans.Feed;
import kawakuticode.com.ilks.R;

/**
 * Created by russeliusernestius on 08/12/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {

    private List<Feed> mListFeeds;
    final private FeedAdapter.ListClickListener mOnClickListener;


    public interface ListClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public FeedAdapter(List<Feed> mListFeeds, FeedAdapter.ListClickListener mOnClickListener) {
        this.mListFeeds = mListFeeds;
        this.mOnClickListener = mOnClickListener;
    }


    @Override
    public FeedAdapter.FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.feeds_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);


        FeedAdapter.FeedHolder feedHolder = new FeedAdapter.FeedHolder(view);
        return feedHolder;
    }

    @Override
    public void onBindViewHolder(FeedAdapter.FeedHolder holder, int position) {

        Feed feed = mListFeeds.get(position);
        holder.feed_image.setImageResource(R.mipmap.news);
        holder.tv_date_feed.setText(feed.getUpdated_time().toString());
        holder.tv_feed_content.setText(feed.getMessage());

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }

    public class FeedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView feed_image;
        private TextView tv_date_feed;
        private TextView tv_feed_content;

        public FeedHolder(View itemView) {
            super(itemView);
            this.feed_image = (ImageView) itemView.findViewById(R.id.img_news);
            this.tv_date_feed = (TextView) itemView.findViewById(R.id.tv_date_time_feed);
            this.tv_feed_content = (TextView) itemView.findViewById(R.id.tv_feed_content);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }
    }
}
