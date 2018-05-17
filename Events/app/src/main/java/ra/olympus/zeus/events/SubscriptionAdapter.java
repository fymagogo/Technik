package ra.olympus.zeus.events;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder> {

    public SubscriptionAdapter(){}

    private List<Subscription> mSubscriptionList;

    public SubscriptionAdapter(List<Subscription> SubscriptionList){
        this.mSubscriptionList = SubscriptionList;
    }


    @Override
    public SubscriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View subscriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_subscription_item,parent,false);
        SubscriptionAdapter.SubscriptionViewHolder holder = new SubscriptionAdapter.SubscriptionViewHolder(subscriptionView);
        return holder;
    }

    @Override
    public void onBindViewHolder(SubscriptionViewHolder holder, int position) {
        Subscription subscription = mSubscriptionList.get(position);

        holder.mAddSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    static class SubscriptionViewHolder extends RecyclerView.ViewHolder {
        private FloatingActionButton mAddSubscription;
        private ImageView mSubscriptionImageView;
        private TextView mSubscriptionTextView;
        private Context mContext;

        public SubscriptionViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mAddSubscription = itemView.findViewById(R.id.fab);
            mSubscriptionImageView = itemView.findViewById(R.id.subscription_image);
            mSubscriptionTextView = itemView.findViewById(R.id.subscription_title);
        }
    }


}
