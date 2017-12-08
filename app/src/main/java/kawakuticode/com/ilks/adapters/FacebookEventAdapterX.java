package kawakuticode.com.ilks.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kawakuticode.com.ilks.Beans.FacebookEvent;
import kawakuticode.com.ilks.R;
import kawakuticode.com.ilks.utilities.ContentDisplayUtilities;
import kawakuticode.com.ilks.utilities.DateUtilities;

/**
 * Created by russeliusernestius on 29/10/17.
 */

public class FacebookEventAdapterX extends RecyclerView.Adapter<FacebookEventAdapterX.EventViewHolder> {


    final private ListItemClickListener mOnClickListener;
    private List<FacebookEvent> mEvents;
    private DateUtilities mDateUtilis;
    private Context mContext;


    private ContentDisplayUtilities mContentDisplayUtilities;


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public FacebookEventAdapterX(List<FacebookEvent> list_of_events, ListItemClickListener listener, Context context) {
        mOnClickListener = listener;
        mEvents = list_of_events;
        this.mContext = context;
    }


    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public FacebookEventAdapterX.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.event_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        EventViewHolder viewHolder = new EventViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FacebookEventAdapterX.EventViewHolder holder, int position) {
        FacebookEvent event = mEvents.get(position);
        Picasso.with(getmContext()).load(event.getCover().getSource()).into(holder.event_flyer);
        holder.tv_party_name.setText(event.getName());
        DateUtilities.timeLeft(event.getStart_time());
        holder.tv_url.setText(event.getTicket_uri());
        setPlaceOnHolder(event, holder);
        setDateOnHolder(event, holder);


    }

    public void setPlaceOnHolder(FacebookEvent event, FacebookEventAdapterX.EventViewHolder holder) {

        if (event.getPlace() != null) {
            String place = ContentDisplayUtilities.truncatePlaceName(event.getPlace().getName_place());
            holder.tv_place.setText(place);
        } else {
            holder.tv_place.setText(R.string.place_to_be_announced);
        }

    }

    public void setDateOnHolder(FacebookEvent event, FacebookEventAdapterX.EventViewHolder holder) {


        boolean status = DateUtilities.datePassed(event.getStart_time());
        holder.tv_date.setText(DateUtilities.formattDateEvent(event.getStart_time()));
        if (status == true) {
            holder.tv_date.setTextColor(Color.RED);
        }

    }


    @Override
    public int getItemCount() {
        return mEvents.size();
    }


    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_party_name, tv_date, tv_place, tv_url;
        ImageView event_flyer;

        public EventViewHolder(View itemView) {
            super(itemView);

            tv_party_name = (TextView) itemView.findViewById(R.id.name_event_tv);
            tv_date = (TextView) itemView.findViewById(R.id.date_event_tv);
            tv_place = (TextView) itemView.findViewById(R.id.place_event_tv);
            tv_url = (TextView) itemView.findViewById(R.id.link_event_tv);
            event_flyer = (ImageView) itemView.findViewById(R.id.img_event);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
            // Log.d("TAG", mEvents.get(clickedPosition).toString());
        }
    }
}
