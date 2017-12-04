package sk.stuba.fei.uamt.e_shop;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static sk.stuba.fei.uamt.e_shop.R.id.search;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final int REQUEST_CODE_LOGIN = 1;
    final int REQUEST_CODE_REGISTRATION = 2;
    UIChanger uiChanger;
    ProductsToShowTask mProductsToShowTask;
    private String userEmail;
    private LinearLayout products;
    private ProgressBar progressBar;
    private String searchterm = "";
    SearchView searchView = null;
    private SimpleCursorAdapter myAdapter;
    private  ArrayList<String> dataList;

    final String[] from = new String[] {"fishName"};
    final int[] to = new int[] {android.R.id.text1};
    private String[] strArrData = {"No Suggestions"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        handleIntent(getIntent());

        myAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        uiChanger = new UIChanger(navigationView);
        products = (LinearLayout) findViewById(R.id.products);
        progressBar = (ProgressBar) findViewById(R.id.products_progress);
        mProductsToShowTask = new ProductsToShowTask(this,uiChanger,products, progressBar, userEmail, searchterm);
        mProductsToShowTask.execute((Void)null);


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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // adds item to action bar
       getMenuInflater().inflate(R.menu.main, menu);

        // Get Search item from action bar and Get Search service
        final MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setIconified(false);
            searchView.setSuggestionsAdapter(myAdapter);
            // Getting selected (clicked) item suggestion
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    searchterm = "";
                    reloadChanges(userEmail);
                    return true;
                }
            });

            searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionClick(int position) {

                    // Add clicked text to search box
                    CursorAdapter ca = searchView.getSuggestionsAdapter();
                    Cursor cursor = ca.getCursor();
                    cursor.moveToPosition(position);
                    searchView.setQuery(cursor.getString(cursor.getColumnIndex("fishName")),false);
                    searchterm = cursor.getString(cursor.getColumnIndex("fishName"));
                    reloadChanges(userEmail);
                    return true;
                }

                @Override
                public boolean onSuggestionSelect(int position) {

                    return true;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    // Filter data
                    final MatrixCursor mc = new MatrixCursor(new String[]{ BaseColumns._ID, "fishName" });
                    strArrData = mProductsToShowTask.dataList.toArray(new String[mProductsToShowTask.dataList.size()]);
                    for (int i=0; i<strArrData.length; i++) {
                        if (strArrData[i].toLowerCase().startsWith(s.toLowerCase()))
                            mc.addRow(new Object[] {i, strArrData[i]});
                    }
                    myAdapter.changeCursor(mc);
                    return false;
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_registration) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivityForResult(intent, REQUEST_CODE_REGISTRATION);
        } else if (id == R.id.nav_signIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_CODE_LOGIN);
        } else if ( id == R.id.nav_sign_out){
            uiChanger.changeUISignout();
            userEmail = null;
            reloadChanges(userEmail);
        } else if (id == R.id.nav_my_orders){
            Intent intent = new Intent(this, MyOrdersActivity.class);
            intent.putExtra("userEmail", userEmail);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            searchterm = query;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_LOGIN) {
            if (data.hasExtra("name") && data.hasExtra("email")) {
                uiChanger.changeUISignIn(data);
                userEmail = data.getStringExtra("email");
                reloadChanges(userEmail);
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_REGISTRATION){
            if (data.hasExtra("name") && data.hasExtra("email")) {
                uiChanger.changeUISignIn(data);
                userEmail = data.getStringExtra("email");
                reloadChanges(userEmail);
            }
        }
    }

    private void reloadChanges(String userEmail){
        products.removeAllViewsInLayout();
        mProductsToShowTask = new ProductsToShowTask(this,uiChanger,products, progressBar, userEmail, searchterm);
        mProductsToShowTask.execute((Void)null);
    }

}
