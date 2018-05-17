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
    private List<Event> mEventList;
    private EventAdapter adapter1;
    private android.support.v7.widget.RecyclerView eventRecyclerView;

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

         mEventList = new java.util.ArrayList<>();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_categories, container, false);

        mRetrofitBuilder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        final EventsClient client = mRetrofitBuilder.create(EventsClient.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.category_types,android.R.layout.simple_expandable_list_item_1);
        Spinner spinner = view.findViewById(R.id.events_category_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch(position){
//                    case 0: //religion
 //                      mEventsCategoryCall = client.getEventsByCategory(3);
//                        requestEventsByCategory(mEventsCategoryCall);
//                        break;
//                    case 1: //entertainment
//                        mEventsCategoryCall = client.getEventsByCategory("Entertainment");
//                        requestEventsByCategory(mEventsCategoryCall);
//                        break;
//                    case 2: //education
//                        mEventsCategoryCall = client.getEventsByCategory(4);
//                        requestEventsByCategory(mEventsCategoryCall);
//                        break;
//                    case 3: //sports
//                        mEventsCategoryCall = client.getEventsByCategory(2);
//                        requestEventsByCategory(mEventsCategoryCall);
//                        break;
//                    case 4: //lifestyle
//                        mEventsCategoryCall = client.getEventsByCategory("Lifestyle");
//                        requestEventsByCategory(mEventsCategoryCall);
//                        break;

  //                  case 5: //others
//                        mEventsCategoryCall = client.getEventsByCategory(1);
//                        requestEventsByCategory(mEventsCategoryCall);
//                        break;


//                };

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                mEventsCategoryCall = client.getEventsByCategory("Religion");
//              requestEventsByCategory(mEventsCategoryCall);

            }
        });
        spinner.setAdapter(adapter);

        return view;
    }

//    private void requestEventsByCategory(Call<List<Event>> eventsCategoryCall){
//        eventsCategoryCall.enqueue(new Callback<List<Event>>() {
//            @Override
//            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
//                if (response.isSuccessful()) {
//                    List<Event> events = response.body();
//                    mEventList.addAll(events);
//                    adapter1 = new EventAdapter(mEventList);
//                    eventRecyclerView.setAdapter(adapter1);
//                    adapter1.notifyDataSetChanged();
//                } else {
//                    Toast.makeText(getContext(), "Couldn't retrieve events", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Event>> call, Throwable t) {
//                Toast.makeText(getContext(), "Couldn't connect to server", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//
//
//    };

}
