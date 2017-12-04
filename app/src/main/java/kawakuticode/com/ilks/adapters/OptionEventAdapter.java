package kawakuticode.com.ilks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kawakuticode.com.ilks.Beans.OptionEvent;
import kawakuticode.com.ilks.R;

/**
 * Created by russeliusernestius on 01/11/17.
 */

public class OptionEventAdapter extends RecyclerView.Adapter<OptionEventAdapter.OptionViewHolder> {


    final private OptionEventAdapter.ListOptionItemClickListener mOnClickListener;
    private List<OptionEvent> mOptions;

    public interface ListOptionItemClickListener {
        void onOptionItemClick(int clickedItemIndex);
    }

    public OptionEventAdapter(ListOptionItemClickListener mOnClickListener, List<OptionEvent> mOptions) {
        this.mOnClickListener = mOnClickListener;
        this.mOptions = mOptions;
    }

    @Override
    public OptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.grid_details_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        OptionEventAdapter.OptionViewHolder viewHolder = new OptionViewHolder(view);

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(OptionViewHolder holder, int position) {
        OptionEvent optEvent = mOptions.get(position);

        holder.icon.setImageResource(optEvent.getImgSource());
        holder.tv_options.setText(optEvent.getOption());
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    public class OptionViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {


        private ImageView icon;
        private TextView tv_options;

        public OptionViewHolder(View itemView)  {
            super(itemView);
            icon = (ImageView)itemView.findViewById(R.id.grid_icon);
            tv_options = (TextView)itemView.findViewById(R.id.tv_option);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onOptionItemClick(clickedPosition);
        }
    }


}
