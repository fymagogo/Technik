package ra.olympus.zeus.events;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by alfre on 13/04/2018.
 */

public class SearchableActivity extends AppCompatActivity{

    @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);

        Intent SearchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(SearchIntent.getAction())){
            String query = SearchIntent.getStringExtra(SearchManager.QUERY);
           // doMySearch(query);
        }








    }
private void doMySearch(String query){
        




}

}
