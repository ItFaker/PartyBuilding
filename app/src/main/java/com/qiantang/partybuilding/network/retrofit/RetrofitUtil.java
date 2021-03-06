package com.qiantang.partybuilding.network.retrofit;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.logger.Logger;
import com.qiantang.partybuilding.MyApplication;
import com.qiantang.partybuilding.config.CacheKey;
import com.qiantang.partybuilding.config.Config;
import com.qiantang.partybuilding.modle.HttpResult;
import com.qiantang.partybuilding.modle.RxMonthScore;
import com.qiantang.partybuilding.network.ApiService;
import com.qiantang.partybuilding.utils.NetworkUtil;
import com.qiantang.partybuilding.utils.StringUtil;
import com.qiantang.partybuilding.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    /**
     * 服务器地址
     */
    private static ApiService service;
    private static Retrofit retrofit;
    private final static String TAG = "RetrofitUtil";

    public static ApiService getService() {
        if (service == null) {
            service = getRetrofit().create(ApiService.class);
        }
        return service;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(Config.SERVER_HOST)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(MyGsonConverterFactory.create(getGson()))
                    .build();
        }
        return retrofit;
    }

    @NonNull
    private static Gson getGson() {
        return new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
    }

    @NonNull
    private static OkHttpClient getOkHttpClient() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//        //网络缓存路径文件
//        File httpCacheDirectory = new File(MyApplication.getContext()
//                .getExternalCacheDir(), "responses");
//        int cacheSize = 10 * 1024 * 1024; // 10 MiB
//        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor
                        (MyApplication.getContext()));
        return new OkHttpClient.Builder()
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .writeTimeout(20000, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
//                .addNetworkInterceptor(new StethoInterceptor())
//               // 设置缓存
//                .cache(cache)
//              //  网络请求缓存
//                    .addInterceptor(getCacheInterceptor())
                .build();
    }

    @NonNull
    private static Interceptor getCacheInterceptor() {
        return chain -> {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(10, TimeUnit.SECONDS);//这个是控制缓存的最大生命时间
            cacheBuilder.maxStale(365, TimeUnit.DAYS);//这个是控制缓存的过时时间
            CacheControl cacheControl = cacheBuilder.build();
            Request request = chain.request();
            if (!NetworkUtil.isNetworkAvailable(MyApplication.getContext())) {
                request = request.newBuilder()
                        .header("head-request", request.toString())
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetworkUtil.isNetworkAvailable(MyApplication.getContext())) {
                int maxAge = 0; // read from cache
                Log.e(TAG, ":  isNetworkAvailable : " + request.toString());
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                Log.e(TAG, ": no  isNetworkAvailable : " + request.toString());
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        };
    }


    public static class BitmapConverter implements Converter<ResponseBody, Void> {

        public static final BitmapConverter INSTANCE = new BitmapConverter();

        @Override
        public Void convert(ResponseBody value) throws IOException {
            writeResponseBodyToDisk(value);
            return null;
        }
    }

    public static boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File welcomePic = new File(MyApplication.getContext().getExternalFilesDir
                    (null) + File.separator + "myFirst.png");
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

//                long fileSize = body.contentLength();
//                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(welcomePic);


                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);

