package ra.olympus.zeus.events.Search.Recycler.Implement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ra.olympus.zeus.events.R;

/**
 * Created by alfre on 26/04/2018.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>{

    private List<EventSearchClass> eventSearchClasses;
    private Context context;

    public EventAdapter(List<EventSearchClass> eventSearchClasses, Context context) {
        this.eventSearchClasses = eventSearchClasses;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        EventSearchClass event = eventSearchClasses.get(position);
        holder.event_name.setText(String.valueOf(event.getEventname()));
        holder.event_date.setText(String.valueOf(event.getEventdate()));
        Picasso.with(context).load(event.getMainimage()).into(holder.event_image);

    }

    @Override
    public int getItemCount() {
        return eventSearchClasses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final TextView event_name;
        private final TextView event_date;
        private final ImageView event_image;



        public MyViewHolder(View itemView) {
            super(itemView);
            event_name = (TextView) itemView.findViewById(R.id.event_name);
            event_date = (TextView) itemView.findViewById(R.id.event_date_search);
            event_image= (ImageView) itemView.findViewById(R.id.event_image_search);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
