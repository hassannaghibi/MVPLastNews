package ir.ncompany.lastnews.main.news;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ir.ncompany.lastnews.R;
import ir.ncompany.lastnews.LastNewsMVPApplication;
import ir.ncompany.lastnews.model.ModelNewsSport;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MainHolder> {

    private List<ModelNewsSport> modelNewsSports;

    public NewsAdapter(List<ModelNewsSport> modelNewsSports) {
        this.modelNewsSports = modelNewsSports;
    }

    @Override
    public int getItemCount() {
        return modelNewsSports.size();
    }

    @Override
    public void onBindViewHolder(final MainHolder mainHolder, int position) {
        final ModelNewsSport modelNewsSport = modelNewsSports.get(position);

        if (modelNewsSport.getTitle()==null || modelNewsSport.getTitle().equals("")) {
            mainHolder.view_news_tv_title.setText(LastNewsMVPApplication.context.getResources().getString(R.string.view_news_noData));
        } else {
            mainHolder.view_news_tv_title.setText(modelNewsSport.getTitle());
        }

        if (modelNewsSport.getDescription() == null || modelNewsSport.getDescription().equals("")) {
            mainHolder.view_news_tv_description.setText(LastNewsMVPApplication.context.getResources().getString(R.string.view_news_noData));
        } else {
            mainHolder.view_news_tv_description.setText(modelNewsSport.getDescription());
        }

        if (modelNewsSport.getAuthor()==null || modelNewsSport.getAuthor().equals("")) {
            mainHolder.view_news_tv_author.setText(LastNewsMVPApplication.context.getResources().getString(R.string.view_news_noData));
        } else {
            mainHolder.view_news_tv_author.setText(modelNewsSport.getAuthor());
        }

        if (modelNewsSport.getPublishedAt() == null || modelNewsSport.getPublishedAt().equals("")) {
            mainHolder.view_news_tv_date.setText(LastNewsMVPApplication.context.getResources().getString(R.string.view_news_noData));
        } else {
            mainHolder.view_news_tv_date.setText(modelNewsSport.getPublishedAt());
        }

        Glide.with(LastNewsMVPApplication.context)
                .load(modelNewsSport.getUrlToImage())
                .crossFade()
                .placeholder(R.drawable.no_image)
                .into(mainHolder.view_news_image);

    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.view_news, viewGroup, false);

        return new MainHolder(itemView);
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.view_news_image)
        ImageView view_news_image;
        @InjectView(R.id.view_news_tv_title)
        TextView view_news_tv_title;
        @InjectView(R.id.view_news_tv_description)
        TextView view_news_tv_description;
        @InjectView(R.id.view_news_tv_author)
        TextView view_news_tv_author;
        @InjectView(R.id.view_news_tv_date)
        TextView view_news_tv_date;
        @InjectView(R.id.view_news_cardview)
        CardView view_news_cardview;

        public MainHolder(View v) {
            super(v);
            ButterKnife.inject(this,itemView);
        }

    }

}