//                    fileSizeDownloaded += read;
//
//                    String TAG = "RetrofitUtil";
//                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static class MyGsonConverterFactory extends Converter.Factory {

        public static MyGsonConverterFactory create() {
            return create(new Gson());
        }

        public static MyGsonConverterFactory create(Gson gson) {
            return new MyGsonConverterFactory(gson);
        }

        private final Gson gson;

        private MyGsonConverterFactory(Gson gson) {
            if (gson == null) throw new NullPointerException("gson == null");
            this.gson = gson;
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[]
                annotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new GsonBodyConverter<>(adapter);
        }

        private static class GsonBodyConverter<T> implements Converter<ResponseBody, T> {
            private final TypeAdapter<T> adapter;

            GsonBodyConverter(TypeAdapter<T> adapter) {
                this.adapter = adapter;
            }

            @Override
            public T convert(ResponseBody value) throws IOException {
                String temp = value.string();
                Logger.i(temp);
                return adapter.fromJson(temp);
            }
        }
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final HttpResult<T> response) {
        return Observable.create((ObservableEmitter<T> subscriber) -> {
            if (response.isSuccess()) {
                String errorMessage = response.getErrorMessage();
                if (!StringUtil.isEmpty(errorMessage)) {
                    Logger.e(errorMessage);
                }
                if (!subscriber.isDisposed()) {
                    T object = response.getReturnObject();
                    if (!TextUtils.isEmpty(response.getUserquestionnaire_id())) {
                        subscriber.onNext(object == null ? ((T) response) : object);
                    } else if (!TextUtils.isEmpty(response.getImgId())) {
                        subscriber.onNext(object == null ? ((T) response) : object);
                    } else if (response.getCounts() > 0) {
                        try {
                            ((List<RxMonthScore>) object).get(0).setSocre(response.getCounts());
                        } catch (Exception e) {

                        }
                        subscriber.onNext(object == null ? ((T) response) : object);
                    } else if (!TextUtils.isEmpty(response.getAvatar())) {
                        subscriber.onNext((T) response);
                    } else if (!TextUtils.isEmpty(response.getOpenid())) {
                        subscriber.onNext((T) response);
                    } else {
                        try {
                            subscriber.onNext(object == null ? ((T) response) : object);
                        } catch (Exception e) {
                            APIException exception = new APIException(response.getErrorCode(), response.getErrorMessage());
                            subscriber.onNext(object);
                        }
                    }
                }
            } else {
                int errorCode = response.getErrorCode();
                if (!subscriber.isDisposed()) {
                    APIException e = new APIException(errorCode, response.getErrorMessage());
                    if (e.isTokenExpired()) {
                        MyApplication.USER_ID = "";
                        MyApplication.mCache.remove(CacheKey.TOKEN);
                        MyApplication.mCache.remove(CacheKey.USER_ID);
                        MyApplication.isLoginOB.set(false);
                    }
                    subscriber.onError(e);
                }
                return;
            }
            if (!subscriber.isDisposed()) {
                subscriber.onComplete();
            }
        });
    }


    /**
     * eg：登陆时验证码错误；参数为传递等
     */
    public static class APIException extends Exception {

        /**
         * 成功
         */
        public static final int OK = 0;
        /**
         * 回话过期
         */
        public static final int TOKEN_EXPIRED_CODE = 105;
        public static final int TOKEN_FAILURE_CODE = 102;
        public static final int NETWORK_ERROR_STATUS = -1;
        public static final int SERVER_ERROR_STATUS = -2;
        public static final int SOCKE_TTIMEOUT = -3;
        private static final int ACCOUNT_ERROR_CODE = 40202;
        private static final int NOT_FOUND = 404;
        private static final int VERSION_LOW = 455;

        public boolean isTokenExpired() {
            return code == TOKEN_EXPIRED_CODE || code == TOKEN_FAILURE_CODE;
        }

        public boolean isAccountError() {
            return code == ACCOUNT_ERROR_CODE;
        }

        public boolean isNotFoundException() {
            return code == NOT_FOUND;
        }

        public boolean isVersionLow() {
            return code == VERSION_LOW;
        }


        private int code;

        public APIException(int code, String message) {
            super(message);
            this.code = code;
        }

        public APIException(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }


    /**
     * http://www.jianshu.com/p/e9e03194199e
     * <p>
     * Transformer实际上就是一个Func1<Observable<T>, Observable<R>>，
     * 换言之就是：可以通过它将一种类型的Observable转换成另一种类型的Observable，
     * 和调用一系列的内联操作符是一模一样的。
     *
     * @param <T>
     * @return
     */
//    protected <T> Observable.Transformer<T, T> applySchedulers() {
////        return new Observable.Transformer<T, T>() {
////            @Override
////            public Observable<T> call(Observable<T> observable) {
////                return observable.subscribeOn(Schedulers.io())
////                        .observeOn(AndroidSchedulers.mainThread());
////            }
////        };
//
//        return (Observable.Transformer<T, T>) schedulersTransformer;
//    }
//
//    final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
//        @Override
//        public Object call(Object observable) {
//            return ((Observable) observable).subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    ;
//        }
//    };
//    protected <T> Observable.Transformer<HttpResult<T>, T> applySchedulers() {
////        return new Observable.Transformer<Response<T>, T>() {
////            @Override
////            public Observable<T> call(Observable<Response<T>> responseObservable) {
////                return responseObservable.subscribeOn(Schedulers.io())
////                        .observeOn(AndroidSchedulers.mainThread())
////                        .flatMap(new Func1<Response<T>, Observable<T>>() {
////                            @Override
////                            public Observable<T> call(Response<T> tResponse) {
////                                return flatResponse(tResponse);
////                            }
////                        });
////            }
////        };
//        return (Observable.Transformer<HttpResult<T>, T>) transformer;
//    }
//

    protected final ObservableTransformer transformer = (Observable observable) -> {
        return ((Observable) observable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function) (Object response) -> {
                    return flatResponse((HttpResult<Object>) response);
                });
    };
    //    protected final Transformer transformer = observable -> ((Observable) observable)
    // .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .flatMap((Function) response -> flatResponse((HttpResult<Object>) response));


//    protected final Observable.Transformer transformer = observable -> observable
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .flatMap(response -> flatResponse((HttpResult<Object>) response));

    protected <T> ObservableTransformer<HttpResult<T>, T> applySchedulers() {
        return (ObservableTransformer<HttpResult<T>, T>) transformer;
    }

    protected <T> ObservableTransformer<HttpResult<T>, T> apply() {
        return (ObservableTransformer<HttpResult<T>, T>) tf;
    }

    protected final ObservableTransformer tf = observable -> ((Observable) observable)
            .flatMap((Function) response -> flatResponse((HttpResult<Object>) response));

    /**
     * 图片转换
     */
    final ObservableTransformer transformerImg = observable -> ((Observable) observable)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap((Function) response -> writeResponseBodyToDisk((ResponseBody) response));

    /**
     * 图片转换
     */
    protected <T> ObservableTransformer<ResponseBody, T> applySchedulersImg() {
        return (ObservableTransformer<ResponseBody, T>) transformerImg;
    }


    /**
     * 当{@link ApiService}中接口的注解为{@link retrofit2.http.Multipart}时，参数为{@link RequestBody}
     * 生成对应的RequestBody
     *
     * @param param
     * @return
     */
    protected RequestBody createRequestBody(int param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(long param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    protected RequestBody createRequestBody(File param) {
        return RequestBody.create(MediaType.parse("image/*"), param);
    }

}
