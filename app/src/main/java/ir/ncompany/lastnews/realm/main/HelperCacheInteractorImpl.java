package ir.ncompany.lastnews.realm.main;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.ncompany.lastnews.LastNewsMVPApplication;
import ir.ncompany.lastnews.model.ModelNewsSport;
import ir.ncompany.lastnews.realm.RealmNews;
import ir.ncompany.lastnews.realm.RealmSport;

public class HelperCacheInteractorImpl implements HelperCatchInteractor {

    private Realm mRealm = Realm.getInstance(LastNewsMVPApplication.configRealm);

    @Override
    public ArrayList<ModelNewsSport> getNews() {
        ArrayList<ModelNewsSport> modelNews = new ArrayList<>();
        RealmResults<RealmNews> newsRealmResults =
                mRealm.where(RealmNews.class).findAll();
        try {
            for (RealmNews realmNews : newsRealmResults) {

                ModelNewsSport modelNew = new ModelNewsSport();

                modelNew.setDescription(realmNews.getDescription());
                modelNew.setTitle(realmNews.getTitle());
                modelNew.setAuthor(realmNews.getAuthor());
                modelNew.setPublishedAt(realmNews.getPublishedAt());
                modelNew.setUrl(realmNews.getUrl());
                modelNew.setUrlToImage(realmNews.getUrlToImage());

                modelNews.add(modelNew);
            }
        } catch (Exception e) {

        }
        return modelNews;
    }

    @Override
    public ArrayList<ModelNewsSport> getSport() {
        ArrayList<ModelNewsSport> modelSports = new ArrayList<>();
        RealmResults<RealmSport> sportRealmResults =
                mRealm.where(RealmSport.class).findAll();
        try {
            for (RealmSport realmSport : sportRealmResults) {

                ModelNewsSport modelsport = new ModelNewsSport();

                modelsport.setDescription(realmSport.getDescription());
                modelsport.setTitle(realmSport.getTitle());
                modelsport.setAuthor(realmSport.getAuthor());
                modelsport.setPublishedAt(realmSport.getPublishedAt());
                modelsport.setUrl(realmSport.getUrl());
                modelsport.setUrlToImage(realmSport.getUrlToImage());

                modelSports.add(modelsport);
            }
        } catch (Exception e) {

        }
        return modelSports;
    }

}