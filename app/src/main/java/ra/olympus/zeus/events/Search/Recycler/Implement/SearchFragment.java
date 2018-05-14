package ra.olympus.zeus.events.Search.Recycler.Implement;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import ra.olympus.zeus.events.EventHubClient;
import ra.olympus.zeus.events.MainActivity;
import ra.olympus.zeus.events.R;
import ra.olympus.zeus.events.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment{

    private List<EventSearchClass> eventSearchClassList = new ArrayList<>();
    private EventAdapter eventAdapter;
    private View itemView;
    private RecyclerView recyclerView;
    private int eventID;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);


        Intent SearchIntent = getActivity().getIntent();
        if (Intent.ACTION_SEARCH.equals(SearchIntent.getAction())) {
            String query = SearchIntent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);

        }

    }


    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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

    private void doMySearch(String query) {
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);

        Call<List<EventSearchClass>> call = client.getSearchResults(query);
        call.enqueue(new Callback<List<EventSearchClass>>() {
            @Override
            public void onResponse(Call<List<EventSearchClass>> call, Response<List<EventSearchClass>> response) {
                if(response.isSuccessful()){
                    eventSearchClassList.addAll(response.body());
                    eventAdapter.notifyDataSetChanged();

                }

                else {


                    Toast.makeText(getContext(),"This event was not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventSearchClass>> call, Throwable t) {
                Toast.makeText(getContext(), "You have no connection",Toast.LENGTH_SHORT).show();

            }
        });

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        itemView = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = itemView.findViewById(R.id.searchRecyclerView);

        eventAdapter = new EventAdapter(eventSearchClassList,getContext());
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(eLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eventAdapter);



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {



            @Override
            public void onClick(View view, int position) {
                //Intent to transport user to the event selected

                EventSearchClass eventSearchClass = eventSearchClassList.get(position);
                Toast.makeText(getContext(),eventSearchClass.getEventname()+" is Selected!", Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        return itemView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                doMySearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
    }









}
