package ra.olympus.zeus.events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    List<Notification> mNotificationList;

    public NotificationAdapter() {

    }

    public NotificationAdapter(List<Notification> notifications) {
        this.mNotificationList = notifications;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_notification_item, parent, false);
        NotificationAdapter.NotificationViewHolder holder = new NotificationViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = this.mNotificationList.get(position);
        Context context = holder.mContext;
        holder.mEventImageView.setImageDrawable(context.getResources().getDrawable(notification.getImageID()));
        holder.mNotificationTextView.setText(notification.getNotificationText());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {

        private ImageView mEventImageView;
        private TextView mNotificationTextView;
        private Context mContext;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mEventImageView = itemView.findViewById(R.id.event_image);
            mNotificationTextView = itemView.findViewById(R.id.notificationText);
        }
    }
}