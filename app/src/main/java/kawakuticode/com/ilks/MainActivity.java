package kawakuticode.com.ilks;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import kawakuticode.com.ilks.fragments.AboutFragment;
import kawakuticode.com.ilks.fragments.ContactsFragment;
import kawakuticode.com.ilks.fragments.EventFragment;
import kawakuticode.com.ilks.fragments.GalleryFragment;
import kawakuticode.com.ilks.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener, EventFragment.OnFragmentInteractionListener, GalleryFragment.OnFragmentInteractionListener, ContactsFragment.OnFragmentInteractionListener, AboutFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    /*    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //FragmentManager fm = getSupportFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Fragment home_frag = new HomeFragment();
             getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, home_frag)
                    .commit();

            // Handle the camera action
        } else if (id == R.id.nav_events) {

            Fragment events_frag = new EventFragment();

            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, events_frag)
                    .commit();

        } else if (id == R.id.nav_gallery) {

            Fragment gallery_frag = new GalleryFragment();
            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, gallery_frag)
                    .commit();

        } else if (id == R.id.nav_contacts) {

            Fragment contacts_frag = new ContactsFragment();
            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container,contacts_frag)
                    .commit();

        } else if (id == R.id.nav_about) {

            Fragment about_frag = new AboutFragment();
            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, about_frag)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
