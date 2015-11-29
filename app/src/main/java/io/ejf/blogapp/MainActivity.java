package io.ejf.blogapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
//        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView wv;
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        wv = (WebView) findViewById(R.id.webview);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.startsWith("javascript:"))
                    return;

                wv.loadUrl("javascript:function killBar(){document.getElementsByClassName('sidebar')[0].style.display='none'}; killBar();");
                // Define a title for your current page, shown in autocompletion UI
                String title = view.getTitle();
                Uri webUri = Uri.parse(url);
                Uri appUri = Uri.parse("android-app://io.ejf.blogapp/https/" + webUri.getHost() + webUri.getPath());
                // Construct the Action performed by the user
                Action viewAction = Action.newAction(Action.TYPE_VIEW, title, webUri, appUri);
                // Call the App Indexing API start method after the view has completely rendered
                AppIndex.AppIndexApi.start(mClient, viewAction);

            }
        });
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }


    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        if (Intent.ACTION_VIEW.equals(action) && null != data) {
            wv.loadUrl(data.toString());
        }
    }

    @Override
    public void onStop() {
        // Call end() and disconnect the client
        // Define a title for your current page, shown in autocompletion UI
        String title = wv.getTitle();
        if (null != wv.getUrl() && null != wv.getUrl()) {
            Uri webUri = Uri.parse(wv.getUrl());
            Uri appUri = Uri.parse("android-app://io.ejf.blogapp/https/" + webUri.getHost() + webUri.getPath());

            Action viewAction = Action.newAction(Action.TYPE_VIEW, title, webUri, appUri);
            AppIndex.AppIndexApi.end(mClient, viewAction);
            mClient.disconnect();
        }
        super.onStop();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        wv = (WebView) findViewById(R.id.webview);
//        wv.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (!url.startsWith("javascript:"))
//                    wv.loadUrl("javascript:function killBar(){document.getElementsByClassName('sidebar')[0].style.display='none'}; killBar();");
//            }
//        });
//        WebSettings webSettings = wv.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
