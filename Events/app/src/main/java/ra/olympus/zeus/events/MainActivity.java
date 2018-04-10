package ra.olympus.zeus.events;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] mPageTitles = {
            "Events",
            "Categories",
            "Search",
            "Notifications"
    };

    private int[] mTabIcons = {
            R.drawable.ic_event,
            R.drawable.ic_collections_bookmark,
            R.drawable.ic_search,
            R.drawable.ic_notifications,
    };

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mPageTitles[0]);
        }

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createEventIntent = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivity(createEventIntent);
            }
        });

       tabLayout = this.findViewById(R.id.tabs);

        ViewPager viewPager = this.findViewById(R.id.view_pager);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (getSupportActionBar() != null) {
                    toolbar.setTitle(mPageTitles[tab.getPosition()]);
                }

                switch (tab.getPosition()) {
                    case 0:
                        toolbar.setTitle(mPageTitles[tab.getPosition()]);
                        tabLayout.getTabAt(0).setIcon(mTabIcons[0]);
                        tabLayout.getTabAt(1).setIcon(mTabIcons[1]);
                        tabLayout.getTabAt(2).setIcon(mTabIcons[2]);
                        tabLayout.getTabAt(3).setIcon(mTabIcons[3]);
                        fab.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        toolbar.setTitle(mPageTitles[tab.getPosition()]);
                        tabLayout.getTabAt(0).setIcon(mTabIcons[0]);
                        tabLayout.getTabAt(1).setIcon(mTabIcons[1]);
                        tabLayout.getTabAt(2).setIcon(mTabIcons[2]);
                        tabLayout.getTabAt(3).setIcon(mTabIcons[3]);
                        fab.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        toolbar.setTitle(mPageTitles[tab.getPosition()]);
                        tabLayout.getTabAt(0).setIcon(mTabIcons[0]);
                        tabLayout.getTabAt(1).setIcon(mTabIcons[1]);
                        tabLayout.getTabAt(2).setIcon(mTabIcons[2]);
                        tabLayout.getTabAt(3).setIcon(mTabIcons[3]);
                        fab.setVisibility(View.GONE);
                        break;
                    case 3:
                        toolbar.setTitle(mPageTitles[tab.getPosition()]);
                        fab.setVisibility(View.GONE);
                        tabLayout.getTabAt(0).setIcon(mTabIcons[0]);
                        tabLayout.getTabAt(1).setIcon(mTabIcons[1]);
                        tabLayout.getTabAt(2).setIcon(mTabIcons[2]);
                        tabLayout.getTabAt(3).setIcon(mTabIcons[3]);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setupViewPager(viewPager);

        setUpWithTabIcons();
    }


    class FragmentAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(EventsFragment.newInstance());
        adapter.addFragment(CategoriesFragment.newInstance());
        adapter.addFragment(SearchFragment.newInstance());
        adapter.addFragment(NotificationFragment.newInstance());
        viewPager.setAdapter(adapter);
    }

    private void setUpWithTabIcons() {
        tabLayout.getTabAt(0).setIcon(mTabIcons[0]);
        tabLayout.getTabAt(1).setIcon(mTabIcons[1]);
        tabLayout.getTabAt(2).setIcon(mTabIcons[2]);
        tabLayout.getTabAt(3).setIcon(mTabIcons[3]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settingsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID){
            case R.id.setting_action:
                break;
        }
        return super.onOptionsItemSelected(item);
    }




}
