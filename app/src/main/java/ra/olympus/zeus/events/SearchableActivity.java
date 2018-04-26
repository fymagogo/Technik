package ra.olympus.zeus.events;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alfre on 13/04/2018.
 */

public class SearchableActivity extends AppCompatActivity{

    private List<EventSearchClass> eventSearchClassList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;

    @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);


        Intent SearchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(SearchIntent.getAction())){
           String query = SearchIntent.getStringExtra(SearchManager.QUERY);
           // doMySearch(query);
            prepareEvents();
        }








    }

    private void prepareEvents() {
        for(int i=0; i< 30; i++){
            eventSearchClassList.set(i, new EventSearchClass("Event" + i, "10-2-18", ""));
        }
        eventAdapter.notifyDataSetChanged();
    }




    private void doMySearch(String query){

EventHubClient client = ServiceGenerator.createService(EventHubClient.class);

    Call<List<EventSearchClass>> call = client.getSearchResults(query);

    call.enqueue(new Callback<List<EventSearchClass>>() {
        @Override
        public void onResponse(Call<List<EventSearchClass>> call, Response<List<EventSearchClass>> response) {
            if(response.isSuccessful()){
                    eventSearchClassList.addAll(response.body());
                    eventAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onFailure(Call<List<EventSearchClass>> call, Throwable t) {

        }
    });



}

}
