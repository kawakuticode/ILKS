package kawakuticode.com.ilks.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kawakuticode.com.ilks.Beans.FacebookEvent;
import kawakuticode.com.ilks.R;
import kawakuticode.com.ilks.utilities.ContentDisplayUtilities;
import kawakuticode.com.ilks.utilities.DateUtilities;

/**
 * Created by russeliusernestius on 29/10/17.
 */

public class FacebookEventAdapter extends RecyclerView.Adapter<FacebookEventAdapter.EventViewHolder> {


    final private ListItemClickListener mOnClickListener;
    private List<FacebookEvent> mEvents;
    private DateUtilities mDateUtilis;
    private ContentDisplayUtilities mContentDisplayUtilities;


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public FacebookEventAdapter(List<FacebookEvent> list_of_events, ListItemClickListener listener) {
        mOnClickListener = listener;
        mEvents = list_of_events;
    }

    @Override
    public FacebookEventAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.fragment_event_x;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        EventViewHolder viewHolder = new EventViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FacebookEventAdapter.EventViewHolder holder, int position) {
        FacebookEvent event = mEvents.get(position);
Log.d("EVENT ..." , event.toString());
        holder.tv_party_name.setText(event.getName());
        DateUtilities.timeLeft(event.getStart_time());
        holder.tv_price.setText(event.getTicket_uri());
        setPlaceOnHolder(event, holder);
        setDateOnHolder(event, holder);


    }

    public void setPlaceOnHolder(FacebookEvent event, FacebookEventAdapter.EventViewHolder holder) {

        if (event.getPlace() != null) {
//            Log.d("ADAPTER", event.getName() + " ---> " + event.getStart_time().toString());

            String place = ContentDisplayUtilities.truncatePlaceName(event.getPlace().getName_place());
            holder.tv_place.setText(place);
        } else {
            holder.tv_place.setText("Place to be announced");
        }

    }

    public void setDateOnHolder(FacebookEvent event, FacebookEventAdapter.EventViewHolder holder) {


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

        TextView tv_party_name, tv_date, tv_place, tv_price;

        public EventViewHolder(View itemView) {
            super(itemView);

            tv_party_name = (TextView) itemView.findViewById(R.id.tv_name_party);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date_party);
            tv_place = (TextView) itemView.findViewById(R.id.tv_place_party);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price_party);
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
