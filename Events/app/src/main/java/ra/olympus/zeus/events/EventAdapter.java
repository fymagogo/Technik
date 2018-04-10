package ra.olympus.zeus.events;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.Locale;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

        private List<Event> mEventList;

        public EventAdapter(){}

        public EventAdapter(List<Event> EventList){
            this.mEventList = EventList;
        }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_event_item,parent,false);
            EventViewHolder holder = new EventViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            Event event = this.mEventList.get(position);
            Context context = holder.mContext;
            holder.mEventImageView.setImageDrawable(context.getResources().getDrawable(event.getImageID()));
            holder.mEventNameTextView.setText(event.getEventName());
            holder.mEventDateTextView.setText(event.getEventDate());
        }

        @Override
        public int getItemCount() {
            return mEventList.size();
        }

        static class EventViewHolder extends RecyclerView.ViewHolder{

            private ImageView mEventImageView;
            private TextView mEventNameTextView;
            private TextView mEventDateTextView;
            private Context mContext;

            public EventViewHolder(View itemView) {
                super(itemView);
                mContext = itemView.getContext();
                mEventImageView = itemView.findViewById(R.id.comment_image);
                mEventNameTextView = itemView.findViewById(R.id.name_of_event_text_view);
                mEventDateTextView = itemView.findViewById(R.id.logout_text_view);

            }
        }
    }
