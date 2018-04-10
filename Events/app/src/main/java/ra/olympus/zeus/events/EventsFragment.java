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
import android.widget.Spinner;

public class EventsFragment extends Fragment {

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
                        mQuery = mBaseQuery.concat("my_events");
                        break;
                    case 1:
                        mQuery = mBaseQuery.concat("my_events");
                        break;
                    case 2:
                        mQuery = mBaseQuery.concat("my_events");
                        break;
                    case 3:
                        mQuery = mBaseQuery.concat("my_events");
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


}
