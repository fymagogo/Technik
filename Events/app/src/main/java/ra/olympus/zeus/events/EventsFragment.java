package ra.olympus.zeus.events;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {
    private ImageView mEventImageView;
    private TextView mEventNameTextView;
    private TextView mEventDateTextView;
    private String mQuery;
    private final String mBaseQuery = "";


    public EventsFragment() {
        // Required empty public constructor
    }


    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        CardView layout = view.findViewById(R.id.container);

        RecyclerView eventRecyclerView = view.findViewById(R.id.event_recycler);
        eventRecyclerView.setHasFixedSize(true);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        EventAdapter adapter1 = new EventAdapter(DummyData.getData());
        eventRecyclerView.setAdapter(adapter1);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eventDetailIntent = new Intent(getContext(), EventDetailActivity.class);
                startActivity(eventDetailIntent);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.event_menu, android.R.layout.simple_expandable_list_item_1);
        Spinner spinner = view.findViewById(R.id.events_menu_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mQuery = mBaseQuery.concat("events");
                        SendAllEventsNetworkRequest();
                        break;
                    case 1:
                        mQuery = mBaseQuery.concat("myEvents/{Username}");
                        break;
                    case 2:
                        mQuery = mBaseQuery.concat("myinterested/{Username");
                        break;
                    case 3:
                        mQuery = mBaseQuery.concat("myattending/{Username");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);
        return view;
    }
public void SendAllEventsNetworkRequest(){


        /*EventsClient client = ServiceGenerator.createService(EventsClient.class);
        Call<List<Event>> call= client.gettingAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()){
                    List<Event> body = response.body();

                    assert body != null;
                    Picasso.with(EventsFragment.this).load(body.get(0).getImageLink()).into(mEventImageView);
                    mEventNameTextView.setText(body.get(0).getEventName());
                    mEventDateTextView.setText(body.get(0).getEventDate());

                }else{
                    Toast.makeText(EventsFragment.this, "Couldn't retrieve events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(EventsFragment.this, "Couldn't connect to server", Toast.LENGTH_SHORT).show();


            }
        });*/
}

}
