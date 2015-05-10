package com.ogaclejapan.qiitanium.datasource.web.api;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;

abstract class WebApi {

  private final OkHttpClient okHttp;
  private final Gson gson;
  private final String endpoint;

  protected WebApi(OkHttpClient okhttp, Gson gson, String endpoint) {
    this.okHttp = okhttp;
    this.gson = gson;
    this.endpoint = endpoint;
  }

  protected Uri.Builder url() {
    return Uri.parse(endpoint).buildUpon();
  }

  protected Uri.Builder url(String path) {
    return url().appendEncodedPath(path);
  }

  protected Uri.Builder url(String pathFormat, Object... args) {
    return url().appendEncodedPath(String.format(pathFormat, args));
  }

  protected Request.Builder request(Uri.Builder url) {
    return request(url.build());
  }

  protected Request.Builder request(Uri url) {
    return new Request.Builder().url(url.toString());
  }

  protected Request.Builder get(Uri.Builder url) {
    return request(url).get();
  }

  protected Request.Builder get(Uri url) {
    return request(url).get();
  }

  protected Request.Builder post(Uri.Builder url, RequestBody body) {
    return request(url).post(body);
  }

  protected Request.Builder post(Uri url, RequestBody body) {
    return request(url).post(body);
  }

  protected Request.Builder put(Uri.Builder url, RequestBody body) {
    return request(url).put(body);
  }

  protected Request.Builder put(Uri url, RequestBody body) {
    return request(url).put(body);
  }

  protected Request.Builder delete(Uri.Builder url) {
    return request(url).delete();
  }

  protected Request.Builder delete(Uri url) {
    return request(url).delete();
  }

  protected Response execute(Request.Builder request) throws IOException {
    return execute(request.build());
  }

  protected Response execute(Request request) throws IOException {
    return okHttp.newCall(request).execute();
  }

  protected void executeAsync(Request.Builder request, Callback callback) {
    executeAsync(request.build(), callback);
  }

  protected void executeAsync(Request request, Callback callback) {
    okHttp.newCall(request).enqueue(callback);
  }

  protected <T> T objectify(Response response, Class<T> classOfT) throws IOException {
    return objectify(response, TypeToken.get(classOfT));
  }

  protected <T> T objectify(Response response, TypeToken<T> typeTokenOfT) throws IOException {
    return objectify(response, typeTokenOfT.getType());
  }

  protected <T> T objectify(Response response, Type typeOfT) throws IOException {
    return gson.fromJson(response.body().charStream(), typeOfT);
  }

  protected <T> T objectify(String json, Class<T> classOfT) {
    return objectify(json, TypeToken.get(classOfT));
  }

  protected <T> T objectify(String json, TypeToken<T> typeTokenOfT) {
    return objectify(json, typeTokenOfT.getType());
  }

  protected <T> T objectify(String json, Type typeOfT) {
    return gson.fromJson(json, typeOfT);
  }
}
