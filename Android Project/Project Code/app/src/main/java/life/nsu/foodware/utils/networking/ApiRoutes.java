package life.nsu.foodware.utils.networking;

import life.nsu.foodware.models.Restaurant;
import life.nsu.foodware.utils.networking.requests.AuthenticationRequest;
import life.nsu.foodware.utils.networking.responses.RestaurantResponse;
import life.nsu.foodware.utils.networking.responses.RefreshResponse;
import life.nsu.foodware.utils.networking.requests.RegistrationRequest;
import life.nsu.foodware.utils.networking.responses.AuthenticationResponse;
import life.nsu.foodware.utils.networking.responses.MessageResponse;
import life.nsu.foodware.utils.networking.responses.StatusResponse;
import life.nsu.foodware.utils.networking.responses.ValidationResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRoutes {

    @POST("auth/register")
    Call<MessageResponse> registration(@Body RegistrationRequest request);

    @POST("auth/login")
    Call<AuthenticationResponse> authentication(@Body AuthenticationRequest request);

    @POST("auth/refresh")
    Call<RefreshResponse> refresh(@Header("Authorization") String request);

    @POST("auth/logout")
    Call<MessageResponse> deAuthentication(@Header("Authorization") String accessToken);


    //  Vendor API Routes
    @POST("restaurant/create")
    Call<RestaurantResponse> createRestaurant(@Header("Authorization") String accessToken, @Body Restaurant restaurant);

    @Multipart
    @POST("restaurant/logo")
    Call<MessageResponse> uploadLogo (@Header("Authorization") String accessToken, @Part MultipartBody.Part file);

    @POST("restaurant/validate")
    Call<ValidationResponse> validation(@Header("Authorization") String accessToken);

    @POST("restaurant/update")
    Call<RestaurantResponse> updateProfile(@Header("Authorization") String accessToken, @Body Restaurant restaurant);

    @POST("restaurant/status")
    Call<StatusResponse> updateStatus(@Header("Authorization") String accessToken, @Body String status);
}
