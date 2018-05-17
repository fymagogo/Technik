package ra.olympus.zeus.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventsFragment extends Fragment {
    private final String BASE_URL = "http://192.168.43.43:8000/";
    private EventAdapter mEventAdapter;

    Retrofit mRetrofitBuilder;
    private Call<List<Event>> mEventsCall;
    private List<Event> mEventList;
    private EventAdapter adapter1;
    private RecyclerView eventRecyclerView;

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

        mEventList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_events, container, false);
        eventRecyclerView = view.findViewById(R.id.event_recycler);
        eventRecyclerView.setHasFixedSize(true);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        adapter1 = new EventAdapter(DummyData.getData());

        mRetrofitBuilder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        final EventsClient client = mRetrofitBuilder.create(EventsClient.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.event_menu, android.R.layout.simple_expandable_list_item_1);
        Spinner spinner = view.findViewById(R.id.events_menu_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mEventsCall = client.getAllEvents();
                        requestEvents(mEventsCall);
                        break;
                    case 1:
                        mEventsCall = client.getMyEvents("");
                        requestEvents(mEventsCall);
                        break;
                    case 2:
                        mEventsCall = client.getInterestedEvents("");
                        requestEvents(mEventsCall);
                        break;
                    case 3:
                        mEventsCall = client.getAttendingEvents("");
                        requestEvents(mEventsCall);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mEventsCall = client.getAllEvents();
                requestEvents(mEventsCall);
            }
        });
        spinner.setAdapter(adapter);
        mEventsCall = client.getAllEvents();
        requestEvents(mEventsCall);

        return view;
    }

    private void requestEvents(Call<List<Event>> eventsCall) {
        eventsCall.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(getContext(), "Events Fragment", Toast.LENGTH_SHORT).show();
                    List<Event> events = response.body();
                    mEventList.addAll(events);
                    adapter1 = new EventAdapter(mEventList);
                    eventRecyclerView.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
//                    mEventAdapter = new EventAdapter(events);
//                    mEventAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Couldn't retrieve events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getContext(), "Couldn't connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
