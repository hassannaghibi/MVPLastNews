package ir.ncompany.lastnews.main.news;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import io.realm.Realm;
import ir.ncompany.lastnews.LastNewsMVPApplication;
import ir.ncompany.lastnews.api.RetroBaseApi;
import ir.ncompany.lastnews.api.RetroGetBBCNews;
import ir.ncompany.lastnews.api.RetroGetBBCNewsData;
import ir.ncompany.lastnews.model.ModelNewsSport;
import ir.ncompany.lastnews.realm.RealmNews;
import ir.ncompany.lastnews.realm.RealmSport;
import ir.ncompany.lastnews.realm.main.HelperCacheInteractorImpl;
import ir.ncompany.lastnews.realm.main.HelperCatchInteractor;
import ir.ncompany.lastnews.utiles.Utiles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.ncompany.lastnews.R.id.constrant_rc;

/**
 * Created by ASUS on 7/2/2017.
 */

public class NewsPresenterImpl implements NewsPresenter {

    private ArrayList<ModelNewsSport> modelNewsSports = new ArrayList<>();
    private ArrayList<RetroGetBBCNewsData> retroGetBBCNewsDatas = new ArrayList<>();
    private ArrayList<RealmNews> realmNewses = new ArrayList<>();
    private ArrayList<RealmSport> realmSports = new ArrayList<>();
    private String status;
    private Realm mRealm;
    private NewsView newsView;
    private HelperCatchInteractor helperCatchInteractor;

    public NewsPresenterImpl(NewsView newsView) {
        this.newsView = newsView;
        this.helperCatchInteractor = new HelperCacheInteractorImpl();
    }

    @Override
    public void getNews(final View rootView) {
        newsView.LoadingShow();
        RetroBaseApi retroBaseApi = LastNewsMVPApplication.retrofit.create(RetroBaseApi.class);
        final Call<RetroGetBBCNews> retroGetBBCNewsCall = retroBaseApi.bbcNews();
        final Callback<RetroGetBBCNews> retroGetBBCNewsCallback = new Callback<RetroGetBBCNews>() {
            @Override
            public void onResponse(Call<RetroGetBBCNews> call, Response<RetroGetBBCNews> response) {
                if (response.isSuccessful()) {
                    RetroGetBBCNews apiResponse = response.body();
                    if (apiResponse != null) {

                        status = apiResponse.getStatus();

                        if (status.equals("ok")) {
                            modelNewsSports.clear();
                            retroGetBBCNewsDatas = apiResponse.getRetroGetBBCNewsDatas();

                            mRealm = Realm.getInstance(LastNewsMVPApplication.configRealm);
                            mRealm.beginTransaction();

                            for (int i = 0; i < retroGetBBCNewsDatas.size(); i++) {
                                RealmNews realmNews = new RealmNews();

                                realmNews.setTitle(retroGetBBCNewsDatas.get(i).getTitle());
                                realmNews.setDescription(retroGetBBCNewsDatas.get(i).getDescription());
                                realmNews.setUrlToImage(retroGetBBCNewsDatas.get(i).getUrlToImage());
                                realmNews.setUrl(retroGetBBCNewsDatas.get(i).getUrl());
                                realmNews.setPublishedAt(retroGetBBCNewsDatas.get(i).getPublishedAt());
                                realmNews.setAuthor(retroGetBBCNewsDatas.get(i).getAuthor());

                                realmNewses.add(realmNews);
                            }
                            mRealm.delete(RealmNews.class);
                            mRealm.copyToRealmOrUpdate(realmNewses);
                            mRealm.commitTransaction();

                            /*set recycler*/
                            modelNewsSports = helperCatchInteractor.getNews();
                            newsView.setRecyclerNews(modelNewsSports);

                            /*check data*/
                            if (modelNewsSports.size() > 0) {
                                newsView.noData(false);
                            } else {
                                newsView.noData(true);
                            }
                            newsView.LoadingDismiss();

                        } else {
                            newsView.LoadingDismiss();

                            Snackbar.make(rootView, "problem in api try again", Snackbar.LENGTH_LONG)
                                    .setAction("problem", null).show();

                        }
                    } else {
                        Log.e(LastNewsMVPApplication.LOG_TAG, "ProblemAtEndPoint");
                    }
                } else {
                    Utiles.Log("failedToGetAPI ||| ");
                }
            }

            @Override
            public void onFailure(Call<RetroGetBBCNews> call, Throwable t) {
                Utiles.Log("onFailure ||| " + t);
                newsView.LoadingDismiss();
                Snackbar.make(rootView, "problem from server :( ", Snackbar.LENGTH_LONG)
                        .setAction("problem", null).show();
            }
        };

        retroGetBBCNewsCall.enqueue(retroGetBBCNewsCallback);
    }

