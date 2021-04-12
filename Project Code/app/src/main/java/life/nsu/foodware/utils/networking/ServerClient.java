package life.nsu.foodware.utils.networking;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerClient {
    //    test server
//    private static final String BASE_URL = "https://honest-snake-3.telebit.io/";
//    release server
    private static final String BASE_URL = "https://265e64f7b8ba.ngrok.io/api/";

    private static ServerClient mInstance;
    Retrofit retrofit;

    public static synchronized ServerClient getInstance() {
        if (mInstance == null) {
            mInstance = new ServerClient();
        }

        return mInstance;
    }

    public ApiRoutes getRoute() {
        return retrofit.create(ApiRoutes.class);
    }

    private ServerClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        chain -> {
                            Request original = chain.request();

                            Request.Builder requestBuilder = original.newBuilder()
                                    .method(original.method(), original.body());


                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                ).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

}
