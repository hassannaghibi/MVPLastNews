package ir.ncompany.lastnews.main.news;

import android.view.View;

/**
 * Created by ASUS on 7/2/2017.
 */

public interface NewsPresenter {

    void getNews(View rootView);

    void getSport(View rootView);

    void sendOrGetDataFromServer(String status,View rootView);
}
