package ra.olympus.zeus.events;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment{

    private List<EventSearchClass> eventSearchClassList = new ArrayList<>();
    private EventAdapter eventAdapter;
    private View itemView;
    private RecyclerView recyclerView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = itemView.findViewById(R.id.searchRecyclerView);

        eventAdapter = new EventAdapter(eventSearchClassList,getContext());
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(eLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eventAdapter);

        prepareEvents();

        return itemView;
    }

    private void prepareEvents() {
        for(int i=0; i< 30; i++){
            eventSearchClassList.add(new EventSearchClass("Event" + i, "10-2-18", ""));
        }
        eventAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);


    }


}
