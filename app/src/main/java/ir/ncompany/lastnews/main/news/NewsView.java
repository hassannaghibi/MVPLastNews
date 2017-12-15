package ir.ncompany.lastnews.main.news;

import java.util.ArrayList;

import ir.ncompany.lastnews.model.ModelNewsSport;

/**
 * Created by ASUS on 7/2/2017.
 */

public interface NewsView {

    void LoadingShow();

    void LoadingDismiss();

    void noData(boolean status);

    void showNoNet();

    void setRecyclerNews(ArrayList<ModelNewsSport> mobileNumber);
}
