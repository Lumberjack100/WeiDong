package cn.trunch.weidong.http;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.trunch.weidong.util.StrUtils;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 封装OkHttp核心，完成配置和请求的发送
 */
public class OkHttpUtil {
    private static OkHttpClient okHttpClient = null;

    public static void init() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient()
                    .newBuilder()
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(interceptor)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS);
            okHttpClient = builder.build();
        }
    }

    static void get(String url, OkHttpCallback okHttpCallback, HashMap<String, String> paramsMap) {
        Call call = null;
        url = getParamsString(url, paramsMap);
        Request request = new Request.Builder()
                .url(url)
                .build();
        call = okHttpClient.newCall(request);
        call.enqueue(okHttpCallback);
    }

    static void post(String url, OkHttpCallback okHttpCallback, HashMap<String, String> bodyMap) {
        Call call = null;
        FormBody.Builder builder = new FormBody.Builder();
        for (HashMap.Entry<String, String> entry : bodyMap.entrySet()) {
            builder.add(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        call = okHttpClient.newCall(request);
        call.enqueue(okHttpCallback);
    }

    static void upload(String url, OkHttpCallback okHttpCallback, String path) {
        Call call = null;
        File file = new File(path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), fileBody)
                .build();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        call = okHttpClient.newCall(request);
        call.enqueue(okHttpCallback);
    }

    static void uploads(String url, OkHttpCallback okHttpCallback, List<String> paths) {
        Call call = null;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String path : paths) {
            File file = new File(path);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("file", file.getName(), fileBody);
        }
        RequestBody requestBody = builder
                .setType(MultipartBody.FORM)
                .addFormDataPart("type", "2")
                .build();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        call = okHttpClient.newCall(request);
        call.enqueue(okHttpCallback);
    }

    //hwg
    static void uploads(String url, OkHttpCallback okHttpCallback, List<String> paths, int Type) {
        Call call = null;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String path : paths) {
            File file = new File(path);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart(StrUtils.randomNum(false, 6), file.getName(), fileBody);
        }
        RequestBody requestBody = builder
                .setType(MultipartBody.FORM)
                .addFormDataPart("type", String.valueOf(Type))
                .build();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
//        DialogUIUtils.showToastCenter(request.toString());
        call = okHttpClient.newCall(request);
        call.enqueue(okHttpCallback);
    }

    /**
     * 得到追加参数的url
     *
     * @param url          公共url
     * @param urlParamsMap 参数
     * @return 拼装后的url
     */
    private static String getParamsString(String url, HashMap<String, String> urlParamsMap) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (HashMap.Entry<String, String> entry : urlParamsMap.entrySet()) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(entry.getValue(), "utf-8"));
            }
            String paramsString = stringBuilder.toString();
            if (url.contains("?")) {
                url += ("&" + paramsString);
            } else {
                url += ("?" + paramsString);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
