package com.trekking.tatvaevents.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.trekking.tatvaevents.R;
import com.trekking.tatvaevents.adapter.EventsListAdapter;
import com.trekking.tatvaevents.adapter.EventsPageAdapter;
import com.trekking.tatvaevents.eventhandler.callbackhandler.EventItemCallback;
import com.trekking.tatvaevents.eventhandler.listener.EventListViewItemListener;
import com.trekking.tatvaevents.model.Event;

import java.util.ArrayList;

/**
 * @class EventsActivity
 * @desc {@link AppCompatActivity} class to handle event activity.
 */
public class EventsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, EventItemCallback {

    /**
     * Global data members.
     */
    private DrawerLayout drawer = null;
    private ListView m_eventListView = null;
    private ViewPager mEventViewPager = null;
    private FloatingActionButton m_callFab = null;
    private ArrayList<Event> m_eventsArrayList = null;
    private EventsListAdapter m_eveEventsListAdapter = null;
    private EventListViewItemListener m_ItemEventListener = null;

    /**
     * Global constants.
     */
    private final String TAG = "EventsActivity";

    /**
     * {@link AppCompatActivity} override methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_eventListView = (ListView) findViewById(R.id.events_list_view);
        m_callFab = (FloatingActionButton) findViewById(R.id.calling_fab);
        mEventViewPager = (ViewPager) findViewById(R.id.viewpager);

        m_eventsArrayList = new ArrayList<>();
        for (int nEventNo = 0; nEventNo < 30; nEventNo++) {
            Event event = new Event();
            event.setSno(nEventNo);
            event.setTitle("Event Title " + nEventNo);
            event.setDateTime("2017-03-25 08:00 PM");
            event.setDescription("Description:: " + nEventNo);
            event.setPlace("Place:: " + nEventNo);
            event.setPhone("Phone:: " + nEventNo);
            event.setPrice("Price:: " + nEventNo);
            event.setMapPlot("MapPlot:: " + nEventNo);
            event.setBooking("Booking:: " + nEventNo);
            event.setImage("image:: " + nEventNo);
            m_eventsArrayList.add(event);
        }

        m_ItemEventListener = new EventListViewItemListener();
        m_ItemEventListener.setCallback(this);
        m_eveEventsListAdapter = new EventsListAdapter(this, m_ItemEventListener, R.layout.item_event, m_eventsArrayList);
        m_eventListView.setAdapter(m_eveEventsListAdapter);
        m_callFab.setOnClickListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mEventViewPager.setAdapter(new EventsPageAdapter(this, m_eventsArrayList));
        mEventViewPager.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearMemory();
    }

    @Override
    public void onBackPressed() {

        closeDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.events, menu);
        return true;
    }

    /**
     * {@link View.OnClickListener} callback methods.
     */
    @Override
    public void onClick(View clickedView) {

        switch (clickedView.getId()) {
            case R.id.calling_fab:

                Snackbar.make(clickedView, "You want join this event ?", Snackbar.LENGTH_LONG).setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int currentPosition = mEventViewPager.getCurrentItem();
                        Toast.makeText(EventsActivity.this, "Event URL:" + m_eventsArrayList.get(currentPosition).getBooking(), Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;
            default:

                Toast.makeText(this, "Warning: Unhandled OnClickListener found.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * {@link AdapterView.OnItemClickListener} callback method.
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(this, "on item click listener", Toast.LENGTH_SHORT).show();
        drawer.closeDrawer(GravityCompat.START);
    }

    /**
     * {@link EventItemCallback} callback method.
     */
    @Override
    public void onEventItemClicked(View view, int tag) {

        mEventViewPager.setCurrentItem(tag);
        drawer.closeDrawers();
        Toast.makeText(this, "event item: " + tag, Toast.LENGTH_SHORT).show();
    }

    /**
     * @method closeDrawer
     * @desc Method to close the Navigation Drawer.
     */
    private void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * @method clearMemory
     * @desc Method to clear the occupied memory.
     */
    private void clearMemory() {
        removeCallbacks();
        m_callFab = null;
        m_eventsArrayList = null;
    }

    /**
     * @method removeCallbacks
     * @desc Method to remove callbacks.
     */
    private void removeCallbacks() {

    }
}
