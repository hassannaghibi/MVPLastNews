package ir.ncompany.lastnews.main.news;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ir.ncompany.lastnews.LastNewsMVPApplication;
import ir.ncompany.lastnews.R;
import ir.ncompany.lastnews.model.ModelNewsSport;
import ir.ncompany.lastnews.utiles.Utiles;

public class NewsActivity extends AppCompatActivity implements NewsView, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private long date;
    private Dialog dialog_loading;
    private NewsPresenter newsPresenter;
    private boolean isSport = true;

    @InjectView(R.id.constrant_rc)
    RecyclerView constrant_rc;
    @InjectView(R.id.container_main)
    View rootview;
    @InjectView(R.id.constrant_rel_nodata)
    RelativeLayout constrant_rel_nodata;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer;
    @InjectView(R.id.main_img_drawer)
    ImageView mainImgDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        mainImgDrawer.setOnClickListener(this);
        fab.setOnClickListener(this);

        newsPresenter = new NewsPresenterImpl(this);
        showDialogLoading();
        newsPresenter.sendOrGetDataFromServer("getNews", rootview);

        constrant_rc.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });
    }

    @Override
    public void LoadingShow() {
        dialog_loading.show();
    }

    @Override
    public void LoadingDismiss() {
        dialog_loading.dismiss();
    }

    @Override
    public void noData(boolean status) {
        if (status) {
            constrant_rel_nodata.setVisibility(View.VISIBLE);
            constrant_rc.setVisibility(View.GONE);
        } else {
            constrant_rel_nodata.setVisibility(View.GONE);
            constrant_rc.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showNoNet() {
        final Dialog dialog = new Dialog(NewsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_no_net);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id

        Button settingBtn = (Button) dialog.findViewById(R.id.dialog_btn_setting);
        Button tryBtn = (Button) dialog.findViewById(R.id.dialog_btn_refresh);

        tryBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        android.provider.Settings.ACTION_WIFI_SETTINGS));
            }

        });

        settingBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                newsPresenter.sendOrGetDataFromServer("getNews", rootview);
            }
        });
        ImageView exitBtn = (ImageView) dialog.findViewById(R.id.dialog_img_close);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void setRecyclerNews(ArrayList<ModelNewsSport> mobileNumber) {
        if (mobileNumber.size() > 0) {
            NewsAdapter newsAdapter = new NewsAdapter(mobileNumber);
            constrant_rc.setHasFixedSize(false);
            LinearLayoutManager llm = new LinearLayoutManager(LastNewsMVPApplication.context);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            constrant_rc.setLayoutManager(llm);
            constrant_rc.setAdapter(newsAdapter);
        } else {
            Utiles.Log("no suite");
        }
    }

    private void showDialogLoading() {

        dialog_loading = new Dialog(NewsActivity.this);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.dialog_loading);
        dialog_loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_loading.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    public void OpenCloseDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_img_drawer: {
                OpenCloseDrawer();
            }
            break;
            case R.id.fab: {
                if (isSport) {
                    isSport = false;
                    newsPresenter.sendOrGetDataFromServer("getSport", rootview);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_news_white));
                } else {
                    isSport = true;

                    newsPresenter.sendOrGetDataFromServer("getNews", rootview);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_sport_white));
                }            }
            break;
        }
    }
}
