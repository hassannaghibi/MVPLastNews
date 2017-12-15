package ir.ncompany.lastnews.realm.main;

import java.util.ArrayList;

import ir.ncompany.lastnews.model.ModelNewsSport;

/**
 * Created by ASUS on 7/2/2017.
 */

public interface HelperCatchInteractor {

    ArrayList<ModelNewsSport> getNews();

    ArrayList<ModelNewsSport> getSport();
}