    @Override
    public void getSport(final View rootView) {
        newsView.LoadingShow();
        RetroBaseApi retroBaseApi = LastNewsMVPApplication.retrofit.create(RetroBaseApi.class);
        final Call<RetroGetBBCNews> retroGetBBCNewsCall = retroBaseApi.bbcSport();
        final Callback<RetroGetBBCNews> retroGetBBCNewsCallback = new Callback<RetroGetBBCNews>() {
            @Override
            public void onResponse(Call<RetroGetBBCNews> call, Response<RetroGetBBCNews> response) {
                if (response.isSuccessful()) {
                    RetroGetBBCNews apiResponse = response.body();
                    if (apiResponse != null) {

                        status = apiResponse.getStatus();
                        if (status.equals("ok")) {
                            modelNewsSports.clear();
                            retroGetBBCNewsDatas = apiResponse.getRetroGetBBCNewsDatas();

                            mRealm = Realm.getInstance(LastNewsMVPApplication.configRealm);
                            mRealm.beginTransaction();

                            for (int i = 0; i < retroGetBBCNewsDatas.size(); i++) {
                                RealmSport realmSport = new RealmSport();

                                realmSport.setTitle(retroGetBBCNewsDatas.get(i).getTitle());
                                realmSport.setDescription(retroGetBBCNewsDatas.get(i).getDescription());
                                realmSport.setUrlToImage(retroGetBBCNewsDatas.get(i).getUrlToImage());
                                realmSport.setUrl(retroGetBBCNewsDatas.get(i).getUrl());
                                realmSport.setPublishedAt(retroGetBBCNewsDatas.get(i).getPublishedAt());
                                realmSport.setAuthor(retroGetBBCNewsDatas.get(i).getAuthor());

                                realmSports.add(realmSport);
                            }
                            mRealm.delete(RealmSport.class);
                            mRealm.copyToRealmOrUpdate(realmSports);
                            mRealm.commitTransaction();

                            /*set recycler*/
                            modelNewsSports = helperCatchInteractor.getSport();
                            newsView.setRecyclerNews(modelNewsSports);

                            /*check data*/
                            if (modelNewsSports.size() > 0) {
                                newsView.noData(false);
                            } else {
                                newsView.noData(true);
                            }
                            newsView.LoadingDismiss();

                        } else {
                            newsView.LoadingDismiss();
                            Snackbar.make(rootView, "problem in api try again", Snackbar.LENGTH_LONG)
                                    .setAction("problem", null).show();

                        }
                    } else {
                        Log.e(LastNewsMVPApplication.LOG_TAG, "ProblemAtEndPoint");
                    }
                } else {
                    Utiles.Log("failedToGetAPI ||| ");
                }
            }

            @Override
            public void onFailure(Call<RetroGetBBCNews> call, Throwable t) {
                Utiles.Log("onFailure ||| " + t);
                newsView.LoadingDismiss();
                Snackbar.make(rootView, "problem from server :( ", Snackbar.LENGTH_LONG)
                        .setAction("problem", null).show();
            }
        };

        retroGetBBCNewsCall.enqueue(retroGetBBCNewsCallback);

    }

    @Override
    public void sendOrGetDataFromServer(String status, View rootView) {

        if (status.equals("getNews")) {
            modelNewsSports.clear();
            retroGetBBCNewsDatas.clear();
            if (!Utiles.isNetworkConnected()) {
                modelNewsSports.clear();
                modelNewsSports = helperCatchInteractor.getNews();
                if (modelNewsSports.size() > 0) {
                    newsView.noData(false);
                    newsView.setRecyclerNews(modelNewsSports);
                } else {
                    newsView.noData(true);
                    newsView.showNoNet();
                }
            } else {
                getNews(rootView);
            }
        } else if (status.equals("getSport")) {
            modelNewsSports.clear();
            retroGetBBCNewsDatas.clear();
            if (!Utiles.isNetworkConnected()) {
                modelNewsSports.clear();
                modelNewsSports = helperCatchInteractor.getSport();
                if (modelNewsSports.size() > 0) {
                    newsView.noData(false);
                    newsView.setRecyclerNews(modelNewsSports);
                } else {
                    newsView.noData(true);
                    newsView.showNoNet();
                }
            } else {
                getSport(rootView);
            }
        }

    }
}
