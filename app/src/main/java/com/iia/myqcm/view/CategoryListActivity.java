package com.iia.myqcm.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.iia.myqcm.R;
import com.iia.myqcm.data.CategorySQLiteAdapter;
import com.iia.myqcm.data.QcmSQLiteAdapter;
import com.iia.myqcm.data.UserSQLiteAdapter;
import com.iia.myqcm.entity.Category;
import com.iia.myqcm.entity.Qcm;
import com.iia.myqcm.entity.User;
import com.iia.myqcm.webservice.UserWSAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class CategoryListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String CATEGORY_ID = "id";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(ConnexionActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        long userId = sharedpreferences.getLong(ConnexionActivity.USERID,0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final ListView categoriesList = (ListView) this.findViewById(R.id.categoriesList);

        CategorySQLiteAdapter categorySQLiteAdapter = new CategorySQLiteAdapter(this);
        categorySQLiteAdapter.open();

        Cursor result = categorySQLiteAdapter.getAllCursor();

        CategoryCursorAdapter adapter = new CategoryCursorAdapter(this, result, 0);

        categoriesList.setAdapter(adapter);

        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CategoryListActivity.this, QcmListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(CATEGORY_ID, id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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
        getMenuInflater().inflate(R.menu.category_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categories) {
            Intent intent = new Intent(CategoryListActivity.this, CategoryListActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_profile) {
            Intent intent = new Intent(CategoryListActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            String url = UserWSAdapter.IP + "Documentation";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }else if (id == R.id.nav_logout) {
            getSharedPreferences(ConnexionActivity.MyPREFERENCES, 0).edit().clear().apply();
            Intent i = new Intent(CategoryListActivity.this, ConnexionActivity.class);
            this.finish();
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
