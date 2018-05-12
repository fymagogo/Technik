package ra.olympus.zeus.events;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoriesFragment extends Fragment {
    private final String BASE_URL = "http://192.168.43.43:8000/";
    private EventAdapter mEventAdapter;

    Retrofit mRetrofitBuilder;
    private Call<List<Event>> mEventsCategoryCall;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
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
        View view =  inflater.inflate(R.layout.fragment_categories, container, false);

        mRetrofitBuilder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        final EventsClient client = mRetrofitBuilder.create(EventsClient.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.category_types,android.R.layout.simple_expandable_list_item_1);
        Spinner spinner = view.findViewById(R.id.category_types_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        mEventsCategoryCall = client.getEventsByCategory("Religion");
                        requestEventsByCategory(mEventsCategoryCall);
                        break;
                    case 1:
                        mEventsCategoryCall = client.getEventsByCategory("Entertainment");
                        requestEventsByCategory(mEventsCategoryCall);
                        break;
                    case 2:
                        mEventsCategoryCall = client.getEventsByCategory("Education");
                        requestEventsByCategory(mEventsCategoryCall);
                        break;
                    case 3:
                        mEventsCategoryCall = client.getEventsByCategory("Sports");
                        requestEventsByCategory(mEventsCategoryCall);
                        break;
                    case 4:
                        mEventsCategoryCall = client.getEventsByCategory("Lifestyle");
                        requestEventsByCategory(mEventsCategoryCall);
                        break;
                };

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);

        return view;
    }

    private void requestEventsByCategory(Call<List<Event>> eventsCategoryCall){
        eventsCategoryCall.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    List<Event> events = response.body();
                    mEventAdapter = new EventAdapter(events);
                    mEventAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Couldn't retrieve events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getContext(), "Couldn't connect to server", Toast.LENGTH_SHORT).show();


            }
        });



    };

}
