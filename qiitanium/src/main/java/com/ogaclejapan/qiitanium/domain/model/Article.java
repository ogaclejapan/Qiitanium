package com.ogaclejapan.qiitanium.domain.model;

import android.content.Context;

import com.ogaclejapan.qiitanium.domain.core.EntityModel;
import com.ogaclejapan.qiitanium.util.IOUtils;
import com.ogaclejapan.rx.binding.RxList;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class Article extends EntityModel {

  private static final String QIITA_URL_FORMAT = "https://qiita.com/%s/items/%s";

  public final RxProperty<String> title = RxProperty.create();
  public final RxProperty<String> excerpt = RxProperty.create();
  public final RxProperty<String> content = RxProperty.create();
  public final RxList<Tag> tags = RxList.create();
  public final RxProperty<Date> createdAt = RxProperty.create();
  public final RxProperty<Integer> commentCount = RxProperty.of(0);
  public final RxProperty<Integer> stockCount = RxProperty.of(0);
  public final RxProperty<Boolean> isBookmark = RxProperty.of(false);
  public final User author;

  Article(Context context, String id, User author) {
    super(context, id);
    this.author = author;
  }

  public String url() {
    return String.format(QIITA_URL_FORMAT, author.id, id);
  }

  public Observable<String> html() {
    return Observable.create(new Observable.OnSubscribe<String>() {
      @Override
      public void call(final Subscriber<? super String> subscriber) {

        try {
          final String html = IOUtils
              .readAllFromAssets(getContext(), "html/article.html");

          Document doc = Jsoup.parse(html);
          Element elem = doc.getElementById("content");
          elem.append(content.get());

          RxUtils.onNextIfSubscribed(subscriber, doc.outerHtml());
          RxUtils.onCompletedIfSubsribed(subscriber);
        } catch (IOException ioe) {
          RxUtils.onErrorIfSubscribed(subscriber, ioe);
        }
      }
    }).subscribeOn(Schedulers.io());
  }

  public Observable<String> tag() {
    if (tags.isEmpty()) {
      return Observable.just(null);
    }
    StringBuilder sb = new StringBuilder();
    for (Tag tag : tags) {
      sb.append(String.format("%s ", tag.name.get()));
    }
    return Observable.just(sb.toString());
  }

}
